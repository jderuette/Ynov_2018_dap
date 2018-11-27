package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseController {

	private static final Logger logger = LogManager.getLogger();

	public static Logger getLogger() {
		return logger;
	}
	
	
	
}
