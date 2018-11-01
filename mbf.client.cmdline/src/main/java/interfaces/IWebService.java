//TODO mbf by Djer ce package de vrait être dans un package "fr.ynov.dap.client"
//TODO mbf by Djer en général on évite de créer des package pour indiquer ce que "sont" les fichier 
// (on sait déja que c'est une interface, c'est sont type !). Un package aura plutot un nom pour indiquer : 
// A quoi sert un type, ou indiquer ce qu'il represente. Ici "requesthandllers" serait un peu mieux.
// Cette Interface peu aussi aller dans le package "services.network"
package interfaces;

/**
 * This interface allows us to handle separately the success and failure server responses.
 */
//TODO mbf by Djer lorsque tu utilise cette Interface, le "onError" est toujours le même.
// Tu pourais créer une lcasse Abstraite qui implemente le "onError" avec comme implementation par defaut le "log de l'exception"
public interface IWebService {
    /**
     * This is the success callback.
     * @param response The request response
     */
    void uponSuccess(String response);

    /**
     * This is the error callback.
     * @param e The error exception
     */
    void uponError(Exception e);
}
