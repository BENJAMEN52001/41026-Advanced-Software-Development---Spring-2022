package com.asd.backened.controller;

import com.asd.backened.common.FileUtils;
import com.asd.backened.constants.BackupConstants;
import com.asd.backened.datasource.BackupDataSourceProperties;
import com.asd.backened.service.MysqlBackupService;
import com.asd.backened.util.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * System data backup and restore
 * @author Xuhao Guo and Yongyan Liu
 * @date 2022
 */
@RestController
@RequestMapping("/backup")
public class MySqlBackupController {

	@Autowired
	MysqlBackupService mysqlBackupService;
	@Autowired
	BackupDataSourceProperties properties;

	@GetMapping("/backup")
	public HttpResult backup() {
		String backupFodlerName = BackupConstants.DEFAULT_BACKUP_NAME + "_" + (new SimpleDateFormat(BackupConstants.DATE_FORMAT)).format(new Date());
		return backup(backupFodlerName);
	}

	private HttpResult backup(String backupFodlerName) {
		String host = properties.getHost();
		String userName = properties.getUserName();
		String password = properties.getPassword();
		String database = properties.getDatabase();
		String backupFolderPath = BackupConstants.BACKUP_FOLDER + backupFodlerName + File.separator;
		String fileName = BackupConstants.BACKUP_FILE_NAME;
		try {
			boolean success = mysqlBackupService.backup(host, userName, password, backupFolderPath, fileName, database);
			if(!success) {
				HttpResult.error("Data backup failed");
			}
		} catch (Exception e) {
			return HttpResult.error(500, e.getMessage());
		}
		return HttpResult.ok();
	}
	
	@GetMapping("/restore")
	public HttpResult restore(@RequestParam String name) throws IOException {
		String host = properties.getHost();
		String userName = properties.getUserName();
		String password = properties.getPassword();
		String database = properties.getDatabase();
		String restoreFilePath = BackupConstants.RESTORE_FOLDER + name;
		try {
			mysqlBackupService.restore(restoreFilePath, host, userName, password, database);
		} catch (Exception e) {
			return HttpResult.error(500, e.getMessage());
		}
		return HttpResult.ok();
	}
	
	@GetMapping("/findRecords")
	public HttpResult findBackupRecords() {
		if(!new File(BackupConstants.DEFAULT_RESTORE_FILE).exists()) {
			// Initial default backup file
			backup(BackupConstants.DEFAULT_BACKUP_NAME);
		}
		List<Map<String, String>> backupRecords = new ArrayList<>();
		File restoreFolderFile = new File(BackupConstants.RESTORE_FOLDER);
		if(restoreFolderFile.exists()) {
			for(File file:restoreFolderFile.listFiles()) {
				Map<String, String> backup = new HashMap<>();
				backup.put("name", file.getName());
				backup.put("title", file.getName());
				if(BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(file.getName())) {
					backup.put("title", "System default backup file");
				}
				backupRecords.add(backup);
			}
		}
		//By default, the backup will be sorted first, and then sorted by timestamp. The new backup will be placed first
		backupRecords.sort((o1, o2) -> BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(o1.get("name")) ? -1
				: BackupConstants.DEFAULT_BACKUP_NAME.equalsIgnoreCase(o2.get("name")) ? 1 : o2.get("name").compareTo(o1.get("name")));
		return HttpResult.ok(backupRecords);
	}
	
	@GetMapping("/delete")
	public HttpResult deleteBackupRecord(@RequestParam String name) {
		if(BackupConstants.DEFAULT_BACKUP_NAME.equals(name)) {   	
			return HttpResult.error("The system default backup cannot be deleted!");
		}
		String restoreFilePath = BackupConstants.BACKUP_FOLDER + name;
		try {
			FileUtils.deleteFile(new File(restoreFilePath));
		} catch (Exception e) {
			return HttpResult.error(500, e.getMessage());
		}
		return HttpResult.ok();
	}

}
