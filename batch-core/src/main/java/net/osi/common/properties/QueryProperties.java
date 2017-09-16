package net.osi.common.properties;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Component
@ConfigurationProperties(prefix = "query")
public class QueryProperties implements Serializable {
	 
	private String mappingFiles;
	private Integer dynamicReload;
	private Boolean skipError;
	
}
