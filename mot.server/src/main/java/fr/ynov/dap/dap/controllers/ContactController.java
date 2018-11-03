package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.ContactService;

/**
 * The Class ContactController.
 */
@RestController
//TODO mot by Djer Evite de mettre des "get" dans les URL 
@RequestMapping("/getContact")
public class ContactController {
   
    /**
     * Gets the nb contact.
     *
     * @param user the user
     * TODO mot by Djer FAUX ! parfois renvoie un "message d'exception" !
     * @return the nb contact
     */
    @RequestMapping("getNbCont/{user}")
    public @ResponseBody String getNbContact(@PathParam("user")String user) {
       
        Integer nbContact = null;
   
        try {
        	nbContact = new ContactService().getNbContact(user);
            return nbContact.toString();
           
        } catch (IOException e) {
            //TODO mot by Djer Traite le todo, un LOG en error serait plus approprié !
            // TODO Auto-generated catch block
             e.printStackTrace();
             //TODO mot by Djer renvoyer ce text sera perturbant pour l'appelant, il vaut mieux renvoyer, Integer
             // Et en cas d'erreur LOG + renvoyer soit 0 (éventuellement -1) soit carément lever une exception
             return e.getMessage();
             
        } catch (GeneralSecurityException e) {
          //TODO mot by Djer Traite le tod, un LOG en error serait plus approprié !
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
           
        }
    }
}