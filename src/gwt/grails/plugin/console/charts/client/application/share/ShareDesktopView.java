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

import com.dianaui.universal.core.client.ui.*;
import com.dianaui.universal.gwtp.client.ModalViewWithUiHandlers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import grails.plugin.console.charts.shared.ShareDetails;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class ShareDesktopView extends ModalViewWithUiHandlers<ShareUiHandlers> implements
        AbstractSharePresenter.MyView {

    interface Binder extends UiBinder<Modal, ShareDesktopView> {
    }

    interface EditorDriver extends SimpleBeanEditorDriver<ShareDetails, ShareDesktopView> {
    }

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

    @UiField
    TextBox title;

    @UiField
    IntegerBox width;

    @UiField
    IntegerBox height;

    @UiField
    Collapse collapse;

    @UiField
    Anchor link;

    @UiField
    Button getLinkButton;

    @Inject
    ShareDesktopView(Binder uiBinder, EventBus eventBus) {
        super(eventBus);

        initWidget(uiBinder.createAndBindUi(this));

        editorDriver.initialize(this);
    }

    @Override
    public void setLink(String link) {
        this.link.setHref(link);
        this.link.setText(link);
        collapse.show();
    }

    @Override
    public SimpleBeanEditorDriver<ShareDetails, ? extends AbstractSharePresenter.MyView> getEditorDriver() {
        return editorDriver;
    }

    @UiHandler("getLinkButton")
    void onGetLinkButtonClicked(ClickEvent event) {
        getUiHandlers().onGetLinkClicked();
    }

}
