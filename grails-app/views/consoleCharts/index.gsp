<html>
<head>
    <meta name="layout"
          content="${grailsApplication.config.grails.plugin.console.charts.layout ?: 'console-charts-plugin-layout'}"/>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${assetPath(src: 'console/charts/application.css')}"/>
    <link rel="stylesheet" href="${assetPath(src: 'console/charts/codemirror.css')}"/>
    <link rel="stylesheet" href="${assetPath(src: 'console/charts/lesser-dark.css')}"/>
    <script type="text/javascript" src="${assetPath(src: 'console/charts/codemirror-compressed.js')}"></script>

    <script src="${resource(dir: 'gwt/GrailsChartsConsole', file: 'GrailsChartsConsole.nocache.js', plugin: 'console-charts')}?${new Date().time}"></script>

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
