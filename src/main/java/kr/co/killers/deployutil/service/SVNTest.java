package kr.co.killers.deployutil.service;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.h2.store.fs.FileUtils;
import org.joda.time.LocalDate;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import kr.co.killers.deployutil.util.ShellUtil;

public class SVNTest {

	private static String copyShellFileName = "source_copy.sh";

	private static String backupShellFileName = "source_backup.sh";

	private static String recoveryShellFileName = "source_recovery.sh";

	private static String copyCheckShellFileName = "source_copy_check.sh";

	private static String recoveryCheckShellFileName = "source_recovery_check.sh";

	public static void main(String[] args) {
		Map<String, String> classNameMap = new HashMap<String, String>();

		DAVRepositoryFactory.setup();
		String url = "";
		String name = "";
		String password = "";
		long startRevision = 0;
		long endRevision = -1; // HEAD (the latest) revision

		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authManager);

			Collection logEntries = null;

			logEntries = repository.log(new String[] { "" }, null, startRevision, endRevision, true, true);

			for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
				SVNLogEntry logEntry = (SVNLogEntry) entries.next();
				if (logEntry.getChangedPaths().size() > 0) {
					Set changedPathsSet = logEntry.getChangedPaths().keySet();

					for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) {
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

			StringBuffer copyBuffer = new StringBuffer();
			StringBuffer copyCheckBuffer = new StringBuffer();
			StringBuffer backupBuffer = new StringBuffer();
			StringBuffer recoveryBuffer = new StringBuffer();
			StringBuffer recoveryCheckBuffer = new StringBuffer();
			LocalDate theDay = new LocalDate();
			String pattern = "yyyyMMdd";
			String today = theDay.toString(pattern);
			String dir = "C://temp" + File.separatorChar + today+ File.separatorChar ;
			FileUtils.createDirectories(dir);

			String copyDir = dir + "copy";
			String backupDir = dir + "backup";

			ShellUtil.writeShell(copyBuffer, copyCheckBuffer, backupBuffer, recoveryBuffer, recoveryCheckBuffer, classNameMap, copyDir, backupDir);

			
			ShellUtil.writeShell(dir + copyShellFileName, copyBuffer.toString());
			ShellUtil.writeShell(dir + backupShellFileName, backupBuffer.toString());
			ShellUtil.writeShell(dir + recoveryShellFileName, recoveryBuffer.toString());
			ShellUtil.writeShell(dir + copyCheckShellFileName, copyCheckBuffer.toString());
			ShellUtil.writeShell(dir + recoveryCheckShellFileName, recoveryCheckBuffer.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
