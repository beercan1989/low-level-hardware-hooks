package co.uk.baconi.keylogger.framework.factories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import co.uk.baconi.keylogger.framework.factories.KeyConverterFactory;
import co.uk.baconi.keylogger.framework.impl.WindowsKeyConverter;
import co.uk.baconi.keylogger.framework.impl.X11KeyConverter;
import co.uk.baconi.keylogger.framework.interfaces.KeyConverter;

public class KeyConverterFactoryTest extends FactoryTestSuite<KeyConverter, WindowsKeyConverter, X11KeyConverter> {

    public KeyConverterFactoryTest() {
        super(KeyConverterFactory.INSTANCE, WindowsKeyConverter.class, X11KeyConverter.class);
    }

    @Test
    public void shouldBeAbleToGetInstance() {
        assertThat(KeyConverterFactory.INSTANCE, is(equalTo(KeyConverterFactory.getInstance())));
    }
}
