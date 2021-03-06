package uk.co.baconi.keylogger.framework.exceptions;

import uk.co.baconi.keylogger.framework.interfaces.FactoryType;

public final class UnsupportedFactoryType extends RuntimeException {
    private static final long serialVersionUID = 3395254264799053564L;

    public UnsupportedFactoryType(final Class<? extends FactoryType<?>> clazz) {
        super(clazz.getName());
    }

    public UnsupportedFactoryType(final Class<? extends FactoryType<?>> clazz, final Throwable t) {
        super(clazz.getName(), t);
    }
}
