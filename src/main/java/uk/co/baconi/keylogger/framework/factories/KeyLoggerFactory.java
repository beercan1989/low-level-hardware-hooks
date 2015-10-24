package uk.co.baconi.keylogger.framework.factories;

import uk.co.baconi.keylogger.framework.impl.WindowsKeyLogger;
import uk.co.baconi.keylogger.framework.impl.x11.X11KeyLogger;
import uk.co.baconi.keylogger.framework.interfaces.KeyLogger;

public final class KeyLoggerFactory extends AbstractFactory<KeyLogger, WindowsKeyLogger, X11KeyLogger> {
    public static final KeyLoggerFactory INSTANCE = new KeyLoggerFactory();

    public static KeyLoggerFactory getInstance() {
        return INSTANCE;
    }

    private KeyLoggerFactory() {
        super(WindowsKeyLogger.class, X11KeyLogger.class);
    }
}
