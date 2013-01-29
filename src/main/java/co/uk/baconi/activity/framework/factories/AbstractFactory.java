package co.uk.baconi.activity.framework.factories;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.uk.baconi.activity.framework.enums.PlatformType;
import co.uk.baconi.activity.framework.exceptions.UnsupportedFactoryType;
import co.uk.baconi.activity.framework.exceptions.UnsupportedOperationSytem;
import co.uk.baconi.activity.framework.interfaces.FactoryType;

abstract class AbstractFactory<T extends FactoryType<T>, W extends T, X extends T> {

    private final Log log = LogFactory.getLog(getClass());

    private final PlatformType platformType;
    private final Class<W> windowsType;
    private final Class<X> x11Type;

    protected AbstractFactory(final Class<W> windowsType, final Class<X> x11Type) {
        this.windowsType = windowsType;
        this.x11Type = x11Type;
        platformType = PlatformType.getPlatformType();
    }

    public final T createInstance() {
        if (platformType.equals(PlatformType.WINDOWS)) {
            return createInstance(windowsType);
        }

        if (platformType.equals(PlatformType.X11)) {
            return createInstance(x11Type);
        }

        throw new UnsupportedOperationSytem(platformType);
    }

    private T createInstance(final Class<? extends T> clazz) {
        try {
            final Constructor<? extends T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            final T result = constructor.newInstance();
            constructor.setAccessible(false);
            return result;
        } catch (final Throwable t) {
            log.error(t.getClass().getName(), t);
            throw new UnsupportedFactoryType(clazz, t);
        }
    }
}
