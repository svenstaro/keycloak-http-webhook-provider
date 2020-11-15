# Keycloak HTTP Webhook Provider

A Keycloak provider that posts events to a URL via HTTP POST as JSON

## How to build

    mvn clean install

## How to install

Copy the provider to the correct directory:

    cp target/keycloak_http_webhook_provider.jar /opt/keycloak/standalone/deployments/

Next, add the provider as an event listener on your Keycloak instance. For instance:

    /opt/jboss/keycloak/bin/kcadm.sh update events/config -s eventsListeners+=http_webhook \
        --no-config --server http://localhost:8080/auth --user admin --password admin --realm master

## How to develop

To very quickly start a development instance of Keycloak with Docker, you can ran

    keycloak/run-keycloak-container.sh

## How to publish

Snapshot version are automatically published via GitHub Actions.
