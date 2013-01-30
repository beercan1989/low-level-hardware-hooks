package co.uk.baconi.activity.framework.impl;

import co.uk.baconi.activity.framework.functions.KeyLoggerResponseFunction;
import co.uk.baconi.activity.framework.interfaces.KeyConverter;
import co.uk.baconi.activity.framework.interfaces.KeyLogger;

public final class WindowsKeyLogger extends AbstractImpl implements KeyLogger {
    private WindowsKeyLogger() {
        super();
    }

    @Override
    public void startLogging(final KeyLoggerResponseFunction keyLoggerFunction, final KeyConverter keyConverter) {
        // TODO Auto-generated method stub
    }
}
