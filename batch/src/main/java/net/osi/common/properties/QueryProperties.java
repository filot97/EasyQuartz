package net.osi.common.properties;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "query")
public class QueryProperties implements Serializable {
	 
	private static final long serialVersionUID = 3566704842331484306L;
	
	private String mappingFiles;
	private Integer dynamicReload;
	private Boolean skipError;
	
}
