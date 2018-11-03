package dap.client;

/**
 * config of the client app .
 *
 * @author David_tepoche
 *
 */
public final class Config {

    /**
     * stcok the instance of config.
     */
    private static Config config;

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
     * rootUrl, default = "http://localhost:8080".
     */
    private String rootUrl = "http://localhost:8080";

    /**
     * @return the rootUrl
     */
    public String getRootUrl() {
        return rootUrl;
    }

    /**
     * @param rootUrl
     */
    //TODO duv by Djer Attention à l'ordre : Constantes, attributs, constructeurs, méthodes métier, getter/setter
    private Config() {
    }

    /**
     * @param rootUrlGiven the rootUrl to set
     */
    public void setRootUrl(final String rootUrlGiven) {
        this.rootUrl = rootUrlGiven;
    }

}
