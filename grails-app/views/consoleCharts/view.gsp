<%@ page import="grails.converters.JSON" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/amcharts.js"></script>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/serial.js"></script>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/themes/none.js"></script>
    <style type="text/css">
    html, body {
        margin: 0;
        padding: 0;
    }

    .error {
        font-weight: 100;
        margin-top: 0;
    }

    #errorMessage {
        background: rgb(247, 247, 247);
        border-radius: 2px;
        position: absolute;
        top: 50%;
        max-width: 90%;
        width: 90%;
        margin: 0 auto;
        left: 5%;
        padding: 20px;
        top: 0;
        bottom: 0;
    }

    #errorMessage h1 {
        margin-top: 0;
        font-weight: 100;
    }

    #errorMessage h4 {
        text-align: right;
    }

    #errorMessage pre {
        border-radius: 0;
        background: rgb(63, 63, 63);
        color: white;
        border-color: transparent;
    }

    #editLink {
        z-index: 99999;
        position: fixed;
        bottom: 1em;
        right: 1em;
    }
    </style>
</head>

<body class="${error ? 'error' : ''}">
<g:if test="${error}">
    <div id="errorMessage">
        <h1>${text}</h1>
        <g:if test="${q}">
            <div class="row">
                <div class="col-md-1">
                    <h4>Q</h4>
                </div>

                <div class="col-md-11">
                    <pre>${q}</pre>
                </div>
            </div>
        </g:if>
        <g:if test="${decoded}">
            <div class="row">
                <div class="col-md-1">
                    <h4>Decoded</h4>
                </div>

                <div class="col-md-11">
                    <pre>${decoded}</pre>
                </div>
            </div>
        </g:if>
        <g:if test="${exception}">
            <div class="row">
                <div class="col-md-1">
                    <h4>Exception</h4>
                </div>

                <div class="col-md-11">
                    <pre>${exception.message}</pre>
                </div>
            </div>
        </g:if>
    </div>
</g:if>
<g:else>
    <div id="result">
        <div id="chart" style="width: ${width}px; height: ${height}px;"></div>
        <%
            def graphs = []
            columns.eachWithIndex { col, i ->
                if (i > 0) {
                    graphs.add([
                            'id'                   : "g${i}",
                            "balloonText"          : "${columns.size > 2 ? '[[title]]: [[value]]' : '[[value]]'}",
                            'valueField'           : "${col}",
                            'valueAxis'            : "v${i}",
                            'bullet'               : 'round',
                            'bulletBorderThickness': 1,
                            'hideBulletsCount'     : 30,
                            'title'                : "${override && override[col.toString()] ? override[col.toString()] : col}",
                            'fillAlphas'           : 0
                    ])
                }
            }

            def scrollbar = columns ? [
                    'autoGridCount'  : true,
                    'graph'          : 'g1',
                    'scrollbarHeight': 40
            ] : []

            def cursor = columns && columns.size() > 5 ? [
                    'cursorAlpha'         : 0.1,
                    'cursorColor'         : '#000000',
                    'fullWidth'           : true,
                    'valueBalloonsEnabled': false,
                    'zoomable'            : true
            ] : []

            def chartData = [
                    'type'          : 'serial',
                    'theme'         : 'none',
                    'pathToImages'  : 'http://www.amcharts.com/lib/3/images/',
                    'legend'        : [
                            'align'          : 'center',
                            'equalWidths'    : true,
                            'periodValueText': 'total: [[value.sum]]',
                            'valueAlign'     : 'left',
                            'valueText'      : '[[value]] ([[percents]]%)',
                            'valueWidth'     : 100
                    ],
                    'dataDateFormat': 'YYYY-MM-DD HH:NN',
                    'dataProvider'  : content,
                    'valueAxes'     : [
                            [
                                    'id'           : 'v1',
                                    'axisThickness': 2,
                                    'gridAlpha'    : 0,
                                    'axisAlpha'    : 1,
                                    'position'     : 'left'
                            ]
                    ],
                    'graphs'        : graphs,
                    'chartScrollbar': scrollbar,
                    'chartCursor'   : cursor,
                    'categoryField' : columns.get(0),
                    'categoryAxis'  : [
                            'parseDates'      : true,
                            'axisColor'       : '#DADADA',
                            'minorGridEnabled': true
                    ],
                    'exportConfig'  : [
                            'menuTop'  : '10px',
                            'menuRight': '11px',
                            'menuItems': [
                                    [
                                            'icon'  : 'http://www.amcharts.com/lib/3/images/export.png',
                                            'format': 'png'
                                    ]
                            ]
                    ]
            ]

            if (title) {
                chartData.titles = [
                        [
                                'text': title,
                                'size': 13
                        ]
                ]
            }
        %>
        <script type="text/javascript" charset="utf-8" id="chartInit">
            AmCharts.makeChart("chart", ${raw((chartData as JSON).toString(true))});
        </script>
    </div>
</g:else>
<g:if test="${editLink}">
    <div id="editLink">
        <a href="${editLink}" target="_blank" class="btn btn-default btn-xs">
            <span class="glyphicon glyphicon-pencil"></span> Edit
        </a>
    </div>
</g:if>
</body>
</html>