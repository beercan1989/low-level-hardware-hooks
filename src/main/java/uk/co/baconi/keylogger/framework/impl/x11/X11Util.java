package uk.co.baconi.keylogger.framework.impl.x11;

import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.unix.X11.Display;

final class X11Util {
    private static final String NO_KEY_SYMBOL = "NoSymbol";

    static String getKeyName(final X11 x11, final Display x11Display, final int code, final int level) {
        final String keysym = x11.XKeysymToString(x11.XKeycodeToKeysym(x11Display, (byte) code, level));
        return (keysym == null || keysym.trim().isEmpty()) ? NO_KEY_SYMBOL : keysym;
    }

    static boolean isNotEmpty(final byte[] byteArray) {
        for (final byte value : byteArray) {
            if (value != 0) {
                return true;
            }
        }
        return false;
    }
}