#ldap.cce.ufpr.br:389 QzfBGhHFKaV1XzgVTrCx

FROM gradle:jdk8

RUN git clone -b ldaptest https://gitlab+deploy-token-5:QzfBGhHFKaV1XzgVTrCx@gitlab.ufpr.br/ufpr-convida/ufprconvida-backend.git

WORKDIR ufprconvida-backend/
RUN gradle build
RUN ls -l build/libs/
RUN pwd


ENV JAVA_OPTS=""

FROM openjdk:8

COPY --from=0 /home/gradle/ufprconvida-backend/build/libs/convida-0.0.1-SNAPSHOT.jar /

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/convida-0.0.1-SNAPSHOT.jar" ]
