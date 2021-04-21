#!/usr/bin/env bash
set -e
PROJECT_BASE_DIR=$(cd $"${BASH_SOURCE%/*}/../" && pwd)
SCRIPT_BASE_DIR="$PROJECT_BASE_DIR/scripts"
DEPLOYMENT_BASE_DIR=$PROJECT_BASE_DIR/environments/local
COMPONENT_BASE_DIR=$PROJECT_BASE_DIR/components
main () {
  build_components
  build_migration_tasks
  deploy_containers
}

build_components() {
  echo "Building deployable components..."
  build_word_list_api
}

build_migration_tasks() {
  echo "Building data migration tasks..."
  build_word_list_main_db_migration
  build_word_list_sub_db_migration
}

deploy_containers() {
  echo "Deploying application modules..."
  (cd $DEPLOYMENT_BASE_DIR
    docker-compose rm \
      --force

    docker-compose up \
      --force-recreate \
      --build
  )
}

#@+additional-declarations|/Users/iwauo.tajima/.laplacian/cache/laplacian-arch.backend-api-springboot2-service-template-1.0.0/scripts/{each environments as environment}/deploy-on-{hyphen environment.name}_additional-declarations_.hbs.sh@
build_word_list_api() {
  (cd $COMPONENT_BASE_DIR/word-list-api-lib
    ./gradlew publish
  )
  (cd $COMPONENT_BASE_DIR/word-list-api
    ./gradlew build
  )
}






#@additional-declarations|/Users/iwauo.tajima/.laplacian/cache/laplacian-arch.backend-api-springboot2-service-template-1.0.0/scripts/{each environments as environment}/deploy-on-{hyphen environment.name}_additional-declarations_.hbs.sh@
#@+additional-declarations|/Users/iwauo.tajima/.laplacian/cache/laplacian-arch.dataset-flyway-migration-template-1.0.0/scripts/{each environments as environment}/deploy-on-{hyphen environment.name}_additional-declarations_.hbs.sh@
build_word_list_main_db_migration() {
  (cd $DEPLOYMENT_BASE_DIR/word-list-main-db-migration
    ./gradlew build
  )
}
build_word_list_sub_db_migration() {
  (cd $DEPLOYMENT_BASE_DIR/word-list-sub-db-migration
    ./gradlew build
  )
}


#@additional-declarations|/Users/iwauo.tajima/.laplacian/cache/laplacian-arch.dataset-flyway-migration-template-1.0.0/scripts/{each environments as environment}/deploy-on-{hyphen environment.name}_additional-declarations_.hbs.sh@
#@+additional-declarations@
#@additional-declarations@

main
