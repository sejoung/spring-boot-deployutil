package kr.co.killers.deployutil.service;

import java.util.Map;

/**
 * Created by ASH on 2015-11-09.
 */
public interface SVNService {
    public void getLatestFileCheckout(String url, String destPath, String id, String passwd) throws Exception;
    public Map<String, String> getRepositorypaths(String url, String id, String passwd, int startRevision, int endRevision) throws Exception;
}
