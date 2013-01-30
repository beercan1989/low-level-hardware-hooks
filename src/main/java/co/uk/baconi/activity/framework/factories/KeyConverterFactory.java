package co.uk.baconi.activity.framework.factories;

import co.uk.baconi.activity.framework.impl.WindowsKeyConverter;
import co.uk.baconi.activity.framework.impl.X11KeyConverter;
import co.uk.baconi.activity.framework.interfaces.KeyConverter;

public final class KeyConverterFactory extends AbstractFactory<KeyConverter, WindowsKeyConverter, X11KeyConverter> {
    public static final KeyConverterFactory INSTANCE = new KeyConverterFactory();

    public static KeyConverterFactory getInstance() {
        return INSTANCE;
    }

    private KeyConverterFactory() {
        super(WindowsKeyConverter.class, X11KeyConverter.class);
    }
}
