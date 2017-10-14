package net.osi.console.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.quartz.JobDataMap;
import org.quartz.core.jmx.QuartzSchedulerMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.access.MBeanProxyFactoryBean;

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
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lombok.EqualsAndHashCode;
import net.osi.console.common.quartz.DataConverter;
import net.osi.console.common.quartz.JobDetailEx;
import net.osi.console.common.quartz.KeyValue;

@SuppressWarnings("serial")
@UIScope
@SpringView(name = TriggerView.VIEW_NAME)
@EqualsAndHashCode
public class TriggerView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "trigger";
	
	@Autowired
	private MBeanProxyFactoryBean quartzProxy;
	private QuartzSchedulerMBean quartz;
	
	private TextField search;	
	private Grid<KeyValue> jobDataGrid;
	private Grid<JobDetailEx> jobDetailGrid;
	private TabSheet jobTab;
	private TabSheet detailTab;
	
    @PostConstruct
    protected void init() throws Exception {
    	quartz = (QuartzSchedulerMBean) quartzProxy.getObject();
    	
    	buildMainArea().buildTopComponent()
    				   .buildTabComponent()
    				   .buildTopArea()
    				   .buildTabArea()
    				   .addTab(jobTab, jobDetailGrid, "Jobs", VaadinIcons.PLAY_CIRCLE)
    				   .addTab(detailTab, jobDataGrid, "Job Data", VaadinIcons.TOUCH)
    				   .buildJobDetailGrid();
    	
    	jobDetailGrid.addItemClickListener(event -> {
    		if (event.getMouseEventDetails().isDoubleClick())
    			return;
    		
    		showJobDataGrid(event.getItem().getJobDataMap());
    	});
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	
    }
    
    private TriggerView buildMainArea() {
    	setStyleName("crud-template");
		setPrimaryStyleName("v-verticallayout");
		setMargin(false);
		setSpacing(false);
		setWidth("100%");
		setHeight("100%");
		
		return this;
    }
    
    private TriggerView buildTopArea() {
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
    
    private TriggerView buildTopComponent() {
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
    
    private TriggerView buildTabComponent() {
    	jobTab = new TabSheet();
    	
    	jobTab.setPrimaryStyleName("v-tabsheet");
    	jobTab.setWidth("100%");
    	jobTab.setHeight("100%");
    	
    	detailTab = new TabSheet();
    	
    	detailTab.setPrimaryStyleName("v-tabsheet");
    	detailTab.setWidth("100%");
    	detailTab.setHeight("100%");
    	
    	jobDataGrid = new Grid<>();
    	
    	jobDataGrid.setSizeFull();
    	jobDataGrid.setPrimaryStyleName("v-grid");
    	
    	jobDetailGrid = new Grid<>();
    	
    	jobDetailGrid.setSizeFull();
    	jobDetailGrid.setPrimaryStyleName("v-grid");    	
    	
    	return this;
    }
    
    private TriggerView buildTabArea() {
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
    
    private TriggerView addTab(@NotNull final TabSheet sheet,
    					   @NotNull final Component component,
    					   @NotNull final String caption,
    					   @NotNull final Resource icon) {
    	
    	sheet.addTab(component, caption, icon);
    	
    	return this;
    }
    
	private TriggerView buildJobDetailGrid() throws Exception {
		List<JobDetailEx> jobDetails = DataConverter.toJobDetails(quartz.getAllJobDetails());
		
		jobDetailGrid.setItems(jobDetails);
		jobDetailGrid.addColumn(JobDetailEx::getName).setCaption("Name");
		jobDetailGrid.addColumn(JobDetailEx::getGroup).setCaption("Group");
		jobDetailGrid.addColumn(JobDetailEx::getDescription).setCaption("Description");
		jobDetailGrid.addColumn(JobDetailEx::getJobClassName).setCaption("Job Class");
		jobDetailGrid.addColumn(JobDetailEx::isDurable).setCaption("Durability");
		jobDetailGrid.addColumn(JobDetailEx::requestsRecovery).setCaption("Recovery");
		jobDetailGrid.addColumn(JobDetailEx::isConcurrentExectionDisallowed).setCaption("Concurrence");
		
		return this;
	}
	
	private void showJobDataGrid(JobDataMap jobDataMap) {
		List<KeyValue> keyValues = DataConverter.toKeyValues(jobDataMap);		
		
		jobDataGrid.removeAllColumns();
		
		jobDataGrid.setItems(keyValues);
		jobDataGrid.addColumn(KeyValue::getKey).setCaption("Key");
		jobDataGrid.addColumn(KeyValue::getValue).setCaption("Value");
	}
}