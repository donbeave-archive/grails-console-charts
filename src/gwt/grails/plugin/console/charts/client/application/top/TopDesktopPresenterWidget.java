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

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import grails.plugin.console.charts.client.application.connection.ConnectionDesktopPresenter;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class TopDesktopPresenterWidget extends AbstractTopPresenter {

    @Inject
    TopDesktopPresenterWidget(final EventBus eventBus,
                              final MyView view,
                              final ConnectionDesktopPresenter connectionPresenter) {
        super(eventBus, view, connectionPresenter);
    }

}