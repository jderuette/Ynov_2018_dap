package fr.ynov.dap.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepostory;
import fr.ynov.dap.dap.data.GoogleAccount;

/**
 *
 * @author David_tepoche
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * link appUserRespository.
     */
    @Autowired
    private AppUserRepostory appUserRepository;

    /**
     * add a user in database.
     *
     * @param userKey user in database.
     * @return appUser created
     */
    @GetMapping("/add/{userKey}")
    public AppUser addUserKey(@PathVariable("userKey") final String userKey) {

        AppUser appUserInBdd = appUserRepository.findByUserKey(userKey);

        if (appUserInBdd == null) {
            AppUser appUser = new AppUser();
            appUser.setUserKey(userKey);
            appUserInBdd = appUserRepository.save(appUser);

        }
        return appUserInBdd;
    }

    /**
     * link an accountName in userKey.
     *
     * @param userKey     user in bdd
     * @param accountName alias for googleAccount
     * @return AppUserCreated
     * @exception NullPointerException if the user in bdd is missing.
     */
    @GetMapping("/link/{userKey}/{accountName}")
    public AppUser addUserKey(@PathVariable("userKey") final String userKey,
            @PathVariable("accountName") final String accountName) throws NullPointerException {

        AppUser appUserInBdd = appUserRepository.findByUserKey(userKey);

        if (userKey == null) {
            getLogger().warn("utilisateur non connu : " + userKey);
            throw new NullPointerException("l'utilisateur n'existe pas");
        }

        GoogleAccount account = new GoogleAccount();
        account.setAccountName(accountName);
        appUserInBdd.addGoogleAccount(account);
        AppUser appuserSaved = appUserRepository.save(appUserInBdd);
        return appuserSaved;
    }

    @Override
    public final String getClassName() {

        return UserController.class.getName();
    }
}
