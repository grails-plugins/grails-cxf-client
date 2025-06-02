# TODO

This Grails 7 upgrade is still very much Work In Progress...

- [ ] The Java classes in `src/integration-test/java/net/webservicex/`should be generated dynamically from the WSDL file
  - [ ] Add `stockquote.wsdl` file (found on WayBack machine)
- [ ] Enable the integration test `StockQuoteClientSpec` (probably failing due to missing configuration...)
- [ ] The plugin can't be published at the moment (the previous published to Bintray which has been [shut down](https://jfrog.com/blog/into-the-sunset-bintray-jcenter-gocenter-and-chartcenter/))
  - [ ] build and release with GitHub actions
- [ ] Update the documentation
- [ ] The documentation mentions a grails command and a Gradle task, they don't seem to work
  - [ ] `grails wsdl-to-java`
  - [ ] `./gradlew wsdlToJava`
- [ ] `logback.groovy` should be converted to `logback.xml`
- [ ] `i18n/` should probably be removed (see old [PR 75](https://github.com/Grails-Plugin-Consortium/grails-cxf-client/pull/75), [PR 77](https://github.com/Grails-Plugin-Consortium/grails-cxf-client/pull/77) and [Issue 73](https://github.com/Grails-Plugin-Consortium/grails-cxf-client/issues/74))
- [ ] Check dependencies -> for newer versions (`./gradlew dependencies`)
- [ ] Check logs/error messages (logback etc)
- [ ] etc. etc. ...

## Improvements / ideas

- [ ] [CodeNarc](https://codenarc.org/) for code quality
- [ ] [Jacoco](https://www.eclemma.org/jacoco/) for measuring test coverage
