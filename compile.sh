#!/bin/bash
rm -rf target/ && rm -rf web-app/gwt && grails clean-all && grails refresh-dependencies && grails prod compile-gwt-modules
