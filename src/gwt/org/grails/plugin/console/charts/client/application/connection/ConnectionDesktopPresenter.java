package org.grails.plugin.console.charts.client.application.connection;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class ConnectionDesktopPresenter extends AbstractConnectionPresenter {

    @Inject
    ConnectionDesktopPresenter(final EventBus eventBus,
                               final MyView view,
                               final PlaceManager placeManager) {
        super(eventBus, view, placeManager);
    }

}
