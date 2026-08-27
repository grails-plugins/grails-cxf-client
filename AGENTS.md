# AGENTS.md - grails-cxf-client

## Project Overview

This is the **Grails CXF Client** plugin. It lets a Grails application consume SOAP web services using
Apache CXF wsdl2java-generated clients, with cached port references, configuration-driven wiring, and
dynamically updatable service endpoints.

- **Language:** Groovy on Java 17
- **Framework:** Grails 7.x
- **Build System:** Gradle (with wrapper) — see `.sdkmanrc`
- **Current Version:** see `gradle.properties` (`projectVersion`)
- **Published as:** `org.grails.plugins:cxf-client`
- **License:** Apache 2.0

This repository is built on [grails-plugin-template](https://github.com/grails-plugins/grails-plugin-template).

## Skill Files (Best Practices)

Detailed best practices are documented as skills in `.agents/skills/` (`.claude` is a symlink to `.agents`):

| Skill                                                                                | Purpose                                                 |
|---------------------------------------------------------------------------------------|----------------------------------------------------------|
| [`repository-structure`](.agents/skills/repository-structure/SKILL.md)                | Canonical directory layout and architectural rules      |
| [`gradle-best-practices`](.agents/skills/gradle-best-practices/SKILL.md)              | Gradle best practices, convention plugins, and idioms   |
| [`plugin-project`](.agents/skills/plugin-project/SKILL.md)                            | Plugin project scope: source code + unit tests only     |
| [`example-apps`](.agents/skills/example-apps/SKILL.md)                                | Example app patterns: integration & functional tests    |
| [`enhance-plugin-with-template`](.agents/skills/enhance-plugin-with-template/SKILL.md) | Migrate an existing plugin onto this template structure |

**Read these skill files before making structural changes to the repository.**

## Critical Rules

1. **NEVER add code to the root `build.gradle` to configure subprojects.** No `subprojects {}`, `allprojects {}`,
   or `configure()` blocks. All shared configuration goes through convention plugins in `build-logic/`.
2. **The plugin project contains ONLY plugin code and unit tests.** No integration tests, no functional tests,
   no example controllers or views.
3. **The example app under `examples/cxf-client-demo/` hosts all integration and functional tests.** It depends
   on the plugin via `implementation project(':cxf-client')` and exercises it as a real consumer would.
4. **Use Gradle convention plugins to deduplicate.** If two or more subprojects share build logic, extract it
   into a convention plugin in `build-logic/`.
5. **Always use lazy Gradle APIs** to avoid eager initialization (`tasks.register()`, `tasks.named()`,
   `configureEach`, `provider {}`).

## Repository Structure

```
grails-cxf-client/
├── .agents/skills/      # Agent skill files (.claude is a symlink to .agents)
├── plugin/              # Core Grails plugin (artifact: cxf-client)
│   ├── grails-app/      #   Plugin commands, i18n messages, and conf
│   └── src/main/        #   Plugin source code (org.grails.cxf.client)
├── examples/cxf-client-demo/  # Example Grails app, hosts the integration test
│   ├── grails-app/      #   cxf.client config wiring the demo StockQuote client
│   ├── wsdl/            #   Sample WSDL files used for wsdl2java demonstrations
│   └── src/integration-test/  # wsdl2java-generated demo stubs + StockQuoteClientSpec
├── docs/                # Asciidoctor documentation
├── build-logic/         # Gradle convention plugins (composite build)
├── .github/workflows/   # CI, release, and release-notes workflows
├── build.gradle          # Root build file (docs + root-publish ONLY)
├── settings.gradle       # Multi-project settings
└── gradle.properties     # Version properties
```

## Build and Test Commands

```bash
# Full build (compile + test)
./gradlew build

# Run only unit tests (plugin module)
./gradlew :cxf-client:test

# Run integration tests (example app)
./gradlew :cxf-client-demo:integrationTest

# Skip tests
./gradlew build -PskipTests

# Run the example app
./gradlew :cxf-client-demo:bootRun

# Generate documentation
./gradlew docs

# Clean build
./gradlew clean build

# Run code style checks only
./gradlew codeStyle

# Skip code style checks
./gradlew build -PskipCodeStyle
```

## SDK Requirements

Use SDKMAN to install the correct tool versions (see `.sdkmanrc`). Run `sdk env install` to set up the
environment.

## Architecture

The plugin registers itself via `CxfClientGrailsPlugin`, which wires one Spring bean of type
`DynamicWebServiceClient` per entry under the `cxf.client.*` config namespace, backed by a shared
`WebServiceClientFactory` that caches CXF port references and allows updating a service's endpoint address at
runtime.

### Core Classes

| Class / Interface                | Location                                                        | Purpose                                                    |
|-----------------------------------|-------------------------------------------------------------------|--------------------------------------------------------------|
| `CxfClientGrailsPlugin`           | `plugin/src/main/groovy/org/grails/cxf/client/`                   | Plugin descriptor; wires one client bean per config entry   |
| `WebServiceClientFactory`         | `plugin/src/main/groovy/org/grails/cxf/client/`                   | Interface for creating/updating cached CXF client proxies   |
| `WebServiceClientFactoryImpl`     | `plugin/src/main/groovy/org/grails/cxf/client/`                   | CXF `JaxWsProxyFactoryBean`-based implementation            |
| `DynamicWebServiceClient`         | `plugin/src/main/groovy/org/grails/cxf/client/`                   | Spring `FactoryBean` exposing the client interface as a bean|
| `WsdlToJavaCommand`               | `plugin/grails-app/commands/org/grails/cxf/client/`               | `grails wsdlToJava` / `./gradlew wsdlToJava` application command |
| `CxfClientInterceptor`            | `plugin/src/main/groovy/org/grails/cxf/client/`                   | Convenience interface for custom CXF interceptors            |

## Configuration

Consumers configure clients under the `cxf.client.<beanName>` config namespace. See
`docs/src/docs/usage/configuration.adoc` for the full reference.

## Testing

### Unit Tests (`plugin/src/test/`)

Unit tests use the **Spock Framework** and run on JUnit Platform. They test the plugin's classes directly,
without a running Grails application context.

### Integration Tests (`examples/cxf-client-demo/src/integration-test/`)

`StockQuoteClientSpec` exercises the plugin as a real consumer would, via `cxf.client` config in
`examples/cxf-client-demo/grails-app/conf/application.yml`. Note that the demo service
(`webservicex.net`'s stock quote service) has been offline for years, so the tests that call it live are
`@Ignore`'d — only the config-wiring assertions run.

## Build-Logic Convention Plugins

Convention plugins in `build-logic/src/main/groovy/` standardize build configuration:

| Plugin                 | Purpose                                                                              |
|------------------------|--------------------------------------------------------------------------------------|
| `app-run.gradle`       | Debug flags for `bootRun`                                                            |
| `compile.gradle`       | Java/Groovy compilation settings (UTF-8, incremental, Java release from `.sdkmanrc`) |
| `docs.gradle`          | Documentation aggregation (Groovydoc + Asciidoctor)                                  |
| `example-app.gradle`   | Example app config (grails-web, GSP, assets)                                         |
| `grails-plugin.gradle` | Grails plugin application                                                            |
| `publish.gradle`       | Per-project Maven publishing metadata                                                |
| `publish-root.gradle`  | Root-level Nexus publishing workaround                                               |
| `testing.gradle`       | Test framework config (Spock, JUnit Platform, test-logger)                           |

## CI/CD

- **CI** (`.github/workflows/ci.yml`): Builds and tests on push/PR; publishes snapshots to Maven Central
  Snapshots on push to release branches.
- **Release** (`.github/workflows/release.yml`): 4-stage pipeline triggered by GitHub release — stage
  artifacts, release to Maven Central, publish docs to GitHub Pages, bump version.
- **Release Notes** (`.github/workflows/release-notes.yml`): Auto-drafts release notes using release-drafter
  with category labels.

## Code Conventions

- Groovy source files use standard Grails conventions (application commands and conf in `grails-app/`, other
  classes in `src/main/groovy/`).
- **Use `def` for local variables** where the type is inferred from the right-hand side. Explicit types should
  only be used for local variables when the type cannot be inferred or when needed for `@CompileStatic`
  compilation. This applies to both production code and tests.
- When writing Gradle, always use the latest best practices to avoid eager initialization.
