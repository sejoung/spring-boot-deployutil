package kr.co.killers.deployutil.service;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * \spring-boot-deployutil\src\main\java\kr\co\killers\deployutil\service\SVNService.java
 */
public class SVNTest {

	public static void main(String[] args) {

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
				System.out.println("---------------------------------------------");
				System.out.println("revision: " + logEntry.getRevision());
				System.out.println("author: " + logEntry.getAuthor());
				System.out.println("date: " + logEntry.getDate());
				System.out.println("log message: " + logEntry.getMessage());

				if (logEntry.getChangedPaths().size() > 0) {
					System.out.println();
					System.out.println("changed paths:");
					Set changedPathsSet = logEntry.getChangedPaths().keySet();

					for (Iterator changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) {
						SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
						System.out.println(" " + entryPath.getType() + " " + entryPath.getPath() + ((entryPath.getCopyPath() != null) ? " (from " + entryPath.getCopyPath() + " revision " + entryPath.getCopyRevision() + ")" : ""));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
