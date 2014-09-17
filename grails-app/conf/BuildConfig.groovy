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
import grails.util.Environment

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'

/*
forkConfig = [maxMemory: 1024, minMemory: 64, debug: false, maxPerm: 256]
grails.project.fork = [
        test   : forkConfig,
        run    : forkConfig,
        war    : forkConfig,
        console: forkConfig
]
*/

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {
    inherits('global') {
    }
    log 'warn'
    repositories {
        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        mavenRepo 'https://raw.github.com/donbeave/mavenrepo/master/'
    }
    dependencies {
        runtime 'com.jcraft:jsch:0.1.51'
        runtime 'mysql:mysql-connector-java:5.1.31', {
            export = false
        }

        test 'org.grails:grails-datastore-test-support:1.0-grails-2.4'
    }

    plugins {
        build(':tomcat:7.0.55',
                ':release:3.0.1',
                ':rest-client-builder:2.0.3') {
            export = false
        }
        runtime ':console:1.4.4'
        compile(':asset-pipeline:1.9.9',
                ':hibernate4:4.3.5.5',
                ':resources:1.2.8') {
            export = false
        }
        compile ':gwt:2.0-SNAPSHOT', {
            transitive = false
            export = false
        }
    }
}

gwt {
    version = '2.6.1'
    gin.version = '2.1.2'
    gwtp.version = '1.3'
    guava.version = '17.0'
    dependencies = [
            'com.dianaui:dianaui-universal-core:0.1-SNAPSHOT',
            'com.dianaui:dianaui-universal-gwtp:0.1-SNAPSHOT',
            'edu.stanford.protege:codemirror-gwt:1.0.1-SNAPSHOT'
    ]
    if (Environment.isDevelopmentMode()) {
        draft.compile = true
        local.workers = 8
    }
    run.args = {
        jvmarg(value: '-Xms1024m')
        jvmarg(value: '-Xmx4096m')
    }
}
