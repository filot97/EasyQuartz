package net.osi.console.view;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@UIScope
@SpringView(name = FiredTriggersView.VIEW_NAME)
public class FiredTriggersView extends VerticalLayout implements View {	
	
	public static final String VIEW_NAME = "firedTriggers";

    @PostConstruct
    void init() {
        
    }

    @Override
    public void enter(ViewChangeEvent event) {
    	
    }

}
