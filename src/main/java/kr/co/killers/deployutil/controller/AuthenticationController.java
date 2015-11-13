package kr.co.killers.deployutil.controller;

import kr.co.killers.deployutil.param.TestParam;
import kr.co.killers.deployutil.service.SVNService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by ASH on 2015-11-13.
 */

@Controller
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private SVNService svnService;

    @RequestMapping("/login")
    public String login(Map<String, Object> model, @Valid TestParam valid) throws Exception{
        String rtn = "";

        log.debug("", valid);

        log.debug("", valid.getSvnPassword());

        if(svnService.svnConnectionCheck(valid)) {
            rtn = "svnlist";
        } else {
            rtn = "welcome";
        }
        return rtn;
    }
}
