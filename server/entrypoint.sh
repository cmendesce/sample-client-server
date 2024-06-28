#!/bin/sh

# Substitua o placeholder pelo valor da variável de ambiente
sed -i "s/FAULT_PERCENTAGE_PLACEHOLDER/${FAULT_PERCENTAGE:-100}/" /etc/envoy/envoy.yaml

# Inicie o Envoy
exec envoy -c /etc/envoy/envoy.yaml
