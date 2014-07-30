package com.dianaui.universal.gwtp.client;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public abstract class ModalViewWithUiHandlers<C extends UiHandlers> extends ModalViewImpl implements HasUiHandlers<C> {

    private C uiHandlers;

    protected ModalViewWithUiHandlers(EventBus eventBus) {
        super(eventBus);
    }

    /**
     * Access the {@link UiHandlers} associated with this {@link com.gwtplatform.mvp.client.View}.
     * <p/>
     * <b>Important!</b> Never call {@link #getUiHandlers()} inside your constructor
     * since the {@link UiHandlers} are not yet set.
     *
     * @return The {@link UiHandlers}, or {@code null} if they are not yet set.
     */
    protected C getUiHandlers() {
        return uiHandlers;
    }

    @Override
    public void setUiHandlers(C uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

}
