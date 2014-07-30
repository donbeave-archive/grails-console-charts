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
package org.grails.plugin.console.charts.client.application;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import org.grails.plugin.console.charts.client.place.NameTokens;
import org.grails.plugin.console.charts.client.place.ParameterTokens;
import org.grails.plugin.console.charts.shared.events.ConnectedEvent;
import org.grails.plugin.console.charts.shared.events.ConnectedHandler;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AbstractApplicationPresenter extends Presenter<AbstractApplicationPresenter.MyView,
        AbstractApplicationPresenter.MyProxy> implements ApplicationUiHandlers, ConnectedHandler {

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    public interface MyProxy extends ProxyPlace<AbstractApplicationPresenter> {
    }

    public interface MyView extends View, HasUiHandlers<ApplicationUiHandlers> {

        void loading();

        void view(String type, JSONObject result);

    }

    @ContentSlot
    public static final GwtEvent.Type<RevealContentHandler<?>> TYPE_SetMainContent =
            new GwtEvent.Type<RevealContentHandler<?>>();

    public static final Object TOP_SLOT = new Object();
    public static final Object LEFT_SLOT = new Object();
    public static final String DEFAULT_VIEW = "Line";

    PlaceManager placeManager;

    String query;
    String appearance;
    String view;
    JSONObject result;

    public AbstractApplicationPresenter(final EventBus eventBus,
                                        final MyView view,
                                        final MyProxy proxy,
                                        final RevealType slot,
                                        final PlaceManager placeManager) {
        super(eventBus, view, proxy, slot);

        this.placeManager = placeManager;

        view.setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        String query = request.getParameter(ParameterTokens.QUERY, null);
        String appearance = request.getParameter(ParameterTokens.APPEARANCE, null);
        String view = request.getParameter(ParameterTokens.VIEW, DEFAULT_VIEW);
        String connectionString = request.getParameter(ParameterTokens.CONNECTION_STRING, null);

        if (connectionString != null)
            AppUtils.CONNECTION_STRING = URL.decodePathSegment(connectionString);

        if (query != null && !query.equals(this.query)) {
            this.query = query;
            result = null;
        }

        if (appearance != null && !appearance.equals(this.appearance)) {
            this.appearance = appearance;
            result = null;
        }

        if (view != null && !view.equals(this.view)) {
            this.view = view;
        }
    }

    @Override
    public void onViewChanged(String view) {
        PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.HOME).with(ParameterTokens.QUERY, query)
                .with(ParameterTokens.CONNECTION_STRING, URL.encodePathSegment(AppUtils.CONNECTION_STRING))
                .with(ParameterTokens.VIEW, view).build();

        placeManager.revealPlace(request);
    }

    @Override
    public void onConnected(ConnectedEvent event) {
        AppUtils.CONNECTION_STRING = event.getConnectionString();
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ConnectedEvent.getType(), this);
    }

    @Override
    protected void onReset() {
        if (result == null) {
            if (query != null) {
                if (AppUtils.CONNECTION_STRING == null) {
                    Window.alert("Not connected to server");

                    return;
                }

                getView().loading();

                try {
                    RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, AppUtils.getDataPath() + "?query=" +
                            URL.encodeQueryString(query) + "&appearance=" + URL.encodePathSegment(appearance) +
                            "&connectionString=" + URL.encodePathSegment(AppUtils.CONNECTION_STRING));

                    rb.setCallback(new RequestCallback() {
                        @Override
                        public void onResponseReceived(Request request, Response response) {
                            JSONValue value = JSONParser.parseStrict(response.getText());
                            result = value.isObject();

                            getView().view(view, result);
                        }

                        @Override
                        public void onError(Request request, Throwable exception) {
                            Window.alert("Error occurred" + exception.getMessage());
                        }
                    });

                    rb.send();
                } catch (RequestException e) {
                    Window.alert("Error occurred" + e.getMessage());
                }
            }
        } else {
            getView().view(view, result);
        }
    }

}