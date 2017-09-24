package net.osi.console.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.access.MBeanProxyFactoryBean;

import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import net.osi.console.design.MainViewDesign;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class MainVew extends MainViewDesign {

	private Navigator navigator;

	@Autowired
	private SpringViewProvider viewProvider;
	
	@Autowired
	private MBeanProxyFactoryBean quartzProxy;

	@PostConstruct
	protected void init() {				
		navigator = new Navigator(UI.getCurrent(), content);

		navigator.addProvider(viewProvider);
		navigator.addView(DashboardView.VIEW_NAME, new DashboardView());
		navigator.addView(SchedulerStateView.VIEW_NAME, new SchedulerStateView());
		navigator.addView(CronTriggersView.VIEW_NAME, new CronTriggersView());
		navigator.addView(FiredTriggersView.VIEW_NAME, new FiredTriggersView());
		navigator.addView(JobDetailsView.VIEW_NAME, new JobDetailsView());
		navigator.addView(TriggersView.VIEW_NAME, new TriggersView());
		navigator.setErrorView(ErrorView.class);

		dashboard.addClickListener(event -> {
			navigator.navigateTo(DashboardView.VIEW_NAME);
			viewTitle.setValue(event.getButton().getCaption());			
		});

		schedulerState.addClickListener(event -> {
			navigator.navigateTo(SchedulerStateView.VIEW_NAME);
			viewTitle.setValue(event.getButton().getCaption());
		});

		cronTriggers.addClickListener(event -> {
			navigator.navigateTo(CronTriggersView.VIEW_NAME);
			viewTitle.setValue(event.getButton().getCaption());
		});

		firedTriggers.addClickListener(event -> {
			navigator.navigateTo(FiredTriggersView.VIEW_NAME);
			viewTitle.setValue(event.getButton().getCaption());
		});

		jobDetails.addClickListener(event -> {
			navigator.navigateTo(JobDetailsView.VIEW_NAME);
			viewTitle.setValue(event.getButton().getCaption());
		});

		triggers.addClickListener(event -> {
			navigator.navigateTo(TriggersView.VIEW_NAME);
			viewTitle.setValue(event.getButton().getCaption());
		});

		navigator.navigateTo(DashboardView.VIEW_NAME);
	}
}