package uk.co.baconi.keylogger.hooks;

import com.sun.jna.platform.win32.WinUser.HHOOK;

public interface WindowsKeyboardHookParent {
    HHOOK getHHOOK();

    void quit();
}
