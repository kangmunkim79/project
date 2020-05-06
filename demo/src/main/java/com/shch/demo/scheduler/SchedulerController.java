package com.shch.demo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.shch.demo.license.service.LicenseService;
import com.shch.demo.userinfo.service.UserInfoService;

@Controller
public class SchedulerController {

	@Autowired
	LicenseService licenseService;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Scheduled(cron = "0 30 4 * * ?")
	public void userUpdateBatch() throws Exception {
		userInfoService.userUpdateBatch();
	}
	
	@Scheduled(cron = "0 30 13 6 5 ?")
	public void miglogFileReadWrite() throws Exception {
		licenseService.miglogFileReadWrite();
	}
	
	@Scheduled(cron = "0 8 * * * ?")
	public void logFileReadWrite() throws Exception {
		licenseService.logFileReadWrite();
	}
}
