# Payment Card Cost API
 Payment Card Cost API


## To RUN
1. Generate package:
   mvn clean package
2. Build the app in Docker
   docker compose build app
3. Build and start services
   docker compose up
4. Done



## Docker commands

- To check if the images were created:
docker images
- To check if the containers are created: 
docker ps -a
- Delete images: docker image rm 5ad7fc  # (5ad represents the first 3 digits of the image ID)
- Delete containers: docker-compose down
