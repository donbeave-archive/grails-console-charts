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

import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.Session
import grails.converters.JSON
import grails.util.Holders
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException

import java.sql.*

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
class ConsoleChartsController {

    static defaultAction = 'data'

    static Session doSshTunnel(String host = 'localhost', int port = 22, String user, String password,
                               String remoteHost, int localPort, int nRemotePort) throws JSchException {
        JSch jsch = new JSch()
        Session session = jsch.getSession(user, host, port)
        session.password = password

        Properties config = new Properties()
        config.put('StrictHostKeyChecking', 'no')
        session.config = config

        session.connect()
        session.setPortForwardingL(localPort, remoteHost, nRemotePort)

        session
    }

    static Connection connectToMySql(String host = 'localhost', int port = 3306, String user = 'root',
                                     String password = null) throws SQLException {
        Properties info = new Properties()

        if (user != null)
            info.put('user', user)

        if (password != null)
            info.put('password', password)

        Class.forName('com.mysql.jdbc.Driver')

        def con = DriverManager.getConnection("jdbc:mysql://${host}:${port}", info, Holders.grailsApplication.class)
        con.autoCommit = false
        con
    }

    def chartsEncryprionService
    def consoleService

    def data(String query, String connectionString, String appearance) {
        if (!query) {
            render([error: true, text: 'Query is empty'] as JSON)
            return;
        }

        query = new String(query.decodeBase64())

        List<String> queries = query.split(';')

        connectionString = chartsEncryprionService.decrypt(connectionString)

        if (!connectionString.contains('{')) {
            render(text: [error: 'connection_string_wrong'] as JSON)
            return;
        }

        def json = JSON.parse(connectionString)

        Session sshSession = null

        String mysqlHostname = json.mysqlHostname ?: 'localhost'
        Integer mysqlPort = json.mysqlPort ?: 3306

        if (json.sshToggle) {
            mysqlPort = 3369
            mysqlHostname = 'localhost'

            sshSession = doSshTunnel(json.sshHostname, json.sshPort ?: 22, json.sshUsername,
                    json.sshPassword, json.mysqlHostname, mysqlPort, json.mysqlPort ?: 3306)
        }

        try {
            Connection connection =
                    connectToMySql(mysqlHostname, mysqlPort, json.mysqlUsername, json.mysqlPassword)

            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)

            if (queries.size() > 1) {
                queries.eachWithIndex { entry, i ->
                    if (i != (queries.size() - 1))
                        stmt.addBatch(entry)
                }

                stmt.executeBatch()
                stmt.close()

                // re-init statement
                stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
            }

            //        if (!query.toLowerCase().contains('limit'))
//            query += ' LIMIT 100'

            ResultSet rs = stmt.executeQuery(queries.size() > 1 ? queries.last() : query)

            def columns = null
            def content = null
            def override = null

            if (appearance) {
                try {
                    def bindingValues = [session: request.session, request: request, rs: rs, md: rs.metaData, base: this]

                    def result = consoleService.eval(new String(appearance.decodeBase64()), bindingValues)

                    if (result instanceof Map) {
                        content = result.content
                        columns = result.columns
                        override = result.override
                    } else {
                        content = result
                    }
                }
                catch (Throwable t) {
                    render(text: [error: t.localizedMessage] as JSON)
                    return;
                }
            } else {
                content = parse(rs)
            }

            rs.last()

            render(text: [content: content, columns: columns ?: getColumns(rs), override: override, count: rs.row] as JSON)

            stmt.close()
            connection.close()
        }
        finally {
            sshSession?.disconnect()
        }
    }

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

                    sshSession = doSshTunnel(json.sshHostname, json.sshPort ?: 22, json.sshUsername,
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
                    Connection connection =
                            connectToMySql(mysqlHostname, mysqlPort, json.mysqlUsername, json.mysqlPassword)

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

    static List parse(ResultSet rs) {
        def content = []

        while (rs.next()) {
            def item = [:]
            for (int i = 1; i <= rs.metaData.getColumnCount(); i++) {
                def value = i == 1 ? rs.getString(i) : null

                if (i > 1) {
                    try {
                        value = rs.getInt(i);
                    } catch (e) {
                        // ignore
                    }
                }

                item[rs.metaData.getColumnName(i)] = value
            }

            content.add item
        }

        content
    }

    static List toList(ResultSet rs) {
        def content = []

        while (rs.next()) {
            def item = []
            for (int i = 1; i <= rs.metaData.getColumnCount(); i++) {
                def value = i == 1 ? rs.getString(i) : null

                if (i > 1) {
                    try {
                        value = rs.getInt(i);
                    } catch (e) {
                        // ignore
                    }
                }

                item.add value
            }

            content.add item
        }

        content
    }

    static List getColumns(ResultSet rs) {
        def result = []

        ResultSetMetaData metaData = rs.metaData

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            result.add metaData.getColumnName(i)
        }

        result
    }

}
