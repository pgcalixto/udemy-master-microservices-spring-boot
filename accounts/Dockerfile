#Start with a base image containing Java runtime
FROM eclipse-temurin:21-alpine

# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER "Pedro Calixto"
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="Pedro Calixto"

# Add the application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]
