#!/bin/bash

# create database

USER="root"
DATABASE="financedb"


#crate database
mysql -u $USER << EOF
CREATE DATABASE $DATABASE
EOF
[ $? -eq 0 ] && echo "created DB" || echo DB already exists


#grant right
mysql -u $USER << EOF
grant all on financedb.* to 'mysql'@127.0.0.1 identified by 'mysql'
EOF
[ $? -eq 0 ] && echo "grant right success" || echo "grant right failure" 

