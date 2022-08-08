./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar
sudp docker build -t springio/gs-spring-boot-docker .
clear
sudo docker run -p 1111:1111 springio/gs-spring-boot-docker
