package net.osi.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "dashboard";

    @PostConstruct
    void init() {
        addComponent(new Label("This is a dashboard view"));
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	Notification.show(VIEW_NAME);
    }

}
