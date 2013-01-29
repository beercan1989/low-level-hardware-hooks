package co.uk.baconi.activity.framework.impl;

import co.uk.baconi.activity.framework.functions.KeyLoggerResponseFunction;
import co.uk.baconi.activity.framework.interfaces.KeyConverter;
import co.uk.baconi.activity.framework.interfaces.KeyLogger;

public final class X11KeyLogger extends AbstractImpl implements KeyLogger {
    private X11KeyLogger() {
        super();
    }

    @Override
    public void startLogging(final KeyLoggerResponseFunction keyLoggerFunction, final KeyConverter keyConverter) {
        // TODO Auto-generated method stub

    }
}
