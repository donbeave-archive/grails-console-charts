#!/bin/bash
rm -rf target/
grails clean-all
grails refresh-dependencies
grails prod compile-gwt-modules
