FROM openjdk:8

EXPOSE 9000

ENV WORKING_DIR /app
ENV SBT_VERSION 1.0.2

RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

WORKDIR $WORKING_DIR

COPY app $WORKING_DIR/app
COPY conf $WORKING_DIR/conf
COPY public $WORKING_DIR/public
COPY test $WORKING_DIR/test

COPY project/build.properties $WORKING_DIR/project/build.properties
COPY project/plugins.sbt $WORKING_DIR/project/plugins.sbt
COPY build.sbt $WORKING_DIR/build.sbt

ENTRYPOINT ["sbt"]
CMD ["run"]