package kr.co.killers.deployutil.service;

/**
 * Created by ASH on 2015-11-09.
 */
public interface SVNService {
    public void getLatestFileRev(String url, String destPath, String id, String passwd) throws Exception;
}
