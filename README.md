# SEACON 2017: Föderation statt Integration - Skalierbare IAM-Anbindung

Hier stellen wir die Sourcen zur Demo vom Vortrag ["Föderation statt Integration - Skalierbare IAM-Anbindung"](https://conciso.de/event/seacon-2017/) unseres Kollegen [Sven-Torben Janus](https://github.com/sventorben) auf der [SEACON 2017](http://www.sea-con.de/seacon2017.html) zur Verfügung.

## Demo starten und stoppen

**Starten***
```docker-compose up```

**Stoppen***
```docker-compose down```

## Integrierte Authentifizierung

* Benutzername: seacon
* Passwort: seacon
* Basic Auth Bas64: c2VhY29uOnNlYWNvbg==

### einzelne Anfrage

```
HASH=c2VhY29uOnNlYWNvbg==
curl -H "Authorization: Basic $HASH" localhost:8081/red
```

### mehrere Anfragen

```bash
cd integrated
mvn gatling:execute
```

## Föderative Authentifizierung

### einzelne Anfrage

**Benutzername und Passwort setzen**
```bash
set USER=seacon
set PASS=seacon
```

**Authentifizieren und Token holen**
```bash
set RESULT=`curl -s --data "grant_type=password&client_id=keycloak-example&username=${USER}&password=${PASS}" http://localhost:9080/auth/realms/keycloak-example/protocol/openid-connect/token`
set TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`
````

**Service anfragen**
```bash
curl -H "Authorization: Bearer $TOKEN" localhost:8082/red
```

### mehrere Anfragen

```bash
cd federated
mvn gatling:execute
```

## Statistiken anschauen

[http://localhost:1936/](http://localhost:1936/)
* Benutzername: admin
* Passwort: admin

## Keycloak Admin Console

[http://localhost:9080/auth/admin/](http://localhost:9080/auth/admin/)
* Benutzername: admin
* Passwort: admin
