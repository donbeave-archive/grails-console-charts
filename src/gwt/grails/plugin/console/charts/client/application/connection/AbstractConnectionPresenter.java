package grails.plugin.console.charts.client.application.connection;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import grails.plugin.console.charts.client.application.AppUtils;
import grails.plugin.console.charts.shared.ConnectStatus;
import grails.plugin.console.charts.shared.ConnectionDetails;
import grails.plugin.console.charts.shared.events.ConnectedEvent;

import java.util.logging.Logger;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AbstractConnectionPresenter extends PresenterWidget<AbstractConnectionPresenter.MyView> implements
        ConnectionUiHandlers {

    public interface MyView extends PopupView, Editor<ConnectionDetails>, HasUiHandlers<ConnectionUiHandlers> {

        SimpleBeanEditorDriver<ConnectionDetails, ? extends MyView> getEditorDriver();

    }

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
            RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, AppUtils.getConnectPath() + "?data=" +
                    URL.encodeQueryString(json));

            rb.setCallback(new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    ConnectStatus status = AutoBeanCodex.decode(AppUtils.BEAN_FACTORY, ConnectStatus.class,
                            response.getText()).as();

                    if (status.isConnected()) {
                        AppUtils.CONNECT_STATUS = status;

                        ConnectedEvent.fire(AbstractConnectionPresenter.this, status.getConnectionString(),
                                status.getStatus());

                        getView().hide();
                    } else {
                        String error = (status.getException() != null ? " (" + status.getException() + ") " : "") +
                                status.getError();
                        Window.alert("Error occurred: " + error);
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error occurred: " + exception.getMessage());
                }
            });

            rb.send();
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
        ConnectionDetails details = AppUtils.BEAN_FACTORY.details().as();
        details.setSshToggle(false);

        getView().getEditorDriver().edit(details);
    }

}
