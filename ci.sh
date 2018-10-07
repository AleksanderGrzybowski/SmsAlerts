#! /bin/bash
set -e

BACKEND_IMAGE=kelog/smsalerts:latest
FRONTEND_IMAGE=kelog/smsalerts-frontend:latest

docker build -t ${BACKEND_IMAGE} . && docker push ${BACKEND_IMAGE}

cd frontend
docker build -t ${FRONTEND_IMAGE} . && docker push ${FRONTEND_IMAGE}

kubectl config use-context ovh

kubectl get pods | grep smsalerts | awk '{print $1}' | xargs kubectl delete pod
