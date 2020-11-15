# Keycloak HTTP Webhook Provider

![CI/CD](https://github.com/svenstaro/keycloak-http-webhook-provider/workflows/CI/CD/badge.svg)

A Keycloak provider that posts events to a URL via HTTP POST as JSON

## How to build

    mvn clean install

## How to install

Copy the provider to the correct directory:

    cp target/keycloak_http_webhook_provider.jar /opt/keycloak/standalone/deployments/
    
In your config, you'll have to find the section
```xml
<subsystem xmlns="urn:jboss:domain:keycloak-server:1.1">
```
and inside that section insert

```xml
<spi name="eventsListener">
    <provider name="http_webhook" enabled="true">
        <properties>
            <property name="serverUrl" value="http://example.com/webhook"/>
            <property name="username" value="your-basic-auth-user"/>
            <property name="password" value="your-basic-auth-password"/>
        </properties>
    </provider>
</spi>
```

Next, add the provider as an event listener on your Keycloak instance. For instance:

    /opt/jboss/keycloak/bin/kcadm.sh update events/config -s eventsListeners+=http_webhook \
        --no-config --server http://localhost:8080/auth --user admin --password admin --realm master

## How to develop

To very quickly start a development instance of Keycloak with Docker, you can ran

    keycloak/run-keycloak-container.sh

## How to publish

Snapshot version are automatically published via GitHub Actions.
