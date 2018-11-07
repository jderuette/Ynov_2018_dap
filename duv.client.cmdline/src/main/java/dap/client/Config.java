package dap.client;

/**
 * config of the client app .
 *
 * @author David_tepoche
 *
 */
public final class Config {

    /**
     * stock the instance of config.
     */
    private static Config config;

    /**
     * rootUrl, default = "http://localhost:8080".
     */
    private String rootUrl = "http://localhost:8080";

    /**
     * @param rootUrl
     */
    private Config() {
    }

    /**
     * singleton.
     *
     * @return the instance stocked
     */
    public static Config getConf() {

        if (config == null) {
            config = new Config();
        }
        return config;
    }

    /**
     * @return the rootUrl
     */
    public String getRootUrl() {
        return rootUrl;
    }

    /**
     * @param rootUrlGiven the rootUrl to set
     */
    public void setRootUrl(final String rootUrlGiven) {
        this.rootUrl = rootUrlGiven;
    }

}
