package org.grails.plugin.console.charts.client.application.connection;

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
import org.grails.plugin.console.charts.client.application.AppUtils;
import org.grails.plugin.console.charts.shared.ConnectionDetails;
import org.grails.plugin.console.charts.shared.events.ConnectedEvent;

import java.util.logging.Logger;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AbstractConnectionPresenter extends PresenterWidget<AbstractConnectionPresenter.MyView> implements
        ConnectionUiHandlers {

    public interface MyView extends PopupView, Editor<ConnectionDetails>, HasUiHandlers<ConnectionUiHandlers> {

        SimpleBeanEditorDriver<ConnectionDetails, ? extends MyView> getEditorDriver();

    }

    interface ConnectionDetailsFactory extends AutoBeanFactory {

        AutoBean<ConnectionDetails> details();

    }

    static final ConnectionDetailsFactory connectionFactory = GWT.create(ConnectionDetailsFactory.class);

    protected final Logger logger = Logger.getLogger(getClass().getName());
    protected final PlaceManager placeManager;

    AbstractConnectionPresenter(final EventBus eventBus,
                                final MyView view,
                                final PlaceManager placeManager) {
        super(eventBus, view);

        this.placeManager = placeManager;

        view.setUiHandlers(this);
        view.setAutoHideOnNavigationEventEnabled(true);
    }

    @Override
    public void onSendClicked() {
        ConnectionDetails details = getView().getEditorDriver().flush();

        // Retrieve the AutoBean controller
        AutoBean<ConnectionDetails> bean = AutoBeanUtils.getAutoBean(details);

        String json = AutoBeanCodex.encode(bean).getPayload();

        try {
            RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, AppUtils.getConnectPath() + "?connectData=" +
                    URL.encodeQueryString(json));

            rb.setCallback(new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    JSONValue value = JSONParser.parseStrict(response.getText());
                    JSONObject result = value.isObject();

                    Boolean connected = result.get("connected").isBoolean().booleanValue();

                    if (connected) {
                        String connectionString = result.get("connectionString").isString().stringValue();
                        String status = result.get("status").isString().stringValue();

                        ConnectedEvent.fire(AbstractConnectionPresenter.this, connectionString, status);

                        getView().hide();
                    } else {
                        String error = result.get("error").isString().stringValue();
                        String exception = result.get("exception") != null ?
                                result.get("exception").isString().stringValue() : null;

                        Window.alert("Error occurred" + (exception != null ? " (" + exception + ") " : "") + error);
                    }
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

    @Override
    protected void onReset() {
        initNewConnection();
    }

    @Override
    protected void onReveal() {
        initNewConnection();
    }

    private void initNewConnection() {
        ConnectionDetails details = connectionFactory.details().as();
        details.setSshToggle(false);

        getView().getEditorDriver().edit(details);
    }

}
