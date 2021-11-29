docker run --name keycloak -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e DB_VENDOR=h2 -p 8180:8080 -p 8543:8443 jboss/keycloak:15.0.2
