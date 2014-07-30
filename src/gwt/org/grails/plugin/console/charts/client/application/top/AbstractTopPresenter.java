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
package org.grails.plugin.console.charts.client.application.top;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import org.grails.plugin.console.charts.client.application.connection.AbstractConnectionPresenter;
import org.grails.plugin.console.charts.shared.events.ConnectedEvent;
import org.grails.plugin.console.charts.shared.events.ConnectedHandler;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AbstractTopPresenter extends PresenterWidget<AbstractTopPresenter.MyView> implements TopUiHandlers,
        ConnectedHandler {

    public interface MyView extends View, HasUiHandlers<TopUiHandlers> {

        void setConnected(Boolean success, String status);

    }

    private AbstractConnectionPresenter connectionPresenter;

    AbstractTopPresenter(final EventBus eventBus,
                         final MyView view,
                         final AbstractConnectionPresenter connectionPresenter) {
        super(eventBus, view);

        this.connectionPresenter = connectionPresenter;

        view.setUiHandlers(this);
    }

    @Override
    public void onConnectClicked() {
        addToPopupSlot(connectionPresenter);
    }

    @Override
    public void onConnected(ConnectedEvent event) {
        getView().setConnected(true, event.getStatus());
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ConnectedEvent.getType(), this);
    }

}
