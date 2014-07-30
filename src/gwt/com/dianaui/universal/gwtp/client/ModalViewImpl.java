package com.dianaui.universal.gwtp.client;


import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PopupViewCloseHandler;
import com.gwtplatform.mvp.client.PopupViewImpl;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.NavigationEvent;
import com.gwtplatform.mvp.client.proxy.NavigationHandler;
import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.ui.Modal;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public abstract class ModalViewImpl extends ViewImpl implements PopupView {

    private final EventBus eventBus;
    private PopupViewCloseHandler popupViewCloseHandler;
    private HandlerRegistration autoHideHandler;
    private HandlerRegistration closeHandlerRegistration;

    /**
     * The {@link PopupViewImpl} class uses the {@link EventBus} to listen to
     * {@link NavigationEvent} in order to automatically close when this event
     * is fired, if desired. See
     * {@link #setAutoHideOnNavigationEventEnabled(boolean)} for details.
     *
     * @param eventBus The {@link EventBus}.
     */
    protected ModalViewImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public boolean isShowing() {
        return asModal().isViewing();
    }

    @Override
    public void center() {
        asModal().show();
    }

    @Override
    public void hide() {
        asModal().hide();

        popupViewCloseHandler = null;

        // remove close handler
        if (closeHandlerRegistration != null) {
            closeHandlerRegistration.removeHandler();
            closeHandlerRegistration = null;
        }
    }

    @Override
    public void setAutoHideOnNavigationEventEnabled(boolean autoHide) {
        if (autoHide) {
            if (autoHideHandler != null) {
                return;
            }

            autoHideHandler = eventBus.addHandler(NavigationEvent.getType(), new NavigationHandler() {
                @Override
                public void onNavigation(NavigationEvent navigationEvent) {
                    if (popupViewCloseHandler != null) {
                        popupViewCloseHandler.onClose();
                    } else {
                        hide();
                    }
                }
            });
        } else {
            if (autoHideHandler != null) {
                autoHideHandler.removeHandler();
            }
        }
    }

    @Override
    public void setCloseHandler(final PopupViewCloseHandler popupViewCloseHandler) {
        this.popupViewCloseHandler = popupViewCloseHandler;

        if (closeHandlerRegistration != null) {
            closeHandlerRegistration.removeHandler();
        }
        if (popupViewCloseHandler == null) {
            closeHandlerRegistration = null;
        } else {
            closeHandlerRegistration = asModal().addHandler(new HideHandler() {
                @Override
                public void onHide(HideEvent hideEvent) {
                    popupViewCloseHandler.onClose();
                }
            }, HideEvent.getType());
        }
    }


    @Override
    public void show() {
        asModal().show();
    }

    @Override
    @Deprecated
    public void setPosition(int left, int top) {
        // not implemented
    }

    protected EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Retrieves this view as a {@link Modal}. See {@link #asWidget()}.
     *
     * @return This view as a {@link Modal} object.
     */
    protected Modal asModal() {
        return (Modal) asWidget();
    }

}
