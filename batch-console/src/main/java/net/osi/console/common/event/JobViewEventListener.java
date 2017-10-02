package net.osi.console.common.event;

import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListener;

import com.google.common.base.Preconditions;
import com.vaadin.spring.annotation.SpringComponent;

import net.osi.console.view.JobView;

@SpringComponent
@SuppressWarnings("serial")
public class JobViewEventListener implements EventBusListener<Object> {

	@Override
	public void onEvent(Event<Object> event) {
		Object source = event.getSource();
		
		Preconditions.checkArgument(source instanceof JobView);
		
		JobView jobView = (JobView) source;
		
	}
}