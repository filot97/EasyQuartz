package net.osi.service.impl;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.osi.service.ServiceExecution;

@Service
@Data
@AllArgsConstructor
public class HelloService implements ServiceExecution {
	
	private static final Logger LOG = LoggerFactory.getLogger(HelloService.class);

	@Override
	public void execute(JobDataMap jobDataAsMap) {
		String hello = jobDataAsMap.getString("say");
		
		LOG.info(hello);
	}	
	
}