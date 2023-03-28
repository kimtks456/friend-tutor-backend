# Backend for [Friend Tutor](https://github.com/2ood/friend-mentor-frontend)


## 🤓 Overview



This is the backend server for the **[Friend Tutor](https://github.com/2ood/friend-mentor-frontend)** project, built under the [Google Developer Student Club - GIST](https://gdsc.community.dev/gwangju-institute-of-science-and-technology/). It provides real-time functionalities based on REST API for user registration, login, and browsing of posts. The architecture is running Spring and MySQL simultaneously on a single VM instance in GCP.

## ✨ Functionality


- <U>**Authentication</U> :** Supports user registration and login using JWT, and most of the APIs require authentication, so an access token should be attached to the Authorization header.
- <U>**Certification</U> :** Users who have actively participated in mentor activities can receive a certificate of recognition.
- <U>**Posts</U> :** Users can upload and browse lectures, as well as view the most recent or most recommended lectures. In addition, the system provides the functionality to like or unlike a lecture post.
- <U>**API docs</U> :** Provides API documentation using Swagger. However, it is recommended to use browser-based translation as the documentation is written in Korean.

## 🔧 How to Setup



### Using Docker

To facilitate easy backend environment setup, we provide Docker. By running the following commands in the terminal, you can start the server, and receive responses via **[http://localhost:8080](http://localhost:8080/).**

- `docker pull kitarp29/gdsc_dsi_api:1.0`
- `docker run -e DATABASE_URL -d --name pk -p 5000:5000 kitarp29/gdsc_dsi_api:1.0`

### Manual Setup

If Docker is not available, you can set up the backend environment directly by following the requirements below.

- JDK 17
- MySQL 8.0.32

You can run MySQL(local) and Spring server by following these steps:

1. Clone the backend source code from the repository.
2. Create a new database named `lms`.
3. Navigate to the project directory and update the MySQL username and password in the application-demo.properties file.
4. Run MySQL in background.
5. Build the project using the Gradle build command.
6. Run the application using the command **`java -jar <jar-file-name>.jar`**.

## 🏃‍ Deploy



In the 2023 GDSC Solution Challenge, GCP credit and a free domain were provided, so the project is being deployed using HTTPS. Spring and MySQL are running on a single GCP VM instance.

- **Backend domain :** [https://www.gdsc-gist-lms.page](https://www.gdsc-gist-lms.page)
- **API docs :** [https://www.gdsc-gist-lms.page/apidocs](https://www.gdsc-gist-lms.page/apidocs)

The frontend is deployed on Netlify and is receiving the API using the above domain.

- **Frontend domain :** [https://friends-mentor.netlify.app](https://friends-mentor.netlify.app/)
