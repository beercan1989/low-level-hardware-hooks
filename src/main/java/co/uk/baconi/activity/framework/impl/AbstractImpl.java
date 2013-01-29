package co.uk.baconi.activity.framework.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

abstract class AbstractImpl {
    protected final Log log = LogFactory.getLog(getClass());

    protected AbstractImpl() {
        super();
    }
}
