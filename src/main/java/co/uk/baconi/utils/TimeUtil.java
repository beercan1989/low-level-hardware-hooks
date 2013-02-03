package co.uk.baconi.utils;

import static co.uk.baconi.keylogger.framework.constants.Strings.COLON;
import static co.uk.baconi.keylogger.framework.constants.Strings.HYPHEN;
import static co.uk.baconi.keylogger.framework.constants.Strings.UNDER_SCORE;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;

public final class TimeUtil {
    private TimeUtil() {
    }

    public static String getCurrentDateTime() {
        final SYSTEMTIME systemTime = getSystemTime();
        return new StringBuilder().append(systemTime.wYear).append(HYPHEN).append(systemTime.wMonth).append(HYPHEN)
                .append(systemTime.wDay).append(UNDER_SCORE).append(systemTime.wHour).append(COLON)
                .append(systemTime.wMinute).append(COLON).append(systemTime.wSecond).append(COLON)
                .append(systemTime.wMilliseconds).toString();
    }

    private static SYSTEMTIME getSystemTime() {
        final SYSTEMTIME systemTime = new SYSTEMTIME();
        Kernel32.INSTANCE.GetSystemTime(systemTime);
        return systemTime;
    }
}
