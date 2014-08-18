<%@ page import="grails.converters.JSON" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${title}</title>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/amcharts.js"></script>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/serial.js"></script>
    <script type="text/javascript" src="http://www.amcharts.com/lib/3/themes/none.js"></script>
    <style type="text/css">
    html, body {
        margin: 0;
        padding: 0;
    }
    </style>
</head>

<body>
<div id="result">
    <div id="chart" style="width: ${width}px; height: ${height}px;"></div>
    <%
        def graphs = []
        columns.eachWithIndex { col, i ->
            if (i > 0) {
                graphs.add([
                        'id'                   : "g${i}",
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

        def chartData = [
                'type'          : 'serial',
                'theme'         : 'none',
                'pathToImages'  : 'http://www.amcharts.com/lib/3/images/',
                'legend'        : [
                        'align'          : 'center',
                        'equalWidths'    : false,
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
                'chartCursor'   : [],
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
</body>
</html>