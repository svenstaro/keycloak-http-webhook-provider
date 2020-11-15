#!/usr/bin/env bash
#
# We have to do it this weird way because Keycloak runs startup scripts BEFORE
# Keycloak has started so it won't work as we'll have to use the HTTP REST API.
# So this script background itself and just polls for the API to become available.

add_http_listener() {
    for i in {1..10}; do
        curl --silent --head --fail http://localhost:8080/auth && \
        /opt/jboss/keycloak/bin/kcadm.sh update events/config -s eventsListeners+=http_webhook \
            --no-config --server http://localhost:8080/auth --user admin --password admin --realm master && \
            exit 0
        sleep 2
    done
    exit 1
}

add_http_listener &
