FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean package
FROM openjdk:11-jre-slim
WORKDIR /app
RUN apt-get update && apt-get install -y \
    xvfb \
    x11vnc \
    libgtk-3-0 \
    libgl1-mesa-glx \
    libx11-6 \
    postgresql
ENV DB_PORT=5432
ENV DB_USER=postgresnew
ENV DB_PASSWORD=111
COPY --from=build /app/SeaRadarApp/target/SeaRadarApp-1.0-SNAPSHOT.jar ./app.jar
COPY script.sh .
RUN chmod +x script.sh
ENTRYPOINT ["./script.sh"]

