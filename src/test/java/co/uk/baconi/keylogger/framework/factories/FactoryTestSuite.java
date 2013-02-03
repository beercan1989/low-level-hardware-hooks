package co.uk.baconi.keylogger.framework.factories;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import co.uk.baconi.keylogger.framework.factories.AbstractFactory;
import co.uk.baconi.keylogger.framework.interfaces.FactoryType;

import com.sun.jna.Platform;

@Ignore("Abstract Test Suite.")
public abstract class FactoryTestSuite<T extends FactoryType<T>, W extends T, X extends T> {

    private final AbstractFactory<T, W, X> instance;
    private final Class<W> windowsClass;
    private final Class<X> x11Class;

    public FactoryTestSuite(final AbstractFactory<T, W, X> instance, final Class<W> windowsClass,
            final Class<X> x11Class) {
        this.instance = instance;
        this.windowsClass = windowsClass;
        this.x11Class = x11Class;
    }

    @Test
    public void shouldBeAbleToCreateFromFactory() {
        final T factoryCreated = instance.createInstance();

        assertThat(factoryCreated, is(not(nullValue())));

        if (Platform.isWindows()) {
            assertThat(factoryCreated, is(instanceOf(windowsClass)));
            assertThat(factoryCreated, is(not(instanceOf(x11Class))));
        }

        if (Platform.isX11()) {
            assertThat(factoryCreated, is(instanceOf(x11Class)));
            assertThat(factoryCreated, is(not(instanceOf(windowsClass))));
        }
    }
}
