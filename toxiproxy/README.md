# Performante Authentifizierung mit Keycloak 

Hier stellen wir die Sourcen zu unserem Blogbeitrag [Performante Authentifizierung mit Keycloak](https://wp.me/p8GTPO-yV) bereit.

Das Beispiel setzt auf der Demo auf, die unser Kollege Sven-Torben Janus(@sventorben) regelmäßig in seinen Voträgen rund um das Thema Keycloak und OpenID Connect nutzt. Grundsätzlich kann für ein Setup daher die [Anleitung zur Demo](../README.md) genutzt werden.

Abweichend davon folgenden Hinweise:

## Demo starten und stoppen

Für das Ausführen der Demo sollte die Datei [docker-compose.yml](docker-compose.yml) in diesem Verzeichnis anstelle der [Datei](../docker-compose.yml) aus der Demo genutzt werden.

**Starten**
```bash
docker-compose up
```

**Stoppen**
```bash
docker-compose down
```

Der Unterschied besteht im Wesentlichen darin, dass für den Blogbeitrag der HAProxy durch einen [Toxiproxy](https://github.com/Shopify/toxiproxy) ausgetauscht wurde.

## Latenzen simulieren
Toxiproxy wird dazu genutzt Latenzen zwischen einem Service oder einer Keycloak-Instanz und dem LDAP-System simulieren zu können.

Eine Latenz von einer Sekunde (1000 ms) kann nach Starten der Demo beispielsweise wie folgt simuliert werden.
```bash
curl -s -XPOST -d '{"type" : "latency", "attributes" : {"latency" : 1000}}' http://localhost:8474/proxies/openldap/toxics
```
