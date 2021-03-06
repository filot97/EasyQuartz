package net.osi.console.view;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
@EqualsAndHashCode
public class MainView extends HorizontalLayout {
	
	private Navigator navigator;

	@Autowired
	private SpringViewProvider viewProvider;	
	
	private CssLayout content;
	private Button schedulerInfoMenu;
	private Button jobDetailMenu;
	private Button triggerMenu;
	private Label viewTitle;
	
	@PostConstruct
	protected void init() {
		buildMainArea().buildMenuComponent()
					   .buildContentComponent()
					   .buildMenuArea()
					   .buildContentArea()
					   .buildNavigator(UI.getCurrent(), content)
					   .addView(SchedulerInfoView.VIEW_NAME, new SchedulerInfoView())
					   .addClickListener(schedulerInfoMenu, SchedulerInfoView.VIEW_NAME)
					   .addView(JobView.VIEW_NAME, new JobView())
					   .addClickListener(jobDetailMenu, JobView.VIEW_NAME)
					   .addView(TriggerView.VIEW_NAME, new TriggerView())
					   .addClickListener(triggerMenu, TriggerView.VIEW_NAME)
					   .navigateTo(SchedulerInfoView.VIEW_NAME);
	}
	
	private MainView buildMainArea() {
		setStyleName("app-shell");
		setPrimaryStyleName("v-horizontallayout");
		setMargin(false);
		setSpacing(false);
		setWidth("100%");
		setHeight("100%");
		
		return this;
	}	
	
	private MainView buildMenuArea() {
		CssLayout menuArea = new CssLayout();
		
		menuArea.setStyleName("navigation-bar-container");
		menuArea.setPrimaryStyleName("v-csslayout");
		menuArea.setWidth("200px");
		menuArea.setHeight("100%");
		
		menuArea.addComponent(buildMenu());
		
		addComponent(menuArea);
		
		return this;
	}
	
	private MainView buildContentArea() {
		VerticalLayout contentArea = new VerticalLayout();
		
		contentArea.setStyleName("content-container");
		contentArea.setPrimaryStyleName("v-verticallayout");
		contentArea.setWidth("100%");
		contentArea.setHeight("100%");
		contentArea.setSpacing(false);
		contentArea.setMargin(false);		
		
		contentArea.addComponent(viewTitle);
		contentArea.setComponentAlignment(viewTitle, Alignment.TOP_LEFT);		
		
		contentArea.addComponent(content);		
		contentArea.setExpandRatio(content, 1.f);
		
		addComponent(contentArea);
		setExpandRatio(contentArea, 1.f);
		
		return this;
	}
	
	private MainView buildMenuComponent() {
		schedulerInfoMenu = new Button("Scheduler Info", VaadinIcons.GRID_BIG);
		
		schedulerInfoMenu.setStyleName("borderless");
		schedulerInfoMenu.setPrimaryStyleName("v-button");
		schedulerInfoMenu.setWidth("100%");
		schedulerInfoMenu.setHeight("-1px");
		
		jobDetailMenu = new Button("Job Detail", VaadinIcons.GROUP);
		
		jobDetailMenu.setStyleName("borderless");
		jobDetailMenu.setPrimaryStyleName("v-button");
		jobDetailMenu.setWidth("100%");
		jobDetailMenu.setHeight("-1px");
		
		triggerMenu = new Button("Trigger", VaadinIcons.AIRPLANE);
		
		triggerMenu.setStyleName("borderless");
		triggerMenu.setPrimaryStyleName("v-button");
		triggerMenu.setWidth("100%");
		triggerMenu.setHeight("-1px");
		
		return this;
	}
	
	private MainView buildContentComponent() {
		viewTitle = new Label(""); 
		
		viewTitle.setStyleName("view-title");
		viewTitle.setPrimaryStyleName("v-label");
		viewTitle.setWidth("100%");
		viewTitle.setHeight("-1px");
		
		content = new CssLayout();
		
		content.setStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		content.setPrimaryStyleName("v-csslayout");
		content.setWidth("100%");
		content.setHeight("100%");
		
		return this;
	}
	
	private MainView buildNavigator(@NotNull final UI ui, @NotNull final ComponentContainer container) {
		navigator = new Navigator(ui, container);		
		
		navigator.addProvider(viewProvider);
		
		return this;
	}
	
	private MainView addView(@NotNull final String viewName, @NotNull final View view) {
		navigator.addView(viewName, view);		
		
		return this;
	}
	
	private void navigateTo(@NotNull final String viewName) {
		navigator.navigateTo(viewName);
	}

	private CssLayout buildMenu() {
		CssLayout area = new CssLayout();
		
		area.setStyleName("navigation-bar");
		area.setPrimaryStyleName("v-csslayout");
		area.setWidth("100%");
		area.setHeight("100%");
		
		Label title = new Label("Quartz Menu");
		
		title.setStyleName("logo");
		title.setPrimaryStyleName("v-label");
		title.setWidth("100%");
		title.setHeight("-1px");
		
		area.addComponent(title);
		
		CssLayout menu = new CssLayout();
		
		menu.setStyleName("navigation");
		menu.setPrimaryStyleName("v-csslayout");
		menu.setWidth("100%");
		menu.setHeight("-1px");	
		
		menu.addComponent(schedulerInfoMenu);
		menu.addComponent(jobDetailMenu);
		menu.addComponent(triggerMenu);
		
		area.addComponent(menu);
		
		return area;
	}
	
	private MainView addClickListener(@NotNull Button button, @NotNull String viewName) {
		button.addClickListener(event -> {
			navigator.navigateTo(viewName);
			
			viewTitle.setValue(event.getButton().getCaption());
		});
		
		return this;
	}
}