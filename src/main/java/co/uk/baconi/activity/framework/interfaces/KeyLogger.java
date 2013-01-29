package co.uk.baconi.activity.framework.interfaces;

import co.uk.baconi.activity.framework.functions.KeyLoggerResponseFunction;

public interface KeyLogger extends FactoryType<KeyLogger> {
    public void startLogging(KeyLoggerResponseFunction keyLoggerFunction, KeyConverter keyConverter);
}
