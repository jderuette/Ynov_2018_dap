package fr.ynov.dap.dap;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class Config.
 */
public class Config {

	/** The sample config. */
	private App sampleConfig;
	
	/**
	 * Instantiates a new config.
	 *
	 * @param sampleConfig the sample config
	 */
	public Config(App sampleConfig) {
		this.sampleConfig = sampleConfig;
	}
	
	/**
	 * Json.
	 *
	 * @return the app
	 */
	@RequestMapping("/")
	public App json() {
		return this.sampleConfig;
	}
}
