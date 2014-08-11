#!/bin/bash
rm -rf target/
grails clean-all
grails refresh-dependencies
grails compile-gwt-modules
