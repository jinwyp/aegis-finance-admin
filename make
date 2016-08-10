#!/bin/bash

script_dir=$(cd `dirname $0`; pwd);
files_dir=$(cd $script_dir/../files; pwd);


source ../aegis-docker/bin/aegis-config;
export container_name=aegis-finance-admin-dev
export project_name=aegis-finance-admin
export image_name=aegis-finance-admin
if [[ "$@" = "staging" ]]; then
    export create_param="-p 8081:8080 \
-v ${files_dir}:/app/files \
-v ${script_dir}/logs:/app/aegis-finance-admin/logs \
-v ${script_dir}/docker-config-staging:/app/aegis-finance-admin/config"
elif [[ "$@" = "testing" ]]; then
    export create_param="-p 8081:8080 \
-v ${files_dir}:/app/files \
-v ${script_dir}/logs:/app/aegis-finance-admin/logs \
-v ${script_dir}/docker-config-testing:/app/aegis-finance-admin/config"
else
    export create_param="-p 8081:8080 \
-v ${files_dir}:/app/files \
-v ${script_dir}/logs:/app/aegis-finance-admin/logs
-v ${script_dir}/docker-config-dev:/app/aegis-finance-admin/config"
fi
export build_type=boot
export ip=${aegis_finance_admin_ip};

mbt $@;

