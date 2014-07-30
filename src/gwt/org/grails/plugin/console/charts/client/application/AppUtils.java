package org.grails.plugin.console.charts.client.application;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AppUtils {

    public static String CONNECTION_STRING = null;

    public static native String getConnectPath() /*-{
        return $wnd.console_charts_connect_url;
    }-*/;

    public static native String getDataPath() /*-{
        return $wnd.console_charts_data_url;
    }-*/;

}
