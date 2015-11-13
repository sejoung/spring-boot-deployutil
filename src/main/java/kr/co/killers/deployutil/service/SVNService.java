package kr.co.killers.deployutil.service;

import kr.co.killers.deployutil.domain.Project;
import kr.co.killers.deployutil.param.ProjectParam;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ASH on 2015-11-09.
 */
public interface SVNService {
    public Map<String, String>  getLatestFileCheckout(String url, String destPath, String id, String password, int startRevision, int endRevision) throws Exception;
    public Map<String, String> getRepositorypaths(String url, String id, String password, int startRevision, int endRevision) throws Exception;
    public boolean compileComplete(String soruceDir, String destDir, String libDir) throws Exception;
    public ProjectParam test(ProjectParam projectParam)throws Exception ;

    public boolean compileComplete(ProjectParam valid) throws IOException;
}
