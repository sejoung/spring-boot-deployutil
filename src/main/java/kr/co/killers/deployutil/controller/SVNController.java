package kr.co.killers.deployutil.controller;

import kr.co.killers.deployutil.constants.CommonConstants;
import kr.co.killers.deployutil.service.SVNService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASH on 2015-11-09.
 */

@Controller
public class SVNController {
    private static final Logger log = LoggerFactory.getLogger(SVNController.class);

    @Value("${checkout.dest.folder}")
    private String destFolder;

    @Value("${svn.url}")
    private String svnUrl;

    @Value("${svn.id}")
    private String svnId;

    @Value("${svn.password}")
    private String svnPassword;

    @Autowired
    private SVNService svnService;

    @RequestMapping("/svn")
    public String svn(Map<String, Object> model) throws Exception {
        Map<String, String> checkOut = new HashMap<String, String>();
        checkOut = svnService.getLatestFileCheckout(svnUrl, CommonConstants.LOCAL_ROOT + destFolder, svnId, svnPassword, Integer.parseInt(CommonConstants.MINUS_ONE), Integer.parseInt(CommonConstants.MINUS_ONE));
        log.debug("{}", checkOut);
        if (!checkOut.isEmpty()) {
            svnService.getCheckoutFileList(new File(CommonConstants.LOCAL_ROOT + destFolder));
        }

        return "svn";
    }

}
