package fr.ynov.dap.exception;

/**
 * Represent an error when adding an account failed.
 * @author Kévin Sibué
 *
 */
public class AddAccountFailedException extends Exception {

    /**
     * I don't know.
     */
    //TODO sik by Djer |POO| Le "serialVersionUID" est utilisé en interne lors de la sérialization (pour "déplacer" ton instance d'un serveur à client/autre serveur : met tous les attributs "en série" (à la suite les uns les autres), par exemple "StoredCredential" de Google est une "Map" sérialisée) et la désérialisation (opération inverse). Cette ID permet d'avertir lorsque que la classe "change de version" pour éviter des problems de (de)sérialisation si la structure de la classe n'a pa changé, mais que son "sens" à changé ("name" contenait un prénom+nom et maintenant ne contient plus qu'un nom). Permetra de détecter qu'un client n'est plus à jours / au serveur.
    // En général on fait **générer** un UID, et on en re-génère un lorsque la classe change. Il s'agit d'un mécanisme "de base" mais "rarement" correctement utilisé alors qu'il peut éviter des problèmes "complexe" à détecter. Tu peux lire la JavaDoc de java.io.Serializable
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public AddAccountFailedException() {
        super("Add account failed");
    }

}
