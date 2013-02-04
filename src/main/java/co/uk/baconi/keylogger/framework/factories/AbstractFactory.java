package co.uk.baconi.keylogger.framework.factories;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import co.uk.baconi.keylogger.framework.constants.PlatformType;
import co.uk.baconi.keylogger.framework.exceptions.UnsupportedFactoryType;
import co.uk.baconi.keylogger.framework.exceptions.UnsupportedOperationSytem;
import co.uk.baconi.keylogger.framework.interfaces.FactoryType;

abstract class AbstractFactory<Type extends FactoryType<Type>, WindowsImplementation extends Type, X11Implementation extends Type> {

    private final Logger log = Logger.getLogger(getClass());

    private final PlatformType platformType;
    private final Class<WindowsImplementation> windowsType;
    private final Class<X11Implementation> x11Type;

    protected AbstractFactory(final Class<WindowsImplementation> windowsType, final Class<X11Implementation> x11Type) {
        this.windowsType = windowsType;
        this.x11Type = x11Type;
        platformType = PlatformType.getPlatformType();
    }

    public final Type create() {
        if (platformType.equals(PlatformType.WINDOWS)) {
            return createInstance(windowsType);
        }

        if (platformType.equals(PlatformType.X11)) {
            return createInstance(x11Type);
        }

        throw new UnsupportedOperationSytem(platformType);
    }

    private Type createInstance(final Class<? extends Type> clazz) {
        try {
            final Constructor<? extends Type> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            final Type result = constructor.newInstance();
            constructor.setAccessible(false);
            return result;
        } catch (final Throwable t) {
            log.error(t.getClass().getName(), t);
            throw new UnsupportedFactoryType(clazz, t);
        }
    }
}
