package co.uk.baconi.activity.framework.factories;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.uk.baconi.activity.framework.enums.PlatformType;
import co.uk.baconi.activity.framework.exceptions.UnsupportedFactoryType;
import co.uk.baconi.activity.framework.exceptions.UnsupportedOperationSytem;
import co.uk.baconi.activity.framework.interfaces.FactoryType;

abstract class AbstractFactory<Type extends FactoryType<Type>, WindowsImplementation extends Type, X11Implementation extends Type> {

    private final Log log = LogFactory.getLog(getClass());

    private final PlatformType platformType;
    private final Class<WindowsImplementation> windowsType;
    private final Class<X11Implementation> x11Type;

    protected AbstractFactory(final Class<WindowsImplementation> windowsType, final Class<X11Implementation> x11Type) {
        this.windowsType = windowsType;
        this.x11Type = x11Type;
        platformType = PlatformType.getPlatformType();
    }

    public final Type createInstance() {
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
