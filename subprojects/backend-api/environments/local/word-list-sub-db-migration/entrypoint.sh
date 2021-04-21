#!/usr/bin/env bash

set -e
set -x

java  \
  ${DATASOURCE_JDBC_URL:+ -Ddatasource.url=}${DATASOURCE_JDBC_URL} \
  ${DATASOURCE_USERNAME:+ -Ddatasource.username=}${DATASOURCE_USERNAME} \
  ${DATASOURCE_PASSWORD:+ -Ddatasource.password=}${DATASOURCE_PASSWORD} \
  -jar /app/db-migrate.jar
