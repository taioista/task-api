
# task-api

Application that pull data from all videos that have been uploaded to a YouTube channel.

The application utilise HTTP API that consists of two methods:

	1. POST /api/tasks/{youtubeChannelId} - creates a new task to parse the

	channel, returns taskId

	2. GET /api/tasks/ - returns the list of created tasks with their statuses

	3. GET /api/tasks/{taskId} - returns list of videos that have been found in the specified channel as an array of objects: video id, description, link to the video


The application was developed with:

- Apache Maven: 3.5.3

- JDK: 11

- Spring Boot: 2.6.4

- Database: PostgreSQL
  

To run the application you will need:

- Create the environment variablezs above:

- API_KEY - API key to be able to access Youtube Data Api V3 (https://developers.google.com/youtube/v3/getting-started?hl=pt-br)

- REPOSITORY_NAME - Name of the repository name in Docker Hub

- API_DB_USERNAME - Database user

- API_DB_PASSWORD - Database password

- Docker configured (https://docs.docker.com/get-started/)

- Run the command above to build the application and the image:

	````
	mvn clean package -P build-docker-image
	````
- Run docker-compose.yml inside docker folder on this repository

 
 If you want to run it locally, use the command above:
	 ````
	 mvn spring-boot:run
	 ````