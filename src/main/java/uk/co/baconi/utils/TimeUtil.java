package uk.co.baconi.utils;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;

import static uk.co.baconi.keylogger.framework.constants.Strings.*;

public final class TimeUtil {
    private TimeUtil() {
    }

    public static String getCurrentDateTime() {
        final SYSTEMTIME systemTime = getSystemTime();
        return String.valueOf(systemTime.wYear) + HYPHEN + systemTime.wMonth + HYPHEN + systemTime.wDay + UNDER_SCORE +
                systemTime.wHour + COLON + systemTime.wMinute + COLON + systemTime.wSecond + COLON +
                systemTime.wMilliseconds;
    }

    private static SYSTEMTIME getSystemTime() {
        final SYSTEMTIME systemTime = new SYSTEMTIME();
        Kernel32.INSTANCE.GetSystemTime(systemTime);
        return systemTime;
    }
}
