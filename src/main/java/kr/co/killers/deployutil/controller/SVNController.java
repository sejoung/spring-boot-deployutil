package kr.co.killers.deployutil.controller;

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

    @Autowired
    private SVNService svnService;

    @RequestMapping("/svn")
    public String svn(Map<String, Object> model) throws Exception {
        Map<String, String> checkOut = new HashMap<String, String>();
        checkOut = svnService.getLatestFileCheckout("http://210.218.225.23/svn/cspapi/branches/cspapi", destFolder, "AhnSangHyuk", "ash193cm", -1, -1);
        log.debug("{}", checkOut);
        if(!checkOut.isEmpty()){
            svnService.getCheckoutFileList(new File(destFolder));
        }

        return "svn";
    }

}
