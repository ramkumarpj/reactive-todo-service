FROM adoptopenjdk/openjdk15:ubuntu-jre

VOLUME /tmp
EXPOSE 8080
ADD /build/libs/todo-0.0.1-SNAPSHOT.jar todo-0.0.1-SNAPSHOT.jar
ADD src/main/scripts/startApp.sh startApp.sh

RUN  apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*

RUN wget -q "https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh"
RUN wget -q "https://raw.githubusercontent.com/vishnubob/wait-for-it/master/LICENSE"
RUN chmod 744 wait-for-it.sh
ENTRYPOINT [ "/bin/bash", "-c" ]
