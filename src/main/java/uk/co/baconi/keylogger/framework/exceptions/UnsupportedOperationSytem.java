package uk.co.baconi.keylogger.framework.exceptions;

import uk.co.baconi.keylogger.framework.constants.PlatformType;

public final class UnsupportedOperationSytem extends RuntimeException {
    private static final long serialVersionUID = -2337868445095912780L;

    public UnsupportedOperationSytem(final PlatformType platformType) {
        super(platformType.name());
    }
}
