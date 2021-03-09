# Exercise-08

```
brew install minikube

minikube start

# make sure kubectl is updateed to current minikube version
minikube kubectl -- get pods -A

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
minikube service --url notebookapp

minikube exec -it  ##deployment-id##-- /bin/bash

kubectl scale deployment textprocessor --replicas=3



```

## Task
Google Cloud
Kubernetes Engine -> Cluster -> Create Cluster (all by default)
Kubernetes Engine -> Workload -> Deploy
用完記得要刪掉cluster不然會一直收錢