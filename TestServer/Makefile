all:
build:
	mkdir -p minecraft
	# todo autorestart=no
	# ctrl + c to exit
	docker run -d -e EULA=true  -p 25569:25565 -v `pwd`/minecraft:/minecraft --name spigot nimmis/spigot:latest
	# container logs
	# ctrl + c to exit
	docker logs spigot -f
container_start:
	docker start spigot
container_stop:
	docker stop spigot
container_logs:
	# see container logs
	# ctrl + c to exit
	docker logs spigot -f
container_shell:
	docker exec -it spigot /bin/bash
start:
	# start the minecraft server
	docker exec spigot mc_start
stop:
	# stop the minecraft server
	docker exec spigot mc_stop
logs:
	# see minecraft logs
	docker exec spigot mc_log
status:
	docker exec spigot mc_status
send:
	if [ -z "${cmd}" ]; then echo "usage: make send cmd='<some command>'"; else docker exec spigot mc_send "${cmd}"; fi;
restart: stop start
container_restart: containter_stop container_start
clean:
	if [ ! -z "$(docker ps --filter name=spigot -q)" ]; then docker kill spigot; fi;
	docker rm spigot
	rm -rf minecraft
