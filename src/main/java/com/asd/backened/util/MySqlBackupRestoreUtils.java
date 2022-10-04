package com.asd.backened.util;

import java.io.File;
import java.io.IOException;

/**
 * MySQL Backup Restore Tool
 * @author Xuhao Guo and Yongyan Liu
 * @date 2022
 */
public class MySqlBackupRestoreUtils {

	/**
	 * Backup database
	 * @param host host address
	 * @param userName Database user name
	 * @param password Database password
	 *
	 *
	 * @param fileName Backup file name
	 *
	 * @param
	 * @return
	 * @throws IOException 
	 */
	public static boolean backup(String host, String userName, String password, String backupFolderPath, String fileName,
			String database) throws Exception {
		File backupFolderFile = new File(backupFolderPath);
		if (!backupFolderFile.exists()) {
			// If the directory does not exist, it needs to be recreated
			backupFolderFile.mkdirs();
		}
		if (!backupFolderPath.endsWith(File.separator) && !backupFolderPath.endsWith("/")) {
			backupFolderPath = backupFolderPath + File.separator;
		}
		// Splice Command Line Commands
		String backupFilePath = backupFolderPath + fileName;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mysqldump --opt ").append(" --add-drop-database ").append(" --add-drop-table ");
		stringBuilder.append(" -h").append(host).append(" -u").append(userName).append(" -p").append(password);
		stringBuilder.append(" --result-file=").append(backupFilePath).append(" --default-character-set=utf8 ").append(database);
		// Call external execution file Java API
		Process process = Runtime.getRuntime().exec(getCommand(stringBuilder.toString()));
		if (process.waitFor() == 0) {
			// 0 Indicates the normal termination of the county
			System.out.println("Database backed up to" + backupFilePath + "  file");
			return true;
		}
		return false;
	}

    /**
     * Restore Database
     * @param restoreFilePath Script path for database backup
     * @param host Database IP address
     * @param database Database name
     * @param userName User Name
     * @param password User Password
     * @return
     */
	public static boolean restore(String restoreFilePath, String host, String userName, String password, String database)
			throws Exception {
		File restoreFile = new File(restoreFilePath);
		if (restoreFile.isDirectory()) {
			for (File file : restoreFile.listFiles()) {
				if (file.exists() && file.getPath().endsWith(".sql")) {
					restoreFilePath = file.getAbsolutePath();
					break;
				}
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mysql -h").append(host).append(" -u").append(userName).append(" -p").append(password);
		stringBuilder.append(" ").append(database).append(" < ").append(restoreFilePath);
		try {
			Process process = Runtime.getRuntime().exec(getCommand(stringBuilder.toString()));
			if (process.waitFor() == 0) {
				System.out.println("The data has been transferred from the original file " + restoreFilePath + " import into a new database");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static String[] getCommand(String command) {
		String os = System.getProperty("os.name");  
		String shell = "/bin/bash";
		String c = "-c";
		if(os.toLowerCase().startsWith("win")){  
			shell = "cmd";
			c = "/c";
		}  
		String[] cmd = { shell, c, command };
		return cmd;
	}

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		String userName = "root";
		String password = "123456";
		String database = "mango";
		
		System.out.println("Start Backup");
		String backupFolderPath = "c:/dev/";
		String fileName = "mango.sql";
		backup(host, userName, password, backupFolderPath, fileName, database);
		System.out.println("Backup succeeded");
		
		System.out.println("Start Restore");
		String restoreFilePath = "c:/dev/mango.sql";
		restore(restoreFilePath, host, userName, password, database);
		System.out.println("Restore succeeded");

	}

}
