package com.ynov.dap.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import com.ynov.dap.Config;

@PropertySource("classpath:config.properties")
public abstract class BaseController {

	public abstract String getClassName();

    private Logger logger = LogManager.getLogger(getClassName());

    public Logger getLogger() {
        return logger;
    }

    /** The env. */
    @Autowired
    protected Config config;
}
