package grails.plugin.console.charts.shared.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public interface ConnectedHandler extends EventHandler {

    void onConnected(ConnectedEvent event);

}
