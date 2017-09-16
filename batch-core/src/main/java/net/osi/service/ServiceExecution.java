package net.osi.service;

import java.util.Map;

public interface ServiceExecution {
	
	public void execute(Map<?, ?> jobDataAsMap);
}
