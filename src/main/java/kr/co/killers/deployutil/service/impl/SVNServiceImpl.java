package kr.co.killers.deployutil.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import kr.co.killers.deployutil.service.SVNService;

/**
 * Created by ASH on 2015-11-09.
 */
@Service
public class SVNServiceImpl implements SVNService {

    @Override
    public Map<String, String> getLatestFileCheckout(String url, String destPath, String id, String passwd, int startRevision, int endRevision) throws Exception {
        Map<String, String> classNameMap = new HashMap<String, String>();
        SVNRepository repository = null;
        repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(id, passwd);
        repository.setAuthenticationManager(authManager);

        long latestRevision = repository.getLatestRevision();
        System.out.println("Repository Latest Revision: " + latestRevision);

        SVNClientManager ourClientManager = SVNClientManager.newInstance();
        ourClientManager.setAuthenticationManager(authManager);

        // use SVNUpdateClient to do the export
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        updateClient.doExport(repository.getLocation(), new File(destPath), SVNRevision.create(startRevision), SVNRevision.create(latestRevision), null, true, SVNDepth.INFINITY);

        Collection logEntries = null;

        logEntries = repository.log(new String[]{""}, null, startRevision, endRevision, true, true);

        for (Iterator entries = logEntries.iterator(); entries.hasNext(); ) {
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();

            if (logEntry.getChangedPaths().size() > 0) {

                Set changedPathsSet = logEntry.getChangedPaths().keySet();
                for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext(); ) {
                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                    String key = entryPath.getPath();
                    String typeValueTemp = classNameMap.get(key);
                    if (typeValueTemp != null && !"".equals(typeValueTemp)) {
                        classNameMap.put(key, typeValueTemp + entryPath.getType());
                    } else {
                        classNameMap.put(key, String.valueOf(entryPath.getType()));
                    }

                }

            }
        }
        return classNameMap;
    }

    @Override
    public Map<String, String> getRepositorypaths(String url, String id, String passwd, int startRevision, int endRevision) throws Exception {
        Map<String, String> classNameMap = new HashMap<String, String>();
        SVNRepository repository = null;
        repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(id, passwd);
        repository.setAuthenticationManager(authManager);

        Collection logEntries = null;

        logEntries = repository.log(new String[]{""}, null, startRevision, endRevision, true, true);

        for (Iterator entries = logEntries.iterator(); entries.hasNext(); ) {
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();

            if (logEntry.getChangedPaths().size() > 0) {

                Set changedPathsSet = logEntry.getChangedPaths().keySet();
                for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext(); ) {
                    SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                    String key = entryPath.getPath();
                    String typeValueTemp = classNameMap.get(key);
                    if (typeValueTemp != null && !"".equals(typeValueTemp)) {
                        classNameMap.put(key, typeValueTemp + entryPath.getType());
                    } else {
                        classNameMap.put(key, String.valueOf(entryPath.getType()));
                    }

                }

            }
        }

        return classNameMap;
    }


    @Override
    public void getCheckoutFileList(File destFolder) throws Exception {
        // todo: C:\checkout.txt -> properties나 constatns로 변경해야됨.
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\checkout.txt", true));
        File[] files = destFolder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                getCheckoutFileList(file);
            } else {
                out.write(file.getCanonicalPath());
                out.newLine();
            }
        }
        out.close();
    }
}
