FROM amazoncorretto:21

WORKDIR /app

COPY . .

RUN yum update -y && yum install -y findutils

CMD ["java", "-jar", "auth-service.jar"]
