FROM java:8
VOLUME /tmp
ADD ${workspace}/target/user-authentication-1.0.0.jar app.jar
ADD ${workspace}/target/classes/application.yml application.yml
ADD ${workspace}/target/classes/application-sit.yml application-sit.yml
RUN bash -c 'touch /app.jar'
EXPOSE 10001
EXPOSE 10002
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=sit"]