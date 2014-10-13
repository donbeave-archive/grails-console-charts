/*
 * Copyright 2014 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
