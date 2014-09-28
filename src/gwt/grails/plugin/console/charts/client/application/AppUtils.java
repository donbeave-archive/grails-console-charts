package grails.plugin.console.charts.client.application;

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
public class AppUtils {

    public static Integer DEFAULT_WIDTH = 900;
    public static Integer DEFAULT_HEIGHT = 500;

    public static String CONNECTION_STRING = null;
    public static String QUERY;
    public static String APPEARANCE;
    public static String VIEW;

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
