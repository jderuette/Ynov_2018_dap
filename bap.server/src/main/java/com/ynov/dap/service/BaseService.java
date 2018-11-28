package com.ynov.dap.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ynov.dap.Config;

public abstract class BaseService {
    protected abstract String getClassName();

    private Logger logger = LogManager.getLogger(getClassName());

    @Autowired
    private Config config;
    
    protected Config getConfig() {
    	return config;
    }

    protected Logger getLogger() {
        return logger;
    }
}
