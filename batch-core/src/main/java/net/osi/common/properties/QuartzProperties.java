package net.osi.common.properties;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
@Configuration
public class QuartzProperties implements Serializable {
	
	public static final String QUARTZ_PROPERTIES_PATH = "/config/application.properties";
	
	@Bean
	public Properties properties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();

		propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_PATH));
		propertiesFactoryBean.afterPropertiesSet();

		return propertiesFactoryBean.getObject();
	}	
}
