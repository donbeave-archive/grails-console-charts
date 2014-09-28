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
package grails.plugin.console.charts.client.application.top;

import com.dianaui.universal.core.client.ui.Button;
import com.dianaui.universal.core.client.ui.Tooltip;
import com.dianaui.universal.core.client.ui.constants.ButtonType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class TopDesktopView extends ViewWithUiHandlers<TopUiHandlers> implements TopDesktopPresenterWidget.MyView {

    interface Binder extends UiBinder<Widget, TopDesktopView> {
    }

    @UiField
    Tooltip connectTooltip;

    @UiField
    Button connectButton;

    @Inject
    TopDesktopView(final Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void setConnected(Boolean success, String status) {
        connectTooltip.setText(status);

        connectButton.setType(success ? ButtonType.SUCCESS : ButtonType.DANGER);
    }

    @UiHandler("connectButton")
    void onConnectClicked(ClickEvent event) {
        getUiHandlers().onConnectClicked();
    }

}
