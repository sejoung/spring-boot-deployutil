package kr.co.killers.deployutil.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ShellUtil {

	public static String getFileExt(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index < 0) {
			return fileName;
		}

		return index < fileName.length() ? fileName.substring(index + 1).toLowerCase() : "";
	}

	public static void writeShell(StringBuffer copyBuffer, StringBuffer copyCheckBuffer, StringBuffer backupBuffer, StringBuffer recoveryBuffer, StringBuffer recoveryCheckBuffer, Map<String, String> classNameMap, String copyDir, String backupDir) {

		for (String key : classNameMap.keySet()) {

			String fileName = key.substring(key.lastIndexOf('/') + 1);
			String code = classNameMap.get(key);
			String fileExt = getFileExt(fileName);
			String varName = "";
			String path = key.replaceAll("/ADP_ADMIN/branches/ADP_ADMIN_UPLUSCLUB/src", "").replaceAll(fileName, "");
			boolean isNew = false;

			if ("java".equals(fileExt)) {
				varName = "";
				writeShell(copyBuffer, copyCheckBuffer, backupBuffer, recoveryBuffer, recoveryCheckBuffer, copyDir, varName, fileName, path, backupDir, isNew);
				writeShell(copyBuffer, copyCheckBuffer, backupBuffer, recoveryBuffer, recoveryCheckBuffer, copyDir, varName, fileName.replace(".java", ".class"), path, backupDir, isNew);
			} else if ("jsp".equals(fileExt)) {
				varName = "";
				writeShell(copyBuffer, copyCheckBuffer, backupBuffer, recoveryBuffer, recoveryCheckBuffer, copyDir, varName, fileName, path, backupDir, isNew);
			} else if ("xml".equals(fileExt)) {
				varName = "";
				writeShell(copyBuffer, copyCheckBuffer, backupBuffer, recoveryBuffer, recoveryCheckBuffer, copyDir, varName, fileName, path, backupDir, isNew);
			} else {

			}

		}

	}

	public static void writeShell(StringBuffer copyBuffer, StringBuffer copyCheckBuffer, StringBuffer backupBuffer, StringBuffer recoveryBuffer, StringBuffer recoveryCheckBuffer, String copyDir, String varName, String fileName, String path, String backupDir, boolean isNew) {

		String copydir = varName + path + fileName;
		String backupdir = path + fileName;

		if (isNew) {
			copyBuffer.append("mkdir -p " + varName + path + "\n");
		}

		copyBuffer.append("cp -rp " + copyDir + File.separatorChar + fileName + " " + copydir + "\n");
		copyBuffer.append("ls -al " + varName + path + "\n\n");

		copyCheckBuffer.append("diff " + copyDir + File.separatorChar + fileName + " " + copydir + "\n\n");

		backupBuffer.append("cp -rp " + varName + path + " " + backupDir + File.separatorChar + backupdir + "\n");
		backupBuffer.append("ls -al " + backupDir + File.separatorChar + backupdir + "\n\n");

		if (isNew) {
			recoveryBuffer.append("rm -rf " + copydir + "\n");
			recoveryBuffer.append("ls -al " + copydir + "\n\n");
		} else {
			recoveryBuffer.append("cp -rp " + backupDir + File.separatorChar + backupdir + " " + copydir + "\n");
			recoveryBuffer.append("ls -al " + copydir + "\n\n");
		}

		recoveryCheckBuffer.append("diff " + backupDir + File.separatorChar + backupdir + " " + copydir + "\n\n");

	}

	public static void writeShell(String dir, String shell) throws IOException {
		File file = new File(dir);
		
		if (file.isFile()) {
			file.delete();
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter(file));

		bw.write(shell);
		bw.flush();
		bw.close();
	}
}
