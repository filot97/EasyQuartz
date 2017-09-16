package net.osi.common.properties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jobs")
public class JobsProperties implements Serializable {	
	
	private static final long serialVersionUID = 5700816342099803463L;	

	private List<Map<?, ?>> properties;
	
}
