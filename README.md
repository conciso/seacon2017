# SEACON 2017: Föderation statt Integration - Skalierbare IAM-Anbindung

Hier stellen wir die Sourcen zur Demo vom Vortrag ["Föderation statt Integration - Skalierbare IAM-Anbindung"](https://conciso.de/event/seacon-2017/) unseres Kollegen [Sven-Torben Janus](https://github.com/sventorben) auf der [SEACON 2017](http://www.sea-con.de/seacon2017.html) zur Verfügung.

## Folien
Die Vortragsfolien finden sich [hier](http://www.sigs.de/download/seacon_2017/files/Fr11_Janus_Foederation-statt-Integration.pdf).

## Demo starten und stoppen

**Starten**
```bash
docker-compose up
```

**Stoppen**
```bash
docker-compose down
```

## Integrierte Authentifizierung

* Benutzername: seacon
* Passwort: seacon
* Basic Auth Base64: c2VhY29uOnNlYWNvbg==

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
USER=seacon
PASS=seacon
```

**Authentifizieren und Token holen**
```bash
RESULT=`curl -s --data "grant_type=password&client_id=keycloak-example&username=${USER}&password=${PASS}" http://localhost:9080/auth/realms/keycloak-example/protocol/openid-connect/token`
TOKEN=`echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g'`
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

## Statistiken

**anschauen**

[http://localhost:1936/](http://localhost:1936/)
* Benutzername: admin
* Passwort: admin

**Zurücksetzen**

Docker Container neu starten:
```bash
docker restart haproxy
```

## Keycloak Admin Console

[http://localhost:9080/auth/admin/](http://localhost:9080/auth/admin/)
* Benutzername: admin
* Passwort: admin
