#!/usr/bin/env bash

set -e

VERSION='1.0.0'
GROUP='laplacian-sample'

PROJECT_BASE_DIR=$(cd $"${BASH_SOURCE%/*}/../" && pwd)
SUBPROJECTS_DIR="$PROJECT_BASE_DIR/subprojects"
DIST_DIR="$PROJECT_BASE_DIR/dist"
LAPLACIAN_HOME="$HOME/.laplacian"
LAPLACIAN_CACHE_DIR="$LAPLACIAN_HOME/cache"

DOMAIN_MODEL_MODULE_NAME='domain-model'
DOMAIN_MODEL_DIR="$SUBPROJECTS_DIR/$DOMAIN_MODEL_MODULE_NAME/model"
DOMAIN_MODEL_SCHEMA_DIR="$SUBPROJECTS_DIR/$DOMAIN_MODEL_MODULE_NAME/schema"
DOMAIN_MODEL_PLUGIN_MODULE_NAME="$DOMAIN_MODEL_MODULE_NAME-plugin"
DOMAIN_MODEL_PLUGIN_DIR="$SUBPROJECTS_DIR/$DOMAIN_MODEL_PLUGIN_MODULE_NAME"
DOMAIN_MODEL_PLUGIN_BUILT_MODULE="$DOMAIN_MODEL_PLUGIN_DIR/build/libs/$GROUP.$DOMAIN_MODEL_PLUGIN_MODULE_NAME-$VERSION.jar"

APPLICATION_MODEL_MODULE_NAME='application-model'
APPLICATION_MODEL_DIR="$SUBPROJECTS_DIR/$APPLICATION_MODEL_MODULE_NAME/model"
APPLICATION_MODEL_SCHEMA_DIR="$SUBPROJECTS_DIR/$APPLICATION_MODEL_MODULE_NAME/schema"

BACKEND_API_PROJECT_NAME='backend-api'
BACKEND_API_PROJECT_DIR="$SUBPROJECTS_DIR/$BACKEND_API_PROJECT_NAME"

METAMODEL_MODEL_URL="https://github.com/nabla-squared/laplacian.core/releases/download/v1.0.0/laplacian-core.metamodel-model-1.0.0.zip"
METAMODEL_PLUGIN_URL="https://github.com/nabla-squared/laplacian.core/releases/download/v1.0.0/laplacian-core.metamodel-plugin-1.0.0.jar"
DOMAIN_MODEL_PLUGIN_TEMPLATE_URL="https://github.com/nabla-squared/laplacian.core/releases/download/v1.0.0/laplacian-core.domain-model-plugin-template-1.0.0.zip"
DOMAIN_MODEL_JSON_SCHEMA_TEMPLATE_URL="https://github.com/nabla-squared/laplacian.core/releases/download/v1.0.0/laplacian-core.domain-model-json-schema-template-1.0.0.zip"

BACKEND_API_DOMAIN_MODEL_URL="https://github.com/nabla-squared/laplacian.arch/releases/download/v1.0.0/laplacian-arch.backend-api-domain-model-1.0.0.zip"
BACKEND_API_DOMAIN_MODEL_PLUGIN_URL="https://github.com/nabla-squared/laplacian.arch/releases/download/v1.0.0/laplacian-arch.backend-api-domain-model-plugin-1.0.0.jar"
BACKEND_API_PROJECT_TEMPLATE_URL="https://github.com/nabla-squared/laplacian.arch/releases/download/v1.0.0/laplacian-arch.backend-api-project-template-1.0.0.zip"
BACKEND_API_SPRINGBOOT2_SERVICE_TEMPLATE_URL="https://github.com/nabla-squared/laplacian.arch/releases/download/v1.0.0/laplacian-arch.backend-api-springboot2-service-template-1.0.0.zip"
DATASET_FLYWAY_MIGRATION_TEMPLATE_URL="https://github.com/nabla-squared/laplacian.arch/releases/download/v1.0.0/laplacian-arch.dataset-flyway-migration-template-1.0.0.zip"
DEPLOYMENT_DOMAIN_MODEL_URL="https://github.com/nabla-squared/laplacian.arch/releases/download/v1.0.0/laplacian-arch.deployment-domain-model-1.0.0.zip"
DEPLOYMENT_DOMAIN_MODEL_PLUGIN_URL="https://github.com/nabla-squared/laplacian.arch/releases/download/v1.0.0/laplacian-arch.deployment-domain-model-plugin-1.0.0.jar"

GRADLE="./gradlew"
ZIP="jar -cfM"

main() {
  # set -x
  generate_domain_model_schema || die
  generate_domain_model_plugin_src || die
  build_domain_model_plugin || die
  generate_application_model_schema || die
  generate_backend_api_project_src || die
}

die() {
  echo "$0 FAILED!!" 1>&2
  exit 1
}

generate_domain_model_schema() {
  laplacian generate \
    --template $DOMAIN_MODEL_JSON_SCHEMA_TEMPLATE_URL \
    --model $METAMODEL_MODEL_URL \
    --plugin $METAMODEL_PLUGIN_URL \
    --destination $DOMAIN_MODEL_SCHEMA_DIR \
    --clean
}

generate_domain_model_plugin_src() {
  laplacian generate \
    --template $DOMAIN_MODEL_PLUGIN_TEMPLATE_URL \
    --model $DOMAIN_MODEL_DIR \
    --model $METAMODEL_MODEL_URL \
    --schema "$DOMAIN_MODEL_SCHEMA_DIR/model-schema.json" \
    --plugin $METAMODEL_PLUGIN_URL \
    --destination $DOMAIN_MODEL_PLUGIN_DIR \
    --clean
}

build_domain_model_plugin() {
  (cd $DOMAIN_MODEL_PLUGIN_DIR
    $GRADLE build) && \
  mkdir -p $DIST_DIR && \
  cp -f $DOMAIN_MODEL_PLUGIN_BUILT_MODULE $DIST_DIR
}

generate_application_model_schema() {
  laplacian generate \
    --template $DOMAIN_MODEL_JSON_SCHEMA_TEMPLATE_URL \
    --model $BACKEND_API_DOMAIN_MODEL_URL \
    --model $DEPLOYMENT_DOMAIN_MODEL_URL \
    --model $METAMODEL_MODEL_URL \
    --plugin $METAMODEL_PLUGIN_URL \
    --destination $APPLICATION_MODEL_SCHEMA_DIR \
    --clean
}

generate_backend_api_project_src() {
  laplacian generate \
    --model $DOMAIN_MODEL_DIR \
    --model $APPLICATION_MODEL_DIR \
    --schema "$APPLICATION_MODEL_SCHEMA_DIR/model-schema.json" \
    --template $BACKEND_API_PROJECT_TEMPLATE_URL \
    --template $BACKEND_API_SPRINGBOOT2_SERVICE_TEMPLATE_URL \
    --template $DATASET_FLYWAY_MIGRATION_TEMPLATE_URL \
    --plugin $METAMODEL_PLUGIN_URL \
    --plugin $BACKEND_API_DOMAIN_MODEL_PLUGIN_URL \
    --plugin $DEPLOYMENT_DOMAIN_MODEL_PLUGIN_URL \
    --plugin $DOMAIN_MODEL_PLUGIN_BUILT_MODULE \
    --destination $BACKEND_API_PROJECT_DIR \
    --clean
}

main