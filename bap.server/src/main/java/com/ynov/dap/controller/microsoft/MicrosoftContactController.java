package com.ynov.dap.controller.microsoft;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.controller.BaseController;
import com.ynov.dap.service.microsoft.MicrosoftContactService;

/**
 * The Class MicrosoftContactController.
 */
@Controller
@RequestMapping("contact")
public class MicrosoftContactController extends BaseController {

    /** The microsoft contact service. */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     * Contacts.
     *
     * @param appUser            the app user
     * @param model              the model
     * @param request            the request
     * @param redirectAttributes the redirect attributes
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @GetMapping("/microsoft/{appUser}/view")
    public String contacts(@PathVariable final String appUser, final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) throws IOException {
        model.addAttribute("accounts", microsoftContactService.getContacts(appUser));

        return "microsoft/contact";
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ynov.dap.controller.BaseController#getClassName()
     */
    @Override
    public String getClassName() {
        return MicrosoftContactController.class.getName();
    }
}
