package kr.co.killers.deployutil.controller;

import kr.co.killers.deployutil.param.AuthehticationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by ASH on 2015-11-13.
 */

@Controller
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpSession session) {
        AuthehticationDetails userDetails = (AuthehticationDetails) session.getAttribute("userLoginInfo");
        log.debug("Welcome logout! {}, {}", session.getId(), userDetails.getUsername());

        session.invalidate();
    }

    @RequestMapping(value = "/login_success", method = RequestMethod.GET)
    public String login_success(HttpSession session) {
        AuthehticationDetails userDetails = (AuthehticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        log.debug("Welcome login_success! {}, {}", session.getId(), userDetails.getUsername() + "/" + userDetails.getPassword());
        session.setAttribute("userLoginInfo", userDetails);
        return "svnlist";
    }

    @RequestMapping(value = "login_duplicate", method = RequestMethod.GET)
    public void login_duplicate() {
        log.debug("Welcome login_duplicate!");
    }

}
