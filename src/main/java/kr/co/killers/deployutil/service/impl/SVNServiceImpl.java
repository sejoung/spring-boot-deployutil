package kr.co.killers.deployutil.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import kr.co.killers.deployutil.constants.CommonConstants;
import kr.co.killers.deployutil.domain.Project;
import kr.co.killers.deployutil.param.ProjectParam;
import kr.co.killers.deployutil.param.TestParam;
import kr.co.killers.deployutil.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.tools.*;

/**
 * Created by ASH on 2015-11-09.
 */
@Service
public class SVNServiceImpl implements SVNService {
    private static final Logger log = LoggerFactory.getLogger(SVNServiceImpl.class);

    @Override
    public Map<String, String> getLatestFileCheckout(String svnUrl, String sourceDir, String svnId, String svnPassword, int startRevision, int endRevision) throws Exception {
        Map<String, String> classNameMap = new HashMap<String, String>();
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnId, svnPassword);
        repository.setAuthenticationManager(authManager);

        long latestRevision = repository.getLatestRevision();
        System.out.println("Repository Latest Revision: " + latestRevision);

        SVNClientManager ourClientManager = SVNClientManager.newInstance();
        ourClientManager.setAuthenticationManager(authManager);

        // use SVNUpdateClient to do the export
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        updateClient.doExport(repository.getLocation(), new File(sourceDir), SVNRevision.create(startRevision), SVNRevision.create(latestRevision), null, true, SVNDepth.INFINITY);

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
    public Map<String, String> getRepositorypaths(String svnUrl, String svnId, String svnPassword, int startRevision, int endRevision) throws Exception {
        Map<String, String> classNameMap = new HashMap<String, String>();
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnId, svnPassword);
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
    public boolean compileComplete(String sourceDir, String sourceWWWDir, String sourceLibDir, String sourceDeployDir) throws Exception {
        // todo: parameter 수정이 필요함. JAVA_VERSION 등..
        String javaVersion = "C:\\Program Files\\Java\\jdk1.7.0_79\\jre";
        String javaLibPath = "C:\\Program Files\\Java\\jdk1.7.0_79\\lib";
        String tomcatLibPath = "C:\\Program Files\\Apache\\apache-tomcat-7.0.62\\lib";

        File[] sourceJars = new File(sourceLibDir).listFiles(File::isFile);
        File[] javaJars = new File(javaLibPath).listFiles(File::isFile);
        File[] tomcatJars = new File(tomcatLibPath).listFiles(File::isFile);

        class DirectoryContents {
            ArrayList<String> filePath = new ArrayList<String>();
            public ArrayList<String> displayDirectoryContents(String dir) throws IOException {
                File[] files = new File(dir).listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        displayDirectoryContents(file.getCanonicalPath());
                    } else {
                        filePath.add(file.getCanonicalPath());
                    }
                }
                return filePath;
            }
        }

        System.setProperty("java.home", javaVersion);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        // TODO: charset -> parameter 변경
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), Charset.forName(CommonConstants.CHARSET_UTF8));
        List<File> sourceFileList = new ArrayList<File>();
        DirectoryContents dc = new DirectoryContents();
        dc.displayDirectoryContents(sourceDir).stream().filter((String file_java) -> file_java.toUpperCase().contains(".JAVA")).forEach(file_java -> sourceFileList.add(new File(file_java)));

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);

        // TODO: charset -> parameter 변경
        ArrayList<String> compileOptions = new ArrayList<String>();
        compileOptions.add("-encoding");
        compileOptions.add(CommonConstants.CHARSET_UTF8);
        compileOptions.add("-d");
        compileOptions.add(sourceDeployDir);
        compileOptions.add("-cp");
        compileOptions.add(CommonUtil.arrayToString(sourceJars, ";") + CommonUtil.arrayToString(javaJars, ";") + CommonUtil.arrayToString(tomcatJars, ";"));
        compileOptions.add("-target");
        compileOptions.add("1.7");
        compileOptions.add("-verbose");
        compileOptions.add("-Xlint:none");

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, compileOptions, null, compilationUnits);
        boolean success = task.call();

        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            log.debug(diagnostic.getSource().toString()+"Line : "+diagnostic.getLineNumber()+" : "+ diagnostic.getCode());
        }

        fileManager.close();
        log.debug("Success: " + success);

        return success;
    }


    /**
     * 2015.11.13 ProjectParam Vo 추가한 형태 메소드로 수정
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> getLatestFileCheckout(ProjectParam param) throws Exception {
        log.debug("start getLatestFileCheckout");
        String svnUrl = param.getSvnUrl();
        String svnId = param.getSvnId();
        String svnPassword = param.getSvnPassword();
        String sourceDir = param.getSourceDir();
        int startRevision = param.getStartRevision();
        int endRevision = param.getEndRevision();
        log.debug("svnUrl:",svnUrl);
        log.debug("svnId:",svnId);
        log.debug("svnPassword:",svnPassword);
        log.debug("sourceDir:",sourceDir);



        Map<String, String> classNameMap = new HashMap<String, String>();
        SVNRepository repository = null;
        repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnId, svnPassword);
        repository.setAuthenticationManager(authManager);

        long latestRevision = repository.getLatestRevision();
        System.out.println("Repository Latest Revision: " + latestRevision);

        SVNClientManager ourClientManager = SVNClientManager.newInstance();
        ourClientManager.setAuthenticationManager(authManager);

        // use SVNUpdateClient to do the export
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        updateClient.doExport(repository.getLocation(), new File(sourceDir), SVNRevision.create(startRevision), SVNRevision.create(latestRevision), null, true, SVNDepth.INFINITY);

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
        log.debug("end getLatestFileCheckout");
        return classNameMap;
    }

    /**
     * 2015.11.13 ProjectParam Vo 추가한 형태 메소드로 수정
     * @param param
     * @return
     * @throws IOException
     */
    @Override
    public boolean compileComplete(ProjectParam param) throws IOException {
        log.debug("start compileComplete");
        String charSet = param.getCharSet();
        String soruceDir = param.getSourceDir();
        String destDir = param.getDestDir();
        String sourceLibDir = param.getSourceLibDir();
        String javaVersion = param.getJavaVersion();
        String javaLibPath = param.getJavaLibPath();
        String tomcatLibPath = param.getTomcatLibPath();
        String sourceDeployDir = param.getSourceDeployDir();
        String sourceDir = param.getSourceDir();

//        String javaVersion = "C:\\Program Files\\Java\\jdk1.7.0_79\\jre";
//        String javaLibPath = "C:\\Program Files\\Java\\jdk1.7.0_79\\lib";
//        String tomcatLibPath = "C:\\Program Files\\Apache\\apache-tomcat-7.0.62\\lib";

        File[] sourceJars = new File(sourceLibDir).listFiles(File::isFile);
        File[] javaJars = new File(javaLibPath).listFiles(File::isFile);
        File[] tomcatJars = new File(tomcatLibPath).listFiles(File::isFile);


        class DirectoryContents {
            ArrayList<String> filePath = new ArrayList<String>();

            public ArrayList<String> displayDirectoryContents(String dir) throws IOException {
                File[] files = new File(dir).listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        displayDirectoryContents(file.getCanonicalPath());
                    } else {
                        filePath.add(file.getCanonicalPath());
                    }
                }
                return filePath;
            }
        }

        System.setProperty("java.home", javaVersion);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        // TODO: charset -> parameter 변경
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), Charset.forName(CommonConstants.CHARSET_UTF8));
        List<File> sourceFileList = new ArrayList<File>();
        DirectoryContents dc = new DirectoryContents();
        dc.displayDirectoryContents(sourceDir).stream().filter((String file_java) -> file_java.toUpperCase().contains(".JAVA")).forEach(file_java -> sourceFileList.add(new File(file_java)));

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);

        // TODO: charset -> parameter 변경
        ArrayList<String> compileOptions = new ArrayList<String>();
        compileOptions.add("-encoding");
        compileOptions.add(CommonConstants.CHARSET_UTF8);
        compileOptions.add("-d");
        compileOptions.add(sourceDeployDir);
        compileOptions.add("-cp");
        compileOptions.add(CommonUtil.arrayToString(sourceJars, ";") + CommonUtil.arrayToString(javaJars, ";") + CommonUtil.arrayToString(tomcatJars, ";"));
        compileOptions.add("-target");
        compileOptions.add("1.7");
        compileOptions.add("-verbose");
        compileOptions.add("-Xlint:none");

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, compileOptions, null, compilationUnits);
        boolean success = task.call();

        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            log.debug(diagnostic.getSource().toString() + "Line : " + diagnostic.getLineNumber() + " : " + diagnostic.getCode());
        }

        fileManager.close();
        log.debug("Success: " + success);

        return success;
    }




    /**
     * Created by chaidam on 2015-11-11.
     */
    // svn.jsp 에서 입력받은 name 값
    @Override
    public Project test(Project project){
        Project resultproject = new Project();
        log.debug("SvnServiceImple start");
        resultproject.setName(project.getName());
        log.debug("name:",resultproject.getName());
        return resultproject;
    }

    @Override
    public boolean svnConnectionCheck(TestParam valid) throws Exception {
        boolean rtn = false;

        log.debug("", valid);

        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(valid.getSvnUrl()));

        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(valid.getSvnId(), valid.getSvnPassword());
        repository.setAuthenticationManager(authManager);

        if(!String.valueOf(repository.getLatestRevision()).isEmpty()) {
            rtn = true;
        }

        log.debug("LOGIN SUCCESS : ", String.valueOf(repository.getLatestRevision()).isEmpty());
        log.debug("SUCCESS : ", repository.getLatestRevision());

        return rtn;
    }
}
