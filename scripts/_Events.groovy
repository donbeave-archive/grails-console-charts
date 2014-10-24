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
eventCreatePluginArchiveStart = { stagingDir ->
    ant.delete(dir: "${stagingDir}/src/gwt")
    ant.delete(dir: "${stagingDir}/web-app/js/gwt/GrailsChartsConsole/codemirror")
    ant.delete(dir: "${stagingDir}/web-app/js/gwt/GrailsChartsConsole/.junit_symbolMaps")
    ant.delete(dir: "${stagingDir}/web-app/js/gwt/WEB-INF/deploy/GrailsChartsConsole")
    ant.delete(file: "${stagingDir}/web-app/js/gwt/GrailsChartsConsole/GrailsChartsConsole.devmode.js")
    ant.delete(file: "${stagingDir}/web-app/js/gwt/GrailsChartsConsole/compilation-mappings.txt")
    ant.delete(file: "${stagingDir}/web-app/js/gwt/GrailsChartsConsole/junit.html")
    ant.delete(file: "${stagingDir}/web-app/js/gwt/GrailsChartsConsole/junit-standards.html")
    ant.delete(file: "${stagingDir}/web-app/js/gwt/GrailsChartsConsole/manchestersyntax.css")
}

eventAssetPrecompileStart = { assetConfig ->
    if (!config.grails.assets.plugin.'console-charts'.excludes ||
            config.grails.assets.plugin.'console-charts'.excludes.size() == 0) {
        config.grails.assets.plugin.'console-charts'.excludes = ['gwt/GrailsChartsConsole/codemirror/**',
                                                                 'gwt/GrailsChartsConsole/.junit_symbolMaps/**',
                                                                 'gwt/WEB-INF/deploy/GrailsChartsConsole/**']
    }
}
