package com.asd.backened.service;

import java.io.IOException;

/**
 * MySql Command line backup recovery service
 * @author Xuhao Guo and Yongyan Liu
 * @date 2022
 */
public interface MysqlBackupService {

	/**
	 * Backup database
	 * @param host host address
	 * @param userName Database username
	 * @param password Database password
	 * @param
	 * @param fileName Database file name
	 * @param
	 * @return
	 * @throws IOException 
	 */
	boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String database) throws Exception;

    /**
     * Recover Database
     * @param restoreFilePath Database backup script path
     * @param host IP address
     * @param database Database name
     * @param userName username
     * @param password password
     * @return
     */
	boolean restore(String restoreFilePath, String host, String userName, String password, String database) throws Exception;

}
