package net.osi.console.view;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.validation.constraints.NotNull;

import org.quartz.JobDetail;
import org.quartz.core.jmx.QuartzSchedulerMBean;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.access.MBeanProxyFactoryBean;

import com.google.common.collect.Lists;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import net.osi.console.common.jmx.QuartzDataConverter;

@SuppressWarnings("serial")
@UIScope
@SpringView(name = JobView.VIEW_NAME)
public class JobView extends VerticalLayout implements View {	
	
	public static final String VIEW_NAME = "job";	
	
	@Autowired
	private MBeanProxyFactoryBean quartzProxy;
	private QuartzSchedulerMBean quartz;
	
	private TextField search;
	private RichTextArea schedulerInfo;
	private RichTextArea temp;
	private Grid<JobDetailImpl> jobDetailGrid;
	private TabSheet jobTab;
	private TabSheet detailTab;
	
    @PostConstruct
    void init() throws Exception {    	
    	buildMainArea().buildTopComponent()
    				   .buildTabComponent()
    				   .buildTopArea()
    				   .buildTabArea()
    				   .addTab(jobTab, schedulerInfo, "Scheduler Info.", VaadinIcons.INFO_CIRCLE)
    				   .addTab(jobTab, jobDetailGrid, "Jobs", VaadinIcons.PLAY_CIRCLE)
    				   .addTab(detailTab, temp, "Detail", VaadinIcons.TOUCH);    	
    	
    	quartz = (QuartzSchedulerMBean) quartzProxy.getObject();    	
    	    	
    	TabularData jobs = quartz.getAllJobDetails();
    	List<JobDetailImpl> jobDetails = Lists.newArrayList();
		
		for (CompositeData job : (Collection<CompositeData>) jobs.values())
			jobDetails.add(QuartzDataConverter.toJobDetail(job));		
		
		jobDetailGrid.setItems(jobDetails);
		jobDetailGrid.addColumn(JobDetailImpl::getName).setCaption("Name");
		jobDetailGrid.addColumn(JobDetailImpl::getGroup).setCaption("Group");
		jobDetailGrid.addColumn(JobDetailImpl::getDescription).setCaption("Description");
		jobDetailGrid.addColumn(JobDetailImpl::isDurable).setCaption("Durability");
		jobDetailGrid.addColumn(JobDetailImpl::requestsRecovery).setCaption("Recovery");		
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	
    }
    
    private JobView buildMainArea() {
    	setStyleName("crud-template");
		setPrimaryStyleName("v-verticallayout");
		setMargin(false);
		setSpacing(false);
		setWidth("100%");
		setHeight("100%");
		
		return this;
    }
    
    private JobView buildTopArea() {
    	HorizontalLayout topArea = new HorizontalLayout();
    	
    	topArea.setStyleName("top-bar");
    	topArea.setPrimaryStyleName("v-horizontallayout");
    	topArea.setMargin(false);
		topArea.setSpacing(false);
		topArea.setWidth("100%");
		topArea.setHeight("50px");
		
		HorizontalLayout searchArea = new HorizontalLayout();		
		
		searchArea.setPrimaryStyleName("v-horizontallayout");
		searchArea.setMargin(false);
		searchArea.setSpacing(false);
		searchArea.setWidth("100%");
		searchArea.setHeight("-1px");		
		searchArea.addComponent(search);	
		searchArea.setExpandRatio(search, 1.f);
		searchArea.setComponentAlignment(search, Alignment.MIDDLE_LEFT);
		
		topArea.addComponent(searchArea);
		topArea.setComponentAlignment(searchArea, Alignment.MIDDLE_LEFT);				
    	
    	addComponent(topArea);
		
    	return this;
    }
    
    private JobView buildTopComponent() {
    	search = new TextField();
    	
    	search.addStyleName(ValoTheme.TEXTFIELD_SMALL);
    	search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);    	
    	search.setPrimaryStyleName("v-textfield");
    	search.setWidth("20%");
    	search.setHeight("-1px");
    	search.setPlaceholder("Search");
    	search.setIcon(VaadinIcons.SEARCH);
    	
    	return this;
    }
    
    private JobView buildTabComponent() {
    	jobTab = new TabSheet();
    	
    	jobTab.setPrimaryStyleName("v-tabsheet");
    	jobTab.setWidth("100%");
    	jobTab.setHeight("100%");
    	
    	detailTab = new TabSheet();
    	
    	detailTab.setPrimaryStyleName("v-tabsheet");
    	detailTab.setWidth("100%");
    	detailTab.setHeight("100%");
    	
    	schedulerInfo = new RichTextArea();
    	schedulerInfo.setWidth("100%");
    	schedulerInfo.setHeight("-1px");
    	
    	temp = new RichTextArea();
    	
    	jobDetailGrid = new Grid<>();
    	
    	jobDetailGrid.setSizeFull();
    	jobDetailGrid.setPrimaryStyleName("v-grid");    	
    	
    	return this;
    }
    
    private JobView buildTabArea() {
    	CssLayout mainArea = new CssLayout();
    	
    	mainArea.setStyleName("content");
    	mainArea.setPrimaryStyleName("v-csslayout");
    	mainArea.setWidth("100%");
    	mainArea.setHeight("100%"); 
    	
    	VerticalLayout tabArea = new VerticalLayout();
    	
    	tabArea.setStyleName("content");
    	tabArea.setPrimaryStyleName("v-verticallayout");
    	tabArea.setWidth("100%");
    	tabArea.setHeight("100%");
    	tabArea.setMargin(true);
    	tabArea.setSpacing(true);
    	
    	tabArea.addComponent(jobTab);
    	tabArea.setExpandRatio(jobTab, .5f);    	
    	tabArea.addComponent(detailTab);
    	tabArea.setExpandRatio(detailTab, .5f);
    	
    	mainArea.addComponent(tabArea);
    	
    	addComponent(mainArea);
    	setExpandRatio(mainArea, 1.f);
    	
    	return this;
    }
    
    private JobView addTab(@NotNull final TabSheet sheet,
    					   @NotNull final Component component,
    					   @NotNull final String caption,
    					   @NotNull final Resource icon) {
    	
    	sheet.addTab(component, caption, icon);    	
    	
    	return this;
    }
}
