[![Maven Central](https://img.shields.io/maven-central/v/org.grails.plugins/cxf-client)](https://central.sonatype.com/artifact/org.grails.plugins/cxf-client)
[![License](https://img.shields.io/github/license/grails-plugins/grails-cxf-client)](https://www.apache.org/licenses/LICENSE-2.0)
[![CI](https://github.com/grails-plugins/grails-cxf-client/actions/workflows/ci.yml/badge.svg?event=push)](https://github.com/grails-plugins/grails-cxf-client/actions/workflows/ci.yml)

Grails CXF Client
==================

Consume SOAP web services from Grails using Apache CXF wsdl2java-generated clients, with cached port
references, configuration-driven wiring, and dynamically updatable service endpoints.

The [CXF](https://cxf.apache.org/) Client plugin lets you use existing (or new) wsdl2java generated content and
caches the port reference to speed up your SOAP service endpoint invocations through an easy configuration
driven mechanism.

The user guide can be found here: 📚 [Documentation](https://grails-plugins.github.io/grails-cxf-client/)

## Installation

Add the following dependency to the `build.gradle` file:

### Grails 7.x

```groovy
dependencies {
    implementation 'org.grails.plugins:cxf-client:5.0.0-RC1'
}
```

### Grails 3, 4, 5, 6

```groovy
compile 'org.grails.plugins:cxf-client:3.0.7'
```

See the [documentation](https://grails-plugins.github.io/grails-cxf-client/) for configuration, the wsdl2java
command, custom interceptors, security, and more.
