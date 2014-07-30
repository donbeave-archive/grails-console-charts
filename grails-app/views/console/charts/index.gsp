<html>
<head>
    <meta name="layout"
          content="${grailsApplication.config.grails.plugin.console.charts.layout ?: 'console-charts-plugin-layout'}"/>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${assetPath(src: 'console/charts/application.css')}"/>
    <link rel="stylesheet" href="${assetPath(src: 'console/charts/codemirror.css')}"/>
    <script type="text/javascript" src="${assetPath(src: 'console/charts/codemirror-compressed.js')}"></script>

    <script src="${resource(dir: 'gwt/GrailsChartsConsole', file: 'GrailsChartsConsole.nocache.js', plugin: 'console-charts')}?${new Date().time}"></script>

    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load("visualization", "1", {packages: ["corechart"]});
        var console_charts_connect_url = "${createLink(controller: 'consoleCharts', action: 'connect')}";
        var console_charts_data_url = "${createLink(controller: 'consoleCharts', action: 'data')}";
    </script>
</head>

<body>
</body>
</html>
