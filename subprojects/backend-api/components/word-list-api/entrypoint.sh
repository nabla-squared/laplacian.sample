#!/usr/bin/env bash

set -e
set -x

java \
  ${ENABLE_DEBUG:+ -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:${DEBUG_PORT}} \
  ${ENABLE_JMX:+ -Dcom.sun.management.jmxremote} \
  ${ENABLE_JMX:+ -Djava.rmi.server.hostname=0.0.0.0} \
  ${ENABLE_JMX:+ -Dcom.sun.management.jmxremote.port=${JMX_PORT}} \
  ${ENABLE_JMX:+ -Dcom.sun.management.jmxremote.rmi.port=${JMX_PORT}} \
  ${ENABLE_JMX:+ -Dcom.sun.management.jmxremote.authenticate=false} \
  ${ENABLE_JMX:+ -Dcom.sun.management.jmxremote.ssl=false} \
  -Djava.security.egd=file:/dev/./urandom \
  ${IMAGE_VERSION:+ -Dimage.version=}${IMAGE_VERSION} \
  -jar /app/api.jar \
  ${ENABLE_JMX:+ --spring.jmx.enabled=true} \
  ${ENABLE_JMX:+ --spring.datasource.hikari.register-mbeans=true} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_USERNAME:+ --datasource.word-list-main-db.username=}${DATASOURCE_WORD_LIST_MAIN_DB_USERNAME} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_PASSWORD:+ --datasource.word-list-main-db.password=}${DATASOURCE_WORD_LIST_MAIN_DB_PASSWORD} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_JDBC_URL:+ --datasource.word-list-main-db.jdbc-url=}${DATASOURCE_WORD_LIST_MAIN_DB_JDBC_URL} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_CONNECTION_TIMEOUT:+ --datasource.word-list-main-db.hikari.connection-timeout=}${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_CONNECTION_TIMEOUT} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_IDLE_TIMEOUT:+ --datasource.word-list-main-db.hikari.idle-timeout=}${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_IDLE_TIMEOUT} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_MAX_LIFETIME:+ --datasource.word-list-main-db.hikari.max-lifetime=}${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_MAX_LIFETIME} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_MINIMUM_IDLE:+ --datasource.word-list-main-db.hikari.minimum-idle=}${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_MINIMUM_IDLE} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_MAXIMUM_POOL_SIZE:+ --datasource.word-list-main-db.hikari.maximum-pool-size=}${DATASOURCE_WORD_LIST_MAIN_DB_HIKARI_MAXIMUM_POOL_SIZE} \
  ${DATASOURCE_WORD_LIST_MAIN_DB_LOGGING_SLOW_QUERY_THRESHOLD_MILLS:+ --datasource.word-list-main-db.logging.slow-query-threshold-mills=}${DATASOURCE_WORD_LIST_MAIN_DB_LOGGING_SLOW_QUERY_THRESHOLD_MILLS} \
  ${DATASOURCE_WORD_LIST_SUB_DB_USERNAME:+ --datasource.word-list-sub-db.username=}${DATASOURCE_WORD_LIST_SUB_DB_USERNAME} \
  ${DATASOURCE_WORD_LIST_SUB_DB_PASSWORD:+ --datasource.word-list-sub-db.password=}${DATASOURCE_WORD_LIST_SUB_DB_PASSWORD} \
  ${DATASOURCE_WORD_LIST_SUB_DB_JDBC_URL:+ --datasource.word-list-sub-db.jdbc-url=}${DATASOURCE_WORD_LIST_SUB_DB_JDBC_URL} \
  ${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_CONNECTION_TIMEOUT:+ --datasource.word-list-sub-db.hikari.connection-timeout=}${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_CONNECTION_TIMEOUT} \
  ${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_IDLE_TIMEOUT:+ --datasource.word-list-sub-db.hikari.idle-timeout=}${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_IDLE_TIMEOUT} \
  ${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_MAX_LIFETIME:+ --datasource.word-list-sub-db.hikari.max-lifetime=}${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_MAX_LIFETIME} \
  ${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_MINIMUM_IDLE:+ --datasource.word-list-sub-db.hikari.minimum-idle=}${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_MINIMUM_IDLE} \
  ${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_MAXIMUM_POOL_SIZE:+ --datasource.word-list-sub-db.hikari.maximum-pool-size=}${DATASOURCE_WORD_LIST_SUB_DB_HIKARI_MAXIMUM_POOL_SIZE} \
  ${DATASOURCE_WORD_LIST_SUB_DB_LOGGING_SLOW_QUERY_THRESHOLD_MILLS:+ --datasource.word-list-sub-db.logging.slow-query-threshold-mills=}${DATASOURCE_WORD_LIST_SUB_DB_LOGGING_SLOW_QUERY_THRESHOLD_MILLS} \
  ${GOOGLE_SPREADSHEET_API_KEY:+ --google-spreadsheet.api-key=}${GOOGLE_SPREADSHEET_API_KEY} \
  ${REST_API_SAMPLE_WORD_SPREADSHEET_ID:+ --rest-api-sample-word.spreadsheet-id=}${REST_API_SAMPLE_WORD_SPREADSHEET_ID} \
