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
package grails.plugin.console.charts.client.application.share;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import grails.plugin.console.charts.client.application.AppUtils;
import grails.plugin.console.charts.shared.ShareDetails;

import java.util.logging.Logger;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AbstractSharePresenter extends PresenterWidget<AbstractSharePresenter.MyView> implements ShareUiHandlers {

    public interface MyView extends PopupView, Editor<ShareDetails>, HasUiHandlers<ShareUiHandlers> {

        void setLink(String link);

        SimpleBeanEditorDriver<ShareDetails, ? extends MyView> getEditorDriver();

    }

    interface ShareDetailsFactory extends AutoBeanFactory {

        AutoBean<ShareDetails> details();

    }

    static final ShareDetailsFactory shareFactory = GWT.create(ShareDetailsFactory.class);

    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected final PlaceManager placeManager;

    AbstractSharePresenter(final EventBus eventBus,
                           final MyView view,
                           final PlaceManager placeManager) {
        super(eventBus, view);

        this.placeManager = placeManager;

        view.setUiHandlers(this);
        view.setAutoHideOnNavigationEventEnabled(true);
    }

    @Override
    public void onGetLinkClicked(String format) {
        ShareDetails details = getView().getEditorDriver().flush();
        details.setConnectionString(AppUtils.CONNECTION_STRING);
        details.setQuery(AppUtils.QUERY);
        details.setAppearance(AppUtils.APPEARANCE);
        details.setView(AppUtils.VIEW);

        // Retrieve the AutoBean controller
        AutoBean<ShareDetails> bean = AutoBeanUtils.getAutoBean(details);

        String json = AutoBeanCodex.encode(bean).getPayload();

        try {
            RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, AppUtils.getLinkPath() + "?format=" + format);

            rb.sendRequest(json, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    JSONValue value = JSONParser.parseStrict(response.getText());
                    JSONObject result = value.isObject();

                    if (result.get("error") != null) {
                        Window.alert("Error occurred: " + result.get("error").isString().stringValue());
                        return;
                    }

                    getView().setLink(result.get("link").isString().stringValue());
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred: " + exception.getMessage());
                }
            });
        } catch (RequestException e) {
            Window.alert("Error occurred: " + e.getMessage());
        }

    }

    @Override
    protected void onReset() {
        initNewConnection();
    }

    @Override
    protected void onReveal() {
        initNewConnection();
    }

    private void initNewConnection() {
        ShareDetails details = shareFactory.details().as();
        details.setWidth(AppUtils.DEFAULT_WIDTH);
        details.setHeight(AppUtils.DEFAULT_HEIGHT);

        getView().getEditorDriver().edit(details);
    }

}
