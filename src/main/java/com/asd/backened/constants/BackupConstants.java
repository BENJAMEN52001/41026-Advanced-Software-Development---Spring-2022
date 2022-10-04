package com.asd.backened.constants;

import java.io.File;

/**
 * Constant class
 * @author Xuhao Guo and Yongyan Liu
 * @date 2022
 */
public interface BackupConstants {
	
	/** Backup directory name */
	public static final String BACKUP_FOLDER_NAME = "41026backfunction";
	/** Backup directory */
//	public static final String BACKUP_FOLDER = System.getProperty("user.home") + File.separator + BACKUP_FOLDER_NAME + File.separator;
	public static final String BACKUP_FOLDER = "D:\\backup" + File.separator + BACKUP_FOLDER_NAME + File.separator;
	/** Restore directory defaults to backup directory */
	public static final String RESTORE_FOLDER = BACKUP_FOLDER;
	/** Date Format */
	public static final String DATE_FORMAT = "yyyy-MM-dd_HHmmss";
	/** SQL, extension*/
	public static final String SQL_EXT = ".sql";
	/** Default backup file*/
	public static final String BACKUP_FILE_NAME = "41026" + SQL_EXT;
	/** Default Backup Restore Directory Name */
	public static final String DEFAULT_BACKUP_NAME = "backup";
	/** Default Backup Restore File */
	public static final String DEFAULT_RESTORE_FILE = BACKUP_FOLDER + DEFAULT_BACKUP_NAME + File.separator + BACKUP_FILE_NAME;
	
}
