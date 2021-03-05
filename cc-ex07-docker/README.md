# EX07-Docker
- docker介紹 https://www.docker.com/resources/what-container
- docker machine介紹 https://docs.docker.com/machine/
	- Docker Machine is a tool that lets you install Docker Engine on virtual hosts


## Task-1
docker-machine要另外裝！！！！ https://docs.docker.com/machine/install-machine/


``` 
docker-machine create --driver amazonec2 --amazonec2-region us-east-1 awsmachine
```

make sure credential is set


```
docker-machine env awsmachine

eval $(docker-machine env awsmachine)
```

這個時候docker cli已經是遠端到awsmachine的docker

```
docker run hello-world
```

# Task-2

-docker Compose已經裝好在docker desktop裡面了