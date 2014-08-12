#!/bin/bash
rm -rf target/
grails clean-all
grails -Dgwt=true refresh-dependencies
grails -Dgwt=true compile-gwt-modules
