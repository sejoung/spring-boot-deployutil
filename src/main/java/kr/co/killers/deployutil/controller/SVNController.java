package kr.co.killers.deployutil.controller;

import kr.co.killers.deployutil.service.SVNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by ASH on 2015-11-09.
 */

@Controller
public class SVNController {

    @Autowired
    private SVNService svnService;

    @RequestMapping("/svn")
    public String svn(Map<String, Object> model) throws Exception {
        svnService.getLatestFileCheckout("http://210.218.225.23/svn/cspapi/branches/cspapi", "C:\\svn_temp", "AhnSangHyuk", "ash193cm");
        return "svn";
    }

}
