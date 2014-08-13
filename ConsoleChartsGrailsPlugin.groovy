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

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
class ConsoleChartsGrailsPlugin {

    def version = '0.1-SNAPSHOT'
    def grailsVersion = '2.0 > *'
    def loadAfter = ['hibernate', 'hibernate4']

    def title = 'Console Charts Plugin'
    def author = 'Alexey Zhokhov'
    def authorEmail = 'donbeave@gmail.com'
    def description = '''\
A web-based console plugin for drawing [amChartsa|http://www.amcharts.com/] direct by data from SQL queries
'''

    def documentation = 'http://grails.org/plugin/console-charts'

    def license = 'APACHE'

    def developers = [
            [name: 'Alexey Zhokhov', email: 'donbeave@gmail.com']
    ]
    def organization = [name: 'Polusharie', url: 'http://www.polusharie.com']

    def issueManagement = [system: 'GITHUB',
                           url   : 'https://github.com/donbeave/grails-console-charts/issues']
    def scm = [url: 'https://github.com/donbeave/grails-console-charts']

}
