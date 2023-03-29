# Backend for [Friend Tutor](https://github.com/2ood/friend-mentor-frontend)


## 🤓 Overview



This is the backend server for the **[Friend Tutor](https://github.com/2ood/friend-mentor-frontend)** project, built under the [Google Developer Student Club - GIST](https://gdsc.community.dev/gwangju-institute-of-science-and-technology/). It provides real-time functionalities based on REST API for user registration, login, and browsing of posts. The architecture is running Spring and MySQL simultaneously on a single VM instance in GCP.

## ✨ Functionality


- **<U>Authentication</U> :** Supports user registration and login using JWT, and most of the APIs require authentication, so an access token should be attached to the Authorization header.
- **<U>Posts</U> :** Users can upload and browse lectures, as well as view the most recent or most recommended lectures. In addition, the system provides the functionality to like or unlike a lecture post.
- **<U>Certification</U> :** Dedicated mentors who have actively participated can receive a certificate that includes their activity period, the number of uploaded posts, the total number of recommendations received, and their certification rank.
- **<U>API docs</U> :** Provides API documentation using Swagger. However, it is recommended to use browser-based translation as the documentation is written in Korean.

## 🏃‍ Deploy

In the 2023 GDSC Solution Challenge, GCP credit and a free domain were provided, so the project is being deployed using HTTPS. Spring and MySQL are running on a single GCP VM instance.

- **Backend domain :** [https://www.gdsc-gist-lms.page](https://www.gdsc-gist-lms.page)
- **API docs :** [https://www.gdsc-gist-lms.page/apidocs](https://www.gdsc-gist-lms.page/apidocs)

The frontend is deployed on Netlify and is receiving the API using the above domain.

- **Frontend domain :** [https://friends-tutor.netlify.app](https://friends-tutor.netlify.app/)


## 🔧 How to Setup
This project currently supports HTTPS and is being deployed, so its features can be easily accessed through the above domain. Therefore, here we will introduce the method of setting up a backend environment that is the same as our development environment <u>locally</u> and running the code so that APIs can be accessed at http://localhost:8080.

### Requirements
- Port 8080 must be available.
- Docker must be running.
### Using Docker

To facilitate easy backend environment setup, we provide Docker. Normally, the database and server should be executed in separate containers, but for simplicity, an image is provided that can run both MySQL and Spring in the same container.   
By running the following commands in the terminal, you can start the server, and receive responses via **[http://localhost:8080](http://localhost:8080/).**

- `docker pull mtkim/gdsc_friends_tutor:1.0.0`
- `docker run -it --name friends_tutor_BE -p 8080:8080 mtkim/gdsc_friends_tutor:1.0.0`

### Manual Setup

If Docker is not available, you can set up the backend environment directly by following the requirements below.

- JDK 17
- MySQL 8.0.32

You can run MySQL and Spring server by following these steps:

1. Clone the source code of the `demo` branch.
2. Create a new database named `lms`.
3. Navigate to the project directory and update the MySQL username and password in the `application-demo.properties` file. And grant privilege access to the user for the `lms` DB.
4. Run MySQL in background.
5. Build the project using the Gradle build command.
6. Run the application using the command **`java -jar <jar-file-name>.jar`**.
   
Now you should be able to supply APIs locally for the **demo version** of the [Friend Tutor](https://github.com/2ood/friend-mentor-frontend) project instead of the deploy version (HTTPS).

<i>If there are any issues with the setup, please feel free to contact `kimtks456@naver.com` for assistance.</i>
