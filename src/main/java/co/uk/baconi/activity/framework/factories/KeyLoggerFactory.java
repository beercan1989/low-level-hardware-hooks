package co.uk.baconi.activity.framework.factories;

import co.uk.baconi.activity.framework.impl.WindowsKeyLogger;
import co.uk.baconi.activity.framework.impl.X11KeyLogger;
import co.uk.baconi.activity.framework.interfaces.KeyLogger;

public final class KeyLoggerFactory extends AbstractFactory<KeyLogger, WindowsKeyLogger, X11KeyLogger> {
    public static final KeyLoggerFactory INSTANCE = new KeyLoggerFactory();

    public static KeyLoggerFactory getInstance() {
        return INSTANCE;
    }

    private KeyLoggerFactory() {
        super(WindowsKeyLogger.class, X11KeyLogger.class);
    }
}
