package fr.ynov.dap;

//FIXME grj by Djer Revoirle pricnipe ZeroCOnf, ici c'est du "no Conf".
public class Configuration {

    private static final String DEFAULT_USER      = "julien.grandchavin@gmail.com";
    private static final String CREDENTIAL_FOLDER = "/google/credentials.json";
    private static final String CLIENT_SECRET_DIR = "tokens";
    private static final String APPLICATION_NAME  = "Ynov DaP";
    private static final String CALLBACK_URL      = "http://localhost:8080/oAuth2Callback";

    //TODO grj by Djer Pourquoi Final ?
    public final String getApplicationName() {
        return APPLICATION_NAME;
    }

    public final String getCredentialFolder() {
        return CREDENTIAL_FOLDER;
    }

    public final String getClientSecretFile() {
        return CLIENT_SECRET_DIR;
    }

    public final String getDefaultUser() {
        return DEFAULT_USER;
    }

    public final String getoAuth2CallbackUrl() {
        return CALLBACK_URL;
    }


}
