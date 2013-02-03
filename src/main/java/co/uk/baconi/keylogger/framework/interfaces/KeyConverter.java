package co.uk.baconi.keylogger.framework.interfaces;

import java.util.List;

public interface KeyConverter extends FactoryType<KeyConverter> {
    public List<String> getKeys();
}
