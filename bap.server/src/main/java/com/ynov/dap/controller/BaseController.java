package com.ynov.dap.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.ynov.dap.Config;

/**
 * The Class BaseController.
 */
public abstract class BaseController {

    /**
     * Gets the class name.
     *
     * @return the class name
     */
    public abstract String getClassName();

    /** The logger. */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * Gets the logger.
     *
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /** The config. */
    @Autowired
    private Config config;

    /**
     * Gets the config.
     *
     * @return the config
     */
    public Config getConfig() {
        return config;
    }
}
