package co.uk.baconi.keylogger.framework.factories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import co.uk.baconi.keylogger.framework.factories.KeyLoggerFactory;
import co.uk.baconi.keylogger.framework.impl.WindowsKeyLogger;
import co.uk.baconi.keylogger.framework.impl.x11.X11KeyLogger;
import co.uk.baconi.keylogger.framework.interfaces.KeyLogger;

public class KeyLoggerFactoryTest extends FactoryTestSuite<KeyLogger, WindowsKeyLogger, X11KeyLogger> {

    public KeyLoggerFactoryTest() {
        super(KeyLoggerFactory.INSTANCE, WindowsKeyLogger.class, X11KeyLogger.class);
    }

    @Test
    public void shouldBeAbleToGetInstance() {
        assertThat(KeyLoggerFactory.INSTANCE, is(equalTo(KeyLoggerFactory.getInstance())));
    }
}
