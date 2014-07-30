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

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import org.grails.plugin.console.charts.client.application.editor.EditorDesktopPresenterWidget;
import org.grails.plugin.console.charts.client.application.top.TopDesktopPresenterWidget;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class ApplicationDesktopPresenter extends AbstractApplicationPresenter {

    private final TopDesktopPresenterWidget topPresenter;
    private final EditorDesktopPresenterWidget editorPresenter;

    @Inject
    ApplicationDesktopPresenter(final EventBus eventBus,
                                final MyView view,
                                final MyProxy proxy,
                                final TopDesktopPresenterWidget topPresenter,
                                final EditorDesktopPresenterWidget editorPresenter,
                                final PlaceManager placeManager) {
        super(eventBus, view, proxy, RevealType.RootLayout, placeManager);

        this.topPresenter = topPresenter;
        this.editorPresenter = editorPresenter;
    }

    @Override
    public void onBind() {
        super.onBind();

        setInSlot(TOP_SLOT, topPresenter);
        setInSlot(LEFT_SLOT, editorPresenter);
    }

    @Override
    protected void onReset() {
        super.onReset();

        if (query != null) {
            editorPresenter.getView().getQueryEditor().setValue(query);
        }

        if (appearance != null) {
            editorPresenter.getView().getAppearanceEditor().setValue(appearance);
        }
    }

}
