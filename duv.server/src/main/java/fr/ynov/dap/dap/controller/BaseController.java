package fr.ynov.dap.dap.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author David_tepoche
 *
 */
public abstract class BaseController {
    /**
     * get the name of the child for the logger.
     *
     * @return the name of the child
     */
    public abstract String getClassName();

    /**
     * Initialize the logger.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * get logger with correct parmeter.
     *
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }
}
