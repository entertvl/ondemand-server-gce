# ondemand-server-gce

WIP: This Bungeecord plugin launches GCE instances when player connection.

## authentication

This uses the Google Cloud Client Library to automatically detect and utilize a principal with IAM permissions granted to the target GCE instance through Application Default Credentials (ADC).

## config

Please note that the name in the 'servers' section of config.yml (e.g., example) should match the same section of Bungeecord side.

```yml
servers:
  example:
    project_id: 'example-project-id'
    zone: 'asia-northeast1-b'
    instance: 'example-server'
```
