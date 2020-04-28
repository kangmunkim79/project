package com.shch.demo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.shch.demo.license.service.LicenseService;

@Controller
public class SchedulerController {

	@Autowired
	LicenseService licenseService;
	
	@Scheduled(cron = "0 0 16 28 4 ?")
	public void miglogFileReadWrite()  throws Exception {
		licenseService.miglogFileReadWrite();
	}
	
	//@Scheduled(cron = "0 05 09 1/1 * ?")
	public void logFileReadWrite()  throws Exception {
		//licenseService.logFileReadWrite();
	}
}
