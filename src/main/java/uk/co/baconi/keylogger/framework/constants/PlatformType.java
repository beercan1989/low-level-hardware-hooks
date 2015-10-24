package uk.co.baconi.keylogger.framework.constants;

import com.sun.jna.Platform;

public enum PlatformType {
    X11, //
    WINDOWS, //
    UNSUPPORTED;

    public static PlatformType getPlatformType() {
        if (Platform.isWindows()) {
            return WINDOWS;
        }

        if (Platform.isX11()) {
            return X11;
        }

        return UNSUPPORTED;
    }
}
