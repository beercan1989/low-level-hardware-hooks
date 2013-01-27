package co.uk.baconi.activity.hooks;

import com.sun.jna.platform.win32.WinUser.HHOOK;

public interface WindowsKeyboardHookParent {
    public HHOOK getHHOOK();

    public void quit();
}
