package fr.ynov.dap.web.api.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.google.PeopleGoogleService;
import fr.ynov.dap.web.api.DapController;

///**
// * @author adrij
// *
// */
//@RestController
//@RequestMapping("/people")
//public class PeopleController extends DapController {
//    /**
//     * get peopleGoogleService with Spring.
//     */
//    @Autowired
//    private PeopleGoogleService peopleService;
//
//    /**
//     * Get near Event from now.
//     * @param userKey user key needed for authentication
//     * @return near event from now
//     * @throws Exception exception
//     */
//    @RequestMapping(value = "/number", method = RequestMethod.GET)
//    public Integer getNumberOfPeople(@RequestParam("userKey") final String userKey) throws Exception {
//        return peopleService.getNumberOfContacts(userKey);
//    }
//
//}
