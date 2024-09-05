FROM eclipse-temurin:21

ARG JAR_FILE

ENV DEBIAN_FRONTEND noninteractive
ENV APPLICATION_HOME /opt/flight-performances

# workdir switch
WORKDIR "${APPLICATION_HOME}"

RUN groupadd user
RUN useradd -g user user

# copy files
COPY "target/${JAR_FILE}" "${APPLICATION_HOME}"

ENV JAR_PATH="${APPLICATION_HOME}/${JAR_FILE}"

USER user
# run the jar
CMD java ${JAVA_OPTS} -jar ${JAR_PATH}
