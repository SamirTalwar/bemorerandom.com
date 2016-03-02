FROM azul/zulu-openjdk:8

EXPOSE 8080

RUN apt-get update && apt-get install -y curl

ENV MAVEN_VERSION 3.3.9
ENV PATH /opt/maven/bin:$PATH

RUN mkdir /opt/maven
RUN curl -fsSL "http://mirror.ox.ac.uk/sites/rsync.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz" > /opt/maven/apache-maven-bin.tar.gz
RUN tar xf /opt/maven/apache-maven-bin.tar.gz -C /opt/maven --strip-components=1 \
    && rm /opt/maven/apache-maven-bin.tar.gz

COPY settings.xml /root/.m2/settings.xml
COPY pom.xml /app/pom.xml
COPY api/pom.xml /app/api/pom.xml
WORKDIR /app
RUN mvn dependency:resolve

COPY api/src /app/api/src
RUN mvn package -DskipTests=true

ENTRYPOINT ["mvn", "--projects=api"]
CMD ["exec:java"]