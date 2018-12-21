package fr.ynov.dap.client;

/**
 * The Class Config.
 */
//TODO thb by Djer |Design Patern| (ancien TO-DO)si tu souhaite reprendre le principe "ZeroConf" : 1- tout est modifiable (donc des attributs, accessible via getter/setter); 2- lors de la construction un meximum des attributs sont pré-rempli avec une valeur "raisonablement juste"
public class Config {
	
	/** The instance. */
    //FIXME thb by Djer |POO| (ancien TO-DO) Pourrait être une constante, donc FINAL en plus de static !
	private static Config INSTANCE = null;
	
	/** The http url. */
	//TODO thb by Djer |JavaDoc| (ancien TO-DO) Ce commentaire JavaDoc n'apporte RIEN, www.google.be est aussi une URL. Lorsque la variable est parfaitement nomée, la javadoc est très peu utile, mais ici le nom est "correct" et PAS parfait. "The backEnd server root URL" est court, et précise le contenu. Cette remarque est vrai pour TOUS tes commentaires Javadoc (je te ferais parfois une "porposition")
	private String HTTP_URL = "http://localhost:8080";
			
	/**
	 * Instantiates a new config.
	 */
	private Config() {
		
	}
	
	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	//TODO thb by Djer |POO| (ancien TO-DO) Un simple "get", ou un getInstance (camelCase) serait mieux.
	public static Config getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new Config();
		}
		
		return INSTANCE;
	}

	/**
	 * Gets the http url.
	 *
	 * @return the http url
	 */
	public String getHTTP_URL() {
		return HTTP_URL;
	}
}