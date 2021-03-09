# Exercise-08

```
brew install minikube

minikube start

# make sure is connected
kubectl version

kubectl get nodes

kubectl get pods

kubectl apply -f nba-deployment.yaml

kubectl get deployments

kubectl apply -f tp-deployment.yaml

# register and expose ports to start a service
kubectl apply -f nba-service.yaml

kubectl apply -f tp-service.yaml

# enble nba service
minicube service --url notebookapp

minicube exec -it  ##deployment-id##-- /bin/bash

kubctl scale deployment textprocessor --replicas=3







```

## Task
Google Cloud
Kubernetes Engine -> Cluster -> Create Cluster (all by default)
Kubernetes Engine -> Workload -> Deploy
用完記得要刪掉cluster不然會一直收錢