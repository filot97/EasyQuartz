package net.osi.service.impl;

import java.util.Map;

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
	public void execute(Map<?, ?> jobDataAsMap) {
		String hello = jobDataAsMap.get("say").toString();
		
		LOG.info(hello);
	}	
	
}