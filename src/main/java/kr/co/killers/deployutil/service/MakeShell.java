package kr.co.killers.deployutil.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MakeShell {
	private static final String PROPERTEIS_FILE_NAME = "config.properties";
	private String baseScriptFileName;
	private String shellFileNameCopy;
	private String shellFileNameBackup;
	private String shellFileNameRecovery;
	private String shellFileNameCopyChk;
	private String shellFileNameRecoveryChk;

	private String copyDestDirectoryName;
	private String destSourceDirectoryName;
	private String destShellDirectoryName;
	private String buildPath;
	private static final String KEY_BASE_PATH = "_PATH";
	private static final String KEY_VAR_TYPE = "_VAR_TYPE";
	private static final String KEY_COPYDIR = "_COPYDIR";
	private static final String KEY_BACKUPDIR = "_BACKUPDIR";
	private static final String KEY_VAR_PATH = "VAR_PATH_";
	private static final String KEY_SCAN_PATH = "_SCAN_PATH";
	private static final String KEY_BUILD_PATH = "_BUILD_PATH";
	private static final String KEY_BASE_SHELL_FILE_NAME = "BASE_SHELL_FILE_NAME";
	private static final String KEY_COPY_SHELL_FILE_NAME = "COPY_SHELL_FILE_NAME";
	private static final String KEY_COPY_CHECK_SHELL_FILE_NAME = "COPY_CHECK_SHELL_FILE_NAME";
	private static final String KEY_BACKUP_SHELL_FILE_NAME = "BACKUP_SHELL_FILE_NAME";
	private static final String KEY_RECOVERY_SHELL_FILE_NAME = "RECOVERY_SHELL_FILE_NAME";
	private static final String KEY_RECOVERY_CHECK_SHELL_FILE_NAME = "RECOVERY_CHECK_SHELL_FILE_NAME";
	private static final String KEY_COPY_DEST_DIRECTORY_NAME = "COPY_DEST_DIRECTORY_NAME";
	private static final String KEY_DEST_SOURCE_DIRECTORY_NAME = "DEST_SOURCE_DIRECTORY_NAME";
	private static final String KEY_DEST_SHELL_DIRECTORY_NAME = "DEST_SHELL_DIRECTORY_NAME";
	public static final int MSG_OK = 0;
	public static final int MSG_ERROR = 1;
	public static final int MSG_INFO = 2;
	public static final int MSG_NOTAG = 3;
	private String basePath;
	private String copyDir;
	private String backupDir;
	private Map<String, String> varNameMap = new HashMap();

	private Map<String, String> totalFileMap = new HashMap();

	private Map<String, List<String>> duplicationMap = new HashMap();

	private StringBuilder backupBuffer = new StringBuilder();
	private StringBuilder copyBuffer = new StringBuilder();
	private StringBuilder recoveryBuffer = new StringBuilder();
	private StringBuilder copyCheckBuffer = new StringBuilder();
	private StringBuilder recoveryCheckBuffer = new StringBuilder();

	private int totalCnt = 0;
	private int successCnt = 0;
	private String makeDateStr;
	private Properties configProp;
	private String projectTypeStr;
/*
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("\nMakeShell [type] [file name] [new file name]");
			return;
		}

		new MakeShell().run(args[0], args[1], args[2]);
	}
*/
	public void run(String type, String fileName, String newFileName) {
		try {
			init(type);

			List fileNameList = getTargetFileNameList(fileName);

			List newfileNameList = getTargetFileNameList(newFileName);

			this.totalCnt = fileNameList.size();
			for (Iterator it = fileNameList.iterator(); it.hasNext();) {
				String targetName = (String) it.next();
				if (process(targetName, newfileNameList.contains(targetName))) {
					this.successCnt += 1;
				}
			}

			writeShellFile();
		} catch (Exception e) {
			showErrorMessage("Make Shell Failed!!\n");
		} finally {
			showLineMessage();
			showNoTagMessage("== Total : " + this.totalCnt + "   Success : " + this.successCnt + "   Fail : " + (this.totalCnt - this.successCnt) + "\n");
			showLineMessage();
		}
	}

	private boolean process(String fileName, boolean isNew) throws Exception {
		String filePath = getFilePath(fileName);

		if (filePath == null) {
			return false;
		}

		String selectKey = null;
		for (Iterator it = this.varNameMap.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = (String) this.varNameMap.get(key);

			if (filePath.startsWith(this.basePath + value)) {
				if (selectKey == null) {
					selectKey = key;
				} else if (((String) this.varNameMap.get(key)).length() > ((String) this.varNameMap.get(selectKey)).length()) {
					selectKey = key;
				}
			}
		}

		if (selectKey != null) {
			String value = (String) this.varNameMap.get(selectKey);

			String fileExt = getFileExt(fileName);
			String path = filePath.substring((this.basePath + value).length()).replace("\\", "/");

			writeShell(selectKey, path, fileName, isNew);
			copyFile(filePath, this.copyDestDirectoryName + "\\" + this.destSourceDirectoryName + "\\" + fileName);

			if ("java".equals(fileExt)) {
				writeShell(selectKey, path.replace(".java", ".class"), fileName.replace(".java", ".class"), isNew);
				String classPath = filePath.replace("\\src\\", "\\" + this.buildPath).replace(".java", ".class");
				copyFile(classPath, this.copyDestDirectoryName + "\\" + this.destSourceDirectoryName + "\\" + fileName.replace(".java", ".class"));
			}

			showOKMessage(fileName);
			return true;
		}

		return false;
	}

	private String inputDate() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		showLineMessage();
		showInfoMessage("Input Date yyyymmdd(Ex. 20110225)");
		showLineMessage();
		String input;
		do {
			showNoTagMessage(">> input Date(yyyymmdd) : ");

			input = br.readLine();
		} while ((!isNumber(input)) || (input.length() != 8));

		showNewLine();
		return input;
	}

	private void writeShellFile() throws Exception {
		String msgLine = getMessageLine("End");

		this.copyBuffer.append(msgLine);
		this.backupBuffer.append(msgLine);
		this.recoveryBuffer.append(msgLine);
		this.copyCheckBuffer.append(msgLine);
		this.recoveryCheckBuffer.append(msgLine);

		String[] fileArr = { this.shellFileNameBackup, this.shellFileNameCopy, this.shellFileNameRecovery, this.shellFileNameCopyChk, this.shellFileNameRecoveryChk };
		String[] scriptArr = { this.backupBuffer.toString(), this.copyBuffer.toString(), this.recoveryBuffer.toString(), this.copyCheckBuffer.toString(), this.recoveryCheckBuffer.toString() };
		BufferedWriter bw = null;
		try {
			int i = 0;
			for (int length = scriptArr.length; i < length; i++) {
				File file = new File(this.copyDestDirectoryName + "\\" + this.destShellDirectoryName + "\\" + fileArr[i]);
				if (file.isFile()) {
					file.delete();
				}

				bw = new BufferedWriter(new FileWriter(file));

				bw.write(scriptArr[i]);
				bw.flush();
				bw.close();
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (bw != null)
				bw.close();
		}
	}

	private boolean loadConfigFile() {
		File propFile = new File("config.properties");
		if ((!propFile.isFile()) || (!propFile.canRead())) {
			showErrorMessage("can't file open : config.properties");
			return false;
		}

		Properties configProp = null;
		try {
			configProp = new Properties();
			configProp.load(new BufferedInputStream(new FileInputStream(propFile)));
		} catch (Exception e) {
			showErrorMessage("can't read properties : config.properties");
			return false;
		}

		this.configProp = configProp;

		return true;
	}

	private String getConfigString(String key) throws Exception {
		if (this.configProp == null) {
			showErrorMessage("can't load properties");
			throw new Exception("can't load properties");
		}
		return this.configProp.getProperty(key);
	}

	private String[] getConfigStringArray(String key) throws Exception {
		if (this.configProp == null) {
			showErrorMessage("can't load properties");
			throw new Exception("can't load properties");
		}
		return this.configProp.getProperty(key).split(",");
	}

	private void init(String type) throws Exception {
		loadConfigFile();

		String[] projectTypeArr = getConfigStringArray("PROJECT_TYPE");

		boolean findType = false;
		for (int i = 0; i < projectTypeArr.length; i++) {
			if (projectTypeArr[i].equals(type)) {
				findType = true;
				break;
			}
		}

		if (!findType) {
			showErrorMessage("wrong type!! : " + type);
			throw new Exception("wrong type!! : " + type);
		}

		this.projectTypeStr = type;

		this.basePath = getConfigString(type + this.KEY_BASE_PATH);
		this.copyDir = ("$" + getConfigString(new StringBuilder(String.valueOf(type)).append(KEY_COPYDIR).toString()));
		this.backupDir = ("$" + getConfigString(new StringBuilder(String.valueOf(type)).append(KEY_BACKUPDIR).toString()));

		String[] varTypeArr = getConfigStringArray(type + KEY_VAR_TYPE);
		for (int i = 0; i < varTypeArr.length; i++) {
			String value = getConfigString(KEY_VAR_PATH + varTypeArr[i]);
			this.varNameMap.put("$" + varTypeArr[i], value);
		}

		this.baseScriptFileName = getConfigString(KEY_BASE_SHELL_FILE_NAME);
		this.shellFileNameCopy = getConfigString(KEY_COPY_SHELL_FILE_NAME);
		this.shellFileNameBackup = getConfigString(KEY_BACKUP_SHELL_FILE_NAME);
		this.shellFileNameRecovery = getConfigString(KEY_RECOVERY_SHELL_FILE_NAME);
		this.shellFileNameCopyChk = getConfigString(KEY_COPY_CHECK_SHELL_FILE_NAME);
		this.shellFileNameRecoveryChk = getConfigString(KEY_RECOVERY_CHECK_SHELL_FILE_NAME);

		this.copyDestDirectoryName = getConfigString(KEY_COPY_DEST_DIRECTORY_NAME);
		this.destSourceDirectoryName = getConfigString(KEY_DEST_SOURCE_DIRECTORY_NAME);
		this.destShellDirectoryName = getConfigString(KEY_DEST_SHELL_DIRECTORY_NAME);

		this.buildPath = getConfigString(type + KEY_BUILD_PATH);

		initCopyDirectory();

		String[] scanPathArr = getConfigStringArray(this.projectTypeStr + KEY_SCAN_PATH);
		for (int i = 0; i < scanPathArr.length; i++) {
			try {
				scanTotalFile(this.basePath + scanPathArr[i]);
			} catch (Exception e) {
				showErrorMessage("can't open Source Directory! : " + this.basePath + scanPathArr[i]);
			}
		}

		this.makeDateStr = inputDate();
		writeBaseScript();
	}

	private void initCopyDirectory() {
		deleteDirectory(new File(this.copyDestDirectoryName));

		new File(this.copyDestDirectoryName + "\\" + this.destSourceDirectoryName).mkdirs();
		new File(this.copyDestDirectoryName + "\\" + this.destShellDirectoryName).mkdirs();
	}

	private boolean isNumber(String str) {
		if ((str == null) || (str.length() < 1)) {
			return false;
		}

		char[] charArr = str.toCharArray();
		int i = 0;
		for (int length = charArr.length; i < length; i++) {
			if (!Character.isDigit(charArr[i])) {
				return false;
			}
		}

		return true;
	}

	private void writeShell(String varName, String path, String fileName, boolean isNew) {
		this.copyBuffer.append("cp -rp " + this.copyDir + "/" + fileName + " " + varName + path + "\n");
		this.copyBuffer.append("ls -al " + varName + path + "\n\n");

		this.copyCheckBuffer.append("diff " + this.copyDir + "/" + fileName + " " + varName + path + "\n\n");

		this.backupBuffer.append("cp -rp " + varName + path + " " + this.backupDir + "/" + fileName + "\n");
		this.backupBuffer.append("ls -al " + this.backupDir + "/" + fileName + "\n\n");

		if (isNew) {
			this.recoveryBuffer.append("rm -rf " + varName + path + "\n");
			this.recoveryBuffer.append("ls -al " + varName + path + "\n\n");
		} else {
			this.recoveryBuffer.append("cp -rp " + this.backupDir + "/" + fileName + " " + varName + path + "\n");
			this.recoveryBuffer.append("ls -al " + varName + path + "\n\n");
		}

		this.recoveryCheckBuffer.append("diff " + this.backupDir + "/" + fileName + " " + varName + path + "\n\n");
	}

	private String getFilePath(String fileName) throws Exception {
		String filePath = (String) this.totalFileMap.get(fileName);

		if (filePath == null) {
			showErrorMessage("can't find file : " + fileName);
			return null;
		}

		if (this.duplicationMap.containsKey(fileName)) {
			List dupList = (List) this.duplicationMap.get(fileName);
			showLineMessage();
			showInfoMessage("duplication file name : " + fileName);
			int i = 0;
			for (int length = dupList.size(); i < length; i++)
				showNoTagMessage("  (" + (i + 1) + ") : " + (String) dupList.get(i) + "\n");
			showLineMessage();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int num;
			do {
				String input;
				do {
					showNoTagMessage(">> Select Number (1 ~ " + dupList.size() + ") : ");

					input = br.readLine();
				} while (!isNumber(input));

				num = Integer.parseInt(input);
			} while ((num < 1) || (num > dupList.size()));

			showNewLine();
			return (String) dupList.get(num - 1);
		}

		return filePath;
	}

	private String getFileExt(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index < 0) {
			return fileName;
		}

		return index < fileName.length() ? fileName.substring(index + 1) : "";
	}

	private void scanTotalFile(String filePath) {
		File path = new File(filePath);
		File[] fileList = path.listFiles();

		int i = 0;
		for (int length = fileList.length; i < length; i++) {
			File file = fileList[i];

			if (file.isFile()) {
				String fileName = file.getName();

				if (this.totalFileMap.containsKey(fileName)) {
					List dupList = (List) this.duplicationMap.get(fileName);
					if (dupList == null) {
						dupList = new ArrayList();
						dupList.add((String) this.totalFileMap.get(fileName));
					}
					dupList.add(file.getPath());
					this.duplicationMap.put(fileName, dupList);
				}
				this.totalFileMap.put(fileName, file.getPath());
			} else if ((file.isDirectory()) && (!".svn".equals(file.getName())) && (!"gaeasoft".equals(file.getName()))) {
				scanTotalFile(file.getPath());
			}
		}
	}

	private List<String> getTargetFileNameList(String fileName) {
		List fileNameList = new ArrayList();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));

			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if ("".equals(line)) {
					continue;
				}
				fileNameList.add(line);
			}
		} catch (Exception e) {
			showErrorMessage("can't open file : " + fileName);

			if (br != null)
				try {
					br.close();
				} catch (Exception localException1) {
				}
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (Exception localException2) {
				}
		}
		return fileNameList;
	}

	private void writeBaseScript() throws Exception {
		File file = new File(this.baseScriptFileName);
		if ((!file.isFile()) || (!file.canRead())) {
			showErrorMessage("can't read File : " + this.baseScriptFileName);
			throw new Exception("can't read File : " + this.baseScriptFileName);
		}

		BufferedReader br = null;
		char[] fileBuffer = (char[]) null;
		try {
			fileBuffer = new char[(int) file.length()];

			br = new BufferedReader(new FileReader(file));
			br.read(fileBuffer);
		} catch (Exception e) {
			showErrorMessage("can't read File : " + this.baseScriptFileName);
			throw new Exception("can't read File : " + this.baseScriptFileName);
		} finally {
			if (br != null) {
				br.close();
			}
		}

		String baseScriptStr = new String(fileBuffer).replace("#WDATE#", this.makeDateStr);
		String msgLine = getMessageLine("Start");

		this.copyBuffer.append(baseScriptStr).append(msgLine);
		this.backupBuffer.append(baseScriptStr).append(msgLine);
		this.recoveryBuffer.append(baseScriptStr).append(msgLine);
		this.copyCheckBuffer.append(baseScriptStr).append(msgLine);
		this.recoveryCheckBuffer.append(baseScriptStr).append(msgLine);
	}

	private boolean copyFile(String srcPath, String dstPath) {
		FileChannel fIn = null;
		FileChannel fOut = null;
		try {
			fIn = new FileInputStream(srcPath).getChannel();
			fOut = new FileOutputStream(dstPath).getChannel();

			int maxCount = 10485760;
			long size = fIn.size();
			long position = 0L;

			while (position < size)
				position += fIn.transferTo(position, 10485760L, fOut);
			return true;
		} catch (Exception e) {
			showErrorMessage("faild file copy.. : " + srcPath);
			return false;
		} finally {
			if (fIn != null)
				try {
					fIn.close();
				} catch (Exception localException5) {
				}
			if (fOut != null)
				try {
					fOut.close();
				} catch (Exception localException6) {
				}
		}
	}

	private boolean deleteDirectory(File path) {
		if (!path.exists()) {
			return false;
		}

		File[] files = path.listFiles();
		int i = 0;
		for (int length = files.length; i < length; i++) {
			File file = files[i];
			if (file.isDirectory())
				deleteDirectory(file);
			else {
				file.delete();
			}
		}

		return path.delete();
	}

	private void showErrorMessage(String msg) {
		showMessage(1, msg + "\n");
	}

	private void showOKMessage(String msg) {
		showMessage(0, msg + "\n");
	}

	private void showInfoMessage(String msg) {
		showMessage(2, msg + "\n");
	}

	private void showLineMessage() {
		showMessage(3, "===============================================================================\n");
	}

	private void showNoTagMessage(String msg) {
		showMessage(3, msg);
	}

	private void showNewLine() {
		showMessage(3, "\n");
	}

	private void showMessage(int msgType, String msg) {
		String typeMsg = null;
		switch (msgType) {
		case 0:
			typeMsg = "[*  O K  *] ";
			break;
		case 1:
			typeMsg = "[* ERROR *] ";
			break;
		case 2:
			typeMsg = "[* INFO  *] ";
			break;
		default:
			typeMsg = "";
		}

		System.out.print(typeMsg + msg);
	}

	private String getMessageLine(String msg) {
		StringBuilder sb = new StringBuilder();

		sb.append("#########################################################################################################");
		sb.append("\n###### ").append(this.projectTypeStr).append("  ").append(msg);
		sb.append("\n#########################################################################################################\n\n");

		return sb.toString();
	}
}