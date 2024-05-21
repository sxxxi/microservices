FROM maven:latest AS base
WORKDIR /app
COPY . .
RUN mvn clean package
CMD ["ls"]
