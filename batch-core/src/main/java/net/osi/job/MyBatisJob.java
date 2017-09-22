package net.osi.job;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.osi.common.Constants;

@Service
@Transactional
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyBatisJob implements Job {
	
	private static final Logger LOG = LoggerFactory.getLogger(QueryJob.class);
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {		
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		if (LOG.isDebugEnabled())
			LOG.debug(jobDataMap.toString());
		
		final String sqlId = jobDataMap.getString("sqlId");
		final String sqlType = jobDataMap.getString("sqlType");

		try (SqlSession session = this.sqlSessionFactory.openSession()) {		
			switch (sqlType) {
			case Constants.MYBATIS.SQL_TYPE_INSERT:			
				session.insert(sqlId, jobDataMap.getWrappedMap());				
				break;
	
			case Constants.MYBATIS.SQL_TYPE_UPDATE:
				session.update(sqlId, jobDataMap.getWrappedMap());				
				break;		
			
			case Constants.MYBATIS.SQL_TYPE_DELETE:
				session.delete(sqlId, jobDataMap.getWrappedMap());				
				break;
			}
			
			session.commit();
		}
	}
}
