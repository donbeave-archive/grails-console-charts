package org.grails.plugin.console.charts.client.application.connection;

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
import org.grails.plugin.console.charts.shared.ConnectionDetails;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class ConnectionDesktopView extends ModalViewWithUiHandlers<ConnectionUiHandlers> implements
        AbstractConnectionPresenter.MyView {

    interface Binder extends UiBinder<Modal, ConnectionDesktopView> {
    }

    interface EditorDriver extends SimpleBeanEditorDriver<ConnectionDetails, ConnectionDesktopView> {
    }

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

    @UiField
    TextBox mysqlHostname;

    @UiField
    IntegerBox mysqlPort;

    @UiField
    TextBox mysqlUsername;

    @UiField
    PasswordTextBox mysqlPassword;

    @UiField
    InlineCheckBox sshToggle;

    @UiField
    TextBox sshHostname;

    @UiField
    IntegerBox sshPort;

    @UiField
    TextBox sshUsername;

    @UiField
    PasswordTextBox sshPassword;

    @UiField
    Collapse sshCollapse;

    @UiField
    Button cancelButton;

    @UiField
    Button sendButton;

    @Inject
    public ConnectionDesktopView(Binder uiBinder, EventBus eventBus) {
        super(eventBus);

        initWidget(uiBinder.createAndBindUi(this));

        editorDriver.initialize(this);
    }

    @Override
    public SimpleBeanEditorDriver<ConnectionDetails, ? extends AbstractConnectionPresenter.MyView> getEditorDriver() {
        return editorDriver;
    }

    // TODO switch to ValueChangeEvent
    @UiHandler("sshToggle")
    void onSshClicked(ClickEvent event) {
        if (sshToggle.getValue()) {
            sshCollapse.show();
        } else {
            sshCollapse.hide();
        }
    }

    @UiHandler("cancelButton")
    void onCancelClicked(ClickEvent event) {
        hide();
    }

    @UiHandler("sendButton")
    void onSendClicked(ClickEvent event) {
        getUiHandlers().onSendClicked();
    }

}
