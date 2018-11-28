package com.ynov.dap.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ynov.dap.Config;

/**
 * The Class BaseService.
 */
public abstract class BaseService {
    
    /**
     * Gets the class name.
     *
     * @return the class name
     */
    protected abstract String getClassName();

    /** The logger. */
    private Logger logger = LogManager.getLogger(getClassName());

    /** The config. */
    @Autowired
    private Config config;
    
    /**
     * Gets the config.
     *
     * @return the config
     */
    protected Config getConfig() {
    	return config;
    }

    /**
     * Gets the logger.
     *
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }
}
