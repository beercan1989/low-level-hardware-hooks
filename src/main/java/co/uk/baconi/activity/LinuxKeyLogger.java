package co.uk.baconi.activity;

import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.unix.X11.Display;
import com.sun.jna.platform.unix.X11.Window;

public final class LinuxKeyLogger {
    public static void main(final String[] args) {
        final Display display = X11.INSTANCE.XOpenDisplay("localhost:0.0");
        // final XEvent event_return = new XEvent();
        // X11.INSTANCE.XPeekEvent(display, event_return);
        // System.err.println("KeyCode [" + event_return.xkey.keycode + "]");

        final Window grab_window = X11.INSTANCE.XDefaultRootWindow(display);
        X11.INSTANCE.XGrabKey(display, X11.AnyKey, X11.AnyModifier, grab_window, 0, X11.GrabModeAsync,
                X11.GrabModeAsync);
    }
}
