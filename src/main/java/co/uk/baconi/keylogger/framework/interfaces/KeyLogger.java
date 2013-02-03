package co.uk.baconi.keylogger.framework.interfaces;


public interface KeyLogger extends FactoryType<KeyLogger> {
    public void startLogging(KeyConverter keyConverter);
}
