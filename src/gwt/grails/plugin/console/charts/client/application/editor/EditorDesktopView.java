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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import edu.stanford.bmir.gwtcodemirror.client.GWTCodeMirror;

import static com.google.gwt.query.client.GQuery.$;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class EditorDesktopView extends ViewWithUiHandlers<EditorUiHandlers>
        implements EditorDesktopPresenterWidget.MyView {

    interface Binder extends UiBinder<Widget, EditorDesktopView> {
    }

    static final int MENU_SIZE = 33;

    @UiField(provided = true)
    SplitLayoutPanel container;

    @UiField
    DockLayoutPanel topPanel;

    @UiField(provided = true)
    GWTCodeMirror queryEditor;

    @UiField(provided = true)
    GWTCodeMirror appearanceEditor;
    @UiField
    FlowPanel appearanceContainer;

    boolean mouseDown = false;

    @Inject
    EditorDesktopView(final Binder binder) {
        container = new SplitLayoutPanel(2) {
            @Override
            public void onBrowserEvent(Event event) {
                switch (event.getTypeInt()) {
                    case Event.ONMOUSEDOWN:
                        mouseDown = true;
                        break;

                    case Event.ONMOUSEUP:
                        mouseDown = false;
                        break;

                    case Event.ONMOUSEMOVE:
                        if (mouseDown) {
                            autoHeight();
                        }
                        break;
                }
                super.onBrowserEvent(event);
            }
        };
        queryEditor = new GWTCodeMirror("sql");
        appearanceEditor = new GWTCodeMirror("groovy", "lesser-dark");

        initWidget(binder.createAndBindUi(this));

        container.setWidgetMinSize(topPanel, MENU_SIZE);

        container.sinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEUP | Event.ONMOUSEMOVE);

        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                autoHeight();
            }
        });

        asWidget().addAttachHandler(new AttachEvent.Handler() {
            @Override
            public void onAttachOrDetach(AttachEvent event) {
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        autoHeight();
                    }
                };

                timer.schedule(300);
            }
        });
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

    private void autoHeight() {
        int querySize = topPanel.getOffsetHeight() - MENU_SIZE;
        $(queryEditor).children(".CodeMirror-wrap").css("height", querySize + "px");
        $(queryEditor).children(".CodeMirror-sizer").css("height", querySize + "px");
        $(queryEditor).children(".CodeMirror-gutters").css("height", querySize + "px");
        $(queryEditor).children(".CodeMirror-linenumbers").css("height", querySize + "px");

        int appearanceSize = appearanceContainer.getOffsetHeight();
        $(appearanceEditor).children(".CodeMirror-wrap").css("height", appearanceSize + "px");
        $(appearanceEditor).children(".CodeMirror-sizer").css("height", appearanceSize + "px");
        $(appearanceEditor).children(".CodeMirror-gutters").css("height", appearanceSize + "px");
        $(appearanceEditor).children(".CodeMirror-linenumbers").css("height", appearanceSize + "px");
    }

}
