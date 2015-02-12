############################################################
# Dockerfile to run an OrientDB (Graph) Container
# http://crosbymichael.com/dockerfile-best-practices.html
# http://crosbymichael.com/dockerfile-best-practices-take-2.html
############################################################

FROM dockerfile/java:oracle-java8
MAINTAINER Bill Carlson <bill@coacoas.net>

# Update the default application repository sources list
RUN apt-get update

# Install supervisord
RUN apt-get -y install supervisor curl libmysql-java
RUN mkdir -p /var/log/supervisor

ENV ORIENTDB_VERSION 2.0.2
ENV URL  http://www.orientechnologies.com/download.php?email=unknown@unknown.com&file=orientdb-community-${ORIENTDB_VERSION}.tar.gz&os=linux

RUN curl $URL | tar xzvf - -C /opt && \
  mv /opt/orientdb-community-${ORIENTDB_VERSION} /opt/orientdb && \
  rm -rf /opt/orientdb/databases/* ~/orientdb && \
  ln -s /usr/share/java/mysql.jar /opt/orientdb/lib/mysql-connector.jar

WORKDIR    /opt/orientdb/bin/

# use supervisord to start orientdb
ADD supervisord.conf /etc/supervisor/conf.d/supervisord.conf

EXPOSE 2424
EXPOSE 2480

# Set the user to run OrientDB daemon
USER root

# Default command when starting the container
ENTRYPOINT ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
