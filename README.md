# Prime Spring Vault Example



```bash

cd docker

docker-compose -f docker-compose.yml up -d vault

docker exec -it vault sh



# Iniciar vault
vault operator init -ca-cert=/vault/config/ssl/vault-cert.pem -key-shares=1 -key-threshold=1 -format=json

# Los datos que nos interesan son unseal_keys_b64 y root_token

# Unseal Vault server
vault operator unseal -ca-cert=/vault/config/ssl/vault-cert.pem [unseal_keys_b64]

# Preparara el vault server
vault login -ca-cert=/vault/config/ssl/vault-cert.pem [root_token]

vault policy write -ca-cert=/vault/config/ssl/vault-cert.pem testerrole /home/vault/approlePolicy.hcl

vault auth enable -ca-cert=/vault/config/ssl/vault-cert.pem approle

vault write -ca-cert=/vault/config/ssl/vault-cert.pem auth/approle/role/testrole secret_id_ttl=10m token_ttl=20m token_max_ttl=30m secret_id_num_uses=40 policies=testerrole

vault read  -ca-cert=/vault/config/ssl/vault-cert.pem auth/approle/role/testrole/role-id

# copiar role_id a docker/role_id

vault write --force  -ca-cert=/vault/config/ssl/vault-cert.pem auth/approle/role/testrole/secret-id

# copiar secret_id a docker/secret_id

exit

docker-compose -f docker-compose.yml up -d vault-agent

open https://localhost:8200/ui

docker-compose -f docker-compose.yml logs

```