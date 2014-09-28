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
package grails.plugin.console.charts.client.application;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import grails.plugin.console.charts.client.application.connection.AbstractConnectionPresenter;
import grails.plugin.console.charts.client.application.connection.ConnectionDesktopPresenter;
import grails.plugin.console.charts.client.application.connection.ConnectionDesktopView;
import grails.plugin.console.charts.client.application.editor.AbstractEditorPresenter;
import grails.plugin.console.charts.client.application.editor.EditorDesktopPresenterWidget;
import grails.plugin.console.charts.client.application.editor.EditorDesktopView;
import grails.plugin.console.charts.client.application.share.AbstractSharePresenter;
import grails.plugin.console.charts.client.application.share.ShareDesktopPresenter;
import grails.plugin.console.charts.client.application.share.ShareDesktopView;
import grails.plugin.console.charts.client.application.top.AbstractTopPresenter;
import grails.plugin.console.charts.client.application.top.TopDesktopPresenterWidget;
import grails.plugin.console.charts.client.application.top.TopDesktopView;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class ApplicationDesktopModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        // Application Presenter
        bind(ApplicationDesktopPresenter.class).in(Singleton.class);
        bind(ApplicationDesktopView.class).in(Singleton.class);
        bind(AbstractApplicationPresenter.MyProxy.class).asEagerSingleton();
        bind(AbstractApplicationPresenter.MyView.class).to(ApplicationDesktopView.class);
        bind(AbstractApplicationPresenter.class).to(ApplicationDesktopPresenter.class);

        // Top Presenter
        bind(TopDesktopPresenterWidget.class).in(Singleton.class);
        bind(TopDesktopView.class).in(Singleton.class);
        bind(AbstractTopPresenter.MyView.class).to(TopDesktopView.class);
        bind(AbstractTopPresenter.class).to(TopDesktopPresenterWidget.class);

        // Editor Presenter
        bind(EditorDesktopPresenterWidget.class).in(Singleton.class);
        bind(EditorDesktopView.class).in(Singleton.class);
        bind(AbstractEditorPresenter.MyView.class).to(EditorDesktopView.class);
        bind(AbstractEditorPresenter.class).to(EditorDesktopPresenterWidget.class);

        // Connection Presenter
        bind(ConnectionDesktopPresenter.class).in(Singleton.class);
        bind(ConnectionDesktopView.class).in(Singleton.class);
        bind(AbstractConnectionPresenter.MyView.class).to(ConnectionDesktopView.class);
        bind(AbstractConnectionPresenter.class).to(ConnectionDesktopPresenter.class);

        // Share Presenter
        bind(ShareDesktopPresenter.class).in(Singleton.class);
        bind(ShareDesktopView.class).in(Singleton.class);
        bind(AbstractSharePresenter.MyView.class).to(ShareDesktopView.class);
        bind(AbstractSharePresenter.class).to(ShareDesktopPresenter.class);
    }

}
