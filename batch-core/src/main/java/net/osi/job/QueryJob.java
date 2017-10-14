package net.osi.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.anyframe.query.QueryService;
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

@Service
@Transactional
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QueryJob implements Job {
	
	private static final Logger LOG = LoggerFactory.getLogger(QueryJob.class);

	@Autowired
	private QueryService queryService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		if (LOG.isDebugEnabled())
			LOG.debug(jobDataMap.toString());
		
		final String sqlId = jobDataMap.getString("sqlId");
		
		this.queryService.update(sqlId, this.buildArgs(jobDataMap.getWrappedMap()).toArray());
	}
	
	private List<String> buildArgs(final Map<String, Object> jobDataAsMap) {
		final List<String> args = new ArrayList<>();
		
		if (jobDataAsMap == null)
			return args;
		
		for (final String key : jobDataAsMap.keySet()) {
			final StringBuilder builder = new StringBuilder();
			
			builder.append(key);
			builder.append("=");
			builder.append(jobDataAsMap.get(key));
			
			args.add(builder.toString());
		}
		
		return args;
	}
}
