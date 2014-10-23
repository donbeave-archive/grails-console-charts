<html>
<head>
    <meta name="layout"
          content="${grailsApplication.config.grails.plugin.console.charts.layout ?: 'console-charts-plugin-layout'}"/>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
    <asset:link rel="stylesheet" href="console/charts/application.css"/>
    <asset:javascript src="console/charts/codemirror-compressed.js"/>

    <asset:javascript src="gwt/GrailsChartsConsole/GrailsChartsConsole.nocache.js"/>

    <script type="text/javascript" src="http://www.amcharts.com/lib/3/amcharts.js"></script>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/serial.js"></script>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/themes/none.js"></script>

    <script type="text/javascript">
        var console_charts_connect_url = "${createLink(controller: 'consoleCharts', action: 'connect')}";
        var console_charts_data_url = "${createLink(controller: 'consoleCharts', action: 'data')}";
        var console_charts_link_url = "${createLink(controller: 'consoleCharts', action: 'link')}";
    </script>
</head>

<body>
</body>
</html>
