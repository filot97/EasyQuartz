package net.osi.common.factory;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.anyframe.query.impl.QueryServiceImpl;
import org.anyframe.query.impl.config.loader.MappingXMLLoader;
import org.anyframe.query.impl.jdbc.PagingJdbcTemplate;
import org.anyframe.query.impl.jdbc.generator.OraclePagingSQLGenerator;
import org.anyframe.query.impl.util.RawSQLExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import net.osi.common.properties.QueryProperties;

@Configuration
public class QueryBeanFactory {

	@Autowired
	private QueryProperties queryProperties;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PagingJdbcTemplate jdbcTemplate;

	@Autowired
	private OraclePagingSQLGenerator pagingSQLGenerator;

	@Autowired
	private RawSQLExceptionTranslator exceptionTranslator;

	@Autowired
	private DefaultLobHandler lobHandler;

	@Autowired
	private MappingXMLLoader sqlLoader;

	@Bean
	public PagingJdbcTemplate jdbcTemplate() {
		PagingJdbcTemplate jdbcTemplate = new PagingJdbcTemplate();

		jdbcTemplate.setDataSource(dataSource);
		jdbcTemplate.setExceptionTranslator(exceptionTranslator);

		return jdbcTemplate;
	}
	
	@Bean
	public OraclePagingSQLGenerator pagingSQLGenerator() {
		return new OraclePagingSQLGenerator();
	}

	@Bean
	public RawSQLExceptionTranslator exceptionTranslator() {
		return new RawSQLExceptionTranslator();
	}

	@Bean
	public DefaultLobHandler lobHanlder() {
		DefaultLobHandler lobHandler = new DefaultLobHandler();
		lobHandler.setWrapAsLob(true);
		return lobHandler;
	}

	@Bean
	public MappingXMLLoader sqlLoader() {
		MappingXMLLoader sqlLoader = new MappingXMLLoader();

		sqlLoader.setMappingFiles(queryProperties.getMappingFiles());

		Map<String, String> nullchecks = new HashMap<>();
		nullchecks.put("VARCHAR", "");

		sqlLoader.setNullchecks(nullchecks);
		
		Integer dynamicReload = queryProperties.getDynamicReload(); 
		
		if (dynamicReload != null)
			sqlLoader.setDynamicReload(queryProperties.getDynamicReload());
		
		sqlLoader.setSkipError(this.queryProperties.getSkipError());

		return sqlLoader;
	}

	@Bean
	public QueryServiceImpl queryService() {
		QueryServiceImpl queryService = new QueryServiceImpl();
		
		queryService.setJdbcTemplate(jdbcTemplate);
		queryService.setPagingSQLGenerator(pagingSQLGenerator);
		queryService.setSqlRepository(sqlLoader);
		queryService.setLobHandler(lobHandler);

		return queryService;
	}

}
