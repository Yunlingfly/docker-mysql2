docker rm $(docker ps -a -q)
docker rmi mymysql:test
docker rmi test1jar:test