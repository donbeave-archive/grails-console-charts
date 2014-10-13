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

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import grails.plugin.console.charts.shared.ConnectStatus;
import grails.plugin.console.charts.shared.ConnectionDetails;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AppUtils {

    public interface BeanFactory extends AutoBeanFactory {

        AutoBean<ConnectionDetails> details();

        AutoBean<ConnectStatus> status();

    }

    public static final BeanFactory BEAN_FACTORY = GWT.create(BeanFactory.class);

    public static Integer DEFAULT_WIDTH = 900;
    public static Integer CURRENT_HEIGHT = 500;

    public static String CONNECTION_STRING = null;
    public static String QUERY;
    public static String APPEARANCE;
    public static String VIEW;

    public static ConnectStatus CONNECT_STATUS = null;

    public static native String getConnectPath() /*-{
        return $wnd.console_charts_connect_url;
    }-*/;

    public static native String getDataPath() /*-{
        return $wnd.console_charts_data_url;
    }-*/;

    public static native String getLinkPath() /*-{
        return $wnd.console_charts_link_url;
    }-*/;

    public static native String decodeBase64(final String base64) /*-{
        return unescape(decodeURIComponent(window.atob(base64)));
    }-*/;

    public static native String encodeBase64(final String input) /*-{
        return window.btoa(encodeURIComponent(escape(input)));
    }-*/;

}
