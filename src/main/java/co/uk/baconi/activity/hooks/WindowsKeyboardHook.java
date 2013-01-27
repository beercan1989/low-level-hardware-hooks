package co.uk.baconi.activity.hooks;

import static co.uk.baconi.utils.StringUtil.COMMA;
import static co.uk.baconi.utils.StringUtil.concat;
import co.uk.baconi.activity.Logger;
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

    private static final Logger LOGGER = Logger.getLogger("KeyHookLog.csv", false, true, LOG_HEADINGS);

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

    private void test() {
        // final Display display = X11.INSTANCE.XOpenDisplay("localhost:0.1");
        // final XEvent event_return = new XEvent();
        // X11.INSTANCE.XPeekEvent(display, event_return);
        // System.err.println("KeyCode [" + event_return.xkey.keycode + "]");
        //
        // final Window grab_window = X11.INSTANCE.XRootWindow(display, 1);
        // X11.INSTANCE.XGrabKey(display, X11.AnyKey, X11.AnyModifier, grab_window, 0, X11.GrabModeAsync,
        // X11.GrabModeAsync);
    }
}
