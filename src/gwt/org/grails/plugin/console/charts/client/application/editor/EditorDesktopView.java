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
package org.grails.plugin.console.charts.client.application.editor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import edu.stanford.bmir.gwtcodemirror.client.GWTCodeMirror;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class EditorDesktopView extends ViewWithUiHandlers<EditorUiHandlers>
        implements EditorDesktopPresenterWidget.MyView {

    interface Binder extends UiBinder<Widget, EditorDesktopView> {
    }

    @UiField(provided = true)
    SplitLayoutPanel container;

    @UiField(provided = true)
    GWTCodeMirror queryEditor;

    @UiField(provided = true)
    GWTCodeMirror appearanceEditor;

    @Inject
    EditorDesktopView(final Binder binder) {
        container = new SplitLayoutPanel(2);
        container.setHeight("100%");
        queryEditor = new GWTCodeMirror("sql");
        appearanceEditor = new GWTCodeMirror("groovy");

        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public TakesValue<String> getQueryEditor() {
        return queryEditor;
    }

    @Override
    public TakesValue<String> getAppearanceEditor() {
        return appearanceEditor;
    }

    @UiHandler("runButton")
    void onRunClicked(ClickEvent event) {
        getUiHandlers().run();
    }

}
