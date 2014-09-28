package grails.plugin.console.charts.shared.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class ConnectedEvent extends GwtEvent<ConnectedHandler> {

    public static void fire(final HasHandlers source, final String connectionString, final String status) {
        ConnectedEvent eventInstance = new ConnectedEvent(connectionString, status);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, ConnectedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    private static final Type<ConnectedHandler> TYPE = new GwtEvent.Type<ConnectedHandler>();

    private String connectionString;
    private String status;

    protected ConnectedEvent() {
        // Possibly for serialization.
    }

    public ConnectedEvent(final String connectionString, final String status) {
        this.connectionString = connectionString;
        this.status = status;
    }

    public static Type<ConnectedHandler> getType() {
        return TYPE;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public Type<ConnectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ConnectedHandler handler) {
        handler.onConnected(this);
    }

}
