FROM yonggyo00/ubuntu
ARG JAR_FILE=build/libs/restaurant-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV DDL_AUTO=none
ENV PYTHON_PATH=/bin/python3.9
ENV PYTHON_SCRIPT=/restaurant/

ENTRYPOINT ["java", "-jar", "-DconfigServerUrl=${CONFIG_SERVER}", "-Ddb.host=${DB_HOST}", "-Ddb.username=${DB_USERNAME}", "-Ddb.password=${DB_PASSWORD}", "-Dddl.auto=${DDL_AUTO}", "-Dpython.run.path=${PYTHON_PATH}", "-Dpython.script.path=${PYTHON_SCRIPT}", "app.jar"]

EXPOSE 5000