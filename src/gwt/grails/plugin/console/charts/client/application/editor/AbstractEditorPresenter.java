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
package grails.plugin.console.charts.client.application.editor;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.TakesValue;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import grails.plugin.console.charts.client.application.AppUtils;
import grails.plugin.console.charts.client.place.NameTokens;
import grails.plugin.console.charts.client.place.ParameterTokens;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AbstractEditorPresenter extends PresenterWidget<AbstractEditorPresenter.MyView>
        implements EditorUiHandlers {

    public interface MyView extends View, HasUiHandlers<EditorUiHandlers> {

        TakesValue<String> getQueryEditor();

        TakesValue<String> getAppearanceEditor();

    }

    PlaceManager placeManager;

    AbstractEditorPresenter(final EventBus eventBus,
                            final MyView view,
                            final PlaceManager placeManager) {
        super(eventBus, view);

        this.placeManager = placeManager;

        view.setUiHandlers(this);
    }

    @Override
    public void run() {
        String query = getView().getQueryEditor().getValue();
        if (query != null && !query.equals("")) {
            PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.HOME)
                    .with(ParameterTokens.CONNECTION_STRING, URL.encodePathSegment(AppUtils.CONNECTION_STRING))
                    .with(ParameterTokens.APPEARANCE,
                            URL.encodePathSegment(AppUtils.encodeBase64(getView().getAppearanceEditor().getValue())))
                    .with(ParameterTokens.QUERY, URL.encodePathSegment(AppUtils.encodeBase64(query))).build();
            placeManager.revealPlace(request);
        }
    }

}
