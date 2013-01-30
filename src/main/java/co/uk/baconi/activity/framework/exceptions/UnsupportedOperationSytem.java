package co.uk.baconi.activity.framework.exceptions;

import co.uk.baconi.activity.framework.enums.PlatformType;

public final class UnsupportedOperationSytem extends RuntimeException {
    private static final long serialVersionUID = -2337868445095912780L;

    public UnsupportedOperationSytem(final PlatformType platformType) {
        super(platformType.name());
    }
}
