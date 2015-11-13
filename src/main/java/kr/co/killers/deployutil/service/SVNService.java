package kr.co.killers.deployutil.service;

import kr.co.killers.deployutil.domain.Project;
import kr.co.killers.deployutil.param.ProjectParam;
import kr.co.killers.deployutil.param.TestParam;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ASH on 2015-11-09.
 */
public interface SVNService {
    public Map<String, String>  getLatestFileCheckout(String svnUrl, String sourceDir, String svnId, String svnPassword, int startRevision, int endRevision) throws Exception;
    public Map<String, String> getRepositorypaths(String svnUrl, String svnId, String svnPassword, int startRevision, int endRevision) throws Exception;
    public boolean compileComplete(String sourceDir, String sourceWWWDir, String sourceLibDir, String sourceDeployDir) throws Exception;
    public Project test(Project project);

    public boolean compileComplete(ProjectParam valid) throws IOException;

    public boolean svnConnectionCheck(TestParam valid) throws Exception;
}
