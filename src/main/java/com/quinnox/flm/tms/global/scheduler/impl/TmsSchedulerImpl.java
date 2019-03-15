package com.quinnox.flm.tms.global.scheduler.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.quinnox.flm.tms.global.scheduler.TmsScheduler;

/**
 * This class is act as a scheduler class 
 */
@Component
public class TmsSchedulerImpl implements TmsScheduler{

	private static final Logger logger = LoggerFactory.getLogger(TmsSchedulerImpl.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Override
	@Scheduled(cron = "0 * * * * ?")
	public void uploadEmployeeData()	{
		
		 logger.info("Crone Job :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
		
	}
	

	@Override
	@Scheduled(fixedRate = 30000)
	public void sendEmailAfterFixedInterval()	{
		 logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
		
	}
	
	
}
