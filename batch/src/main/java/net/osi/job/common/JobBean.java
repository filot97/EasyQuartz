package net.osi.job.common;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Component
@Data
@ToString
public class JobBean {
	
	private String jobName;
	private String jobGroup;
	private String cronExpression;
	private String description;
	private Class<?> jobClass;
	
	private Map<String, String> jobDataAsMap;
}
