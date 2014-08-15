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
package org.grails.plugin.console.charts

import com.jcraft.jsch.Session
import grails.converters.JSON
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException

import java.sql.Connection
import java.sql.SQLException

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
class ConsoleChartsController {

    static defaultAction = 'data'

    def chartsEncryprionService
    def consoleChartsService

    def connect(String connectData) {
        if (!connectData) {
            render(text: [connected: false, error: 'empty_data'] as JSON)
            return
        }
        if (connectData.contains('{')) {
            try {
                def json = JSON.parse(connectData)

                String mysqlHostname = json.mysqlHostname ?: 'localhost'
                Integer mysqlPort = json.mysqlPort ?: 3306

                Session sshSession = null

                if (json.sshToggle) {
                    mysqlPort = 3368
                    mysqlHostname = 'localhost'

                    if (!json.sshHostname) {
                        render(text: [connected: false, error: 'ssh_hostname_empty'] as JSON)
                        return
                    }

                    if (!json.sshUsername) {
                        render(text: [connected: false, error: 'ssh_username_empty'] as JSON)
                        return
                    }

                    sshSession = consoleChartsService.doSshTunnel(json.sshHostname, json.sshPort ?: 22, json.sshUsername,
                            json.sshPassword, json.mysqlHostname, mysqlPort, json.mysqlPort ?: 3306)
                }

                if (!json.mysqlHostname) {
                    sshSession?.disconnect()

                    render(text: [connected: false, error: 'mysql_hostname_empty'] as JSON)
                    return
                }

                if (!json.mysqlUsername) {
                    sshSession?.disconnect()

                    render(text: [connected: false, error: 'mysql_username_empty'] as JSON)
                    return
                }

                try {
                    Connection connection = consoleChartsService
                            .connectToMySql(mysqlHostname, mysqlPort, json.mysqlUsername, json.mysqlPassword)

                    connection.close()
                    sshSession?.disconnect()

                    String encodedString = chartsEncryprionService.encrypt(json.toString())

                    String status = "${json.sshToggle ? "${json.mysqlHostname}:${json.mysqlPort}" : "${mysqlHostname}:${mysqlPort}"}${json.sshToggle ? " through ${json.sshHostname}:${json.sshPort}" : ''}"

                    render(text: [connected       : true,
                                  connectionString: encodedString,
                                  status          : "Connected to ${status}"] as JSON)
                } catch (IOException | SQLException e) {
                    sshSession?.disconnect()

                    render(text: [connected: false, error: e.message, exception: e.class] as JSON)
                }
            } catch (ConverterException e) {
                render(text: [connected: false, error: 'wrong_json'] as JSON)
            }
        } else {
            // maybe
        }
    }

    def data(String query, String connectionString, String appearance) {
        try {
            if (!query) {
                render([error: true, text: 'Query is empty'] as JSON)
                return
            }

            // decode parameters
            query = new String(query.decodeBase64())
            connectionString = chartsEncryprionService.decrypt(connectionString)
            appearance = new String(appearance.decodeBase64())

            render(text: consoleChartsService.getData(query, connectionString, appearance, request) as JSON)
        } catch (e) {
            render(text: [error: e.localizedMessage] as JSON)
        }
    }

    def link() {
        def json = request.JSON as JSON
        String data = json.toString()
        String encodedData = chartsEncryprionService.encrypt(data)
        String link =
                createLink(controller: 'consoleCharts', action: 'view', params: [q: encodedData], absolute: true)

        render(text: [link: link] as JSON)
    }

    def view(String q) {
        if (!q) {
            render([error: true, text: 'Q is empty'] as JSON)
            return
        }

        String decoded = chartsEncryprionService.decrypt(q)

        def json = JSON.parse(decoded)

        String query = json.query
        String connectionString = chartsEncryprionService.decrypt(json.connectionString)
        String appearance = json.appearance

        def data = consoleChartsService.getData(query, connectionString, appearance, request)

        data.title = json.title
        data.width = json.width
        data.height = json.height
        data.query = query
        data.connectionString = connectionString
        data.appearance = appearance

        data
    }

}
