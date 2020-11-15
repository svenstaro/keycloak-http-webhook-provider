#!/usr/bin/env bash

docker run --rm --name keycloak \
    -v $PWD/target/keycloak_http_webhook_provider-jar-with-dependencies.jar:/opt/jboss/keycloak/standalone/deployments/http_webhook_provider.jar \
    -v $PWD/keycloak/standalone-ha.xml:/opt/jboss/keycloak/standalone/configuration/standalone-ha.xml \
    -v $PWD/keycloak/startup-script.sh:/opt/jboss/startup-scripts/startup-script.sh \
    -e KEYCLOAK_USER=admin \
    -e KEYCLOAK_PASSWORD=admin \
    -p 8080:8080 \
    --add-host host.docker.internal:$(ip addr show docker0 | grep -Po 'inet \K[\d.]+') \
    "$@" \
    jboss/keycloak
