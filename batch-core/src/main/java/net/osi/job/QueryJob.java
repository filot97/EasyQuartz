package net.osi.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.anyframe.query.QueryService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
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
public class QueryJob implements Job {
	
	private static final Logger LOG = LoggerFactory.getLogger(QueryJob.class);

	@Autowired
	private QueryService queryService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		@SuppressWarnings("unchecked")
		final Map<String, String> jobDataAsMap = (Map<String, String>) context.getJobDetail()
														      				  .getJobDataMap()
														      				  .get(Constants.PPROPERTY);
		
		if (LOG.isDebugEnabled())
			LOG.debug(jobDataAsMap.toString());
		
		final String sqlId = jobDataAsMap.get("sqlId");
		
		this.queryService.update(sqlId, this.buildArgs(jobDataAsMap).toArray());
	}
	
	private List<String> buildArgs(final Map<String, String> jobDataAsMap) {
		final List<String> args = new ArrayList<String>();
		
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
