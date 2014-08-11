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

        // GWT dependencies
        provided 'com.gwtplatform:gwtp-mvp-client:1.2.1', {
            excludes 'maven-artifact'
            export = false
        }
        provided 'commons-lang:commons-lang:2.6', { // need by GWTP
            export = false
        }
        provided 'org.apache.maven:maven-artifact:2.2.1', { // need by GWTP
            export = false
        }
        provided 'com.dianaui:dianaui-universal-core:0.1-SNAPSHOT', {
            export = false
        }
        provided 'com.dianaui:dianaui-universal-gwtp:0.1-SNAPSHOT', {
            export = false
        }
        provided 'edu.stanford.protege:codemirror-gwt:1.0.0', {
            excludes 'gwt-servlet'
            export = false
        }
        provided 'com.google.guava:guava-gwt:17.0', {
            export = false
        }

        test 'org.grails:grails-datastore-test-support:1.0-grails-2.4'
    }

    plugins {
        build(':tomcat:7.0.54',
                ':release:3.0.1',
                ':rest-client-builder:1.0.3') {
            export = false
        }
        runtime ':console:1.4.4'
        compile(':asset-pipeline:1.9.4',
                ':extended-dependency-manager:0.5.6',
                ':hibernate4:4.3.5.4',
                ':resources:1.2.8') {
            export = false
        }
        compile ':gwt:1.0.1', {
            transitive = false
            export = false
        }
    }
}

gwt {
    version = '2.6.1'
    gin.version = '2.1.2'
    use.provided.deps = true
    if (Environment.isDevelopmentMode()) {
        compile.args = {
            arg(value: '-draftCompile')
            arg(value: '-localWorkers')
            arg(value: '8')
        }
    }
    run.args = {
        jvmarg(value: '-Xms1024m')
        jvmarg(value: '-Xmx4096m')
    }

    javac.cmd = 'javac' // Need for JDK 1.8
}
