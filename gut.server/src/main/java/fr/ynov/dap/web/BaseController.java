package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseController {

	private static final Logger logger = LogManager.getLogger();

	public static Logger getLogger() {
	    //TODO gut by Djer |Log4J| Attention toutes tes classe fille vont partager la catégory "BaseController"
		return logger;
	}
	
	
	
}
