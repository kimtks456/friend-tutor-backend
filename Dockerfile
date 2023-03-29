# FROM iits/jdk17-temurin:jdk17-temurin-alpine
FROM eclipse-temurin:17.0.6_10-jre-jammy

# Set working directory
WORKDIR /app

# Install MySQL and set up initial configuration
RUN apt-get update && \
    apt-get -y install sudo && \
    sudo apt-get install -y mysql-server && \
    sudo service mysql start && \
    mysql -u root -e "CREATE DATABASE lms DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" && \
    mysql -u root -e "CREATE USER 'demo'@'%' IDENTIFIED BY 'demo1234';" && \
    mysql -u root -e "GRANT ALL PRIVILEGES ON lms.* TO 'demo'@'%';" && \
    mysql -u root -e "FLUSH PRIVILEGES;"

# Copy Spring Boot application to container
COPY build/libs/solutionChallenge-0.0.1-SNAPSHOT.jar app.jar

ADD start.sh /
RUN chmod +x /start.sh

EXPOSE 8080

# Start Spring Boot application
CMD ["/start.sh"]
