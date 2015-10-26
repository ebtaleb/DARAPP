#!/bin/bash

db=""

function init_db {
    local db_name=$1
    mysql -hlocalhost -uroot -pnyanyanya -e "CREATE DATABASE $db_name DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
}

if [ $# -eq 0 ]
then
    echo "No arguments supplied"
    exit 1
fi

if [ $# -lt 2 ]
then
    echo "create_db [-cd] db_name"
    exit 1
fi

while getopts ":c:d:" opt; do
    case "$opt" in
        c)
            mysqladmin -uroot -pnyanyanya create $OPTARG
            db="$OPTARG"
            echo "database $db created"
            break
            ;;
        d)
            mysqladmin -uroot -pnyanyanya drop $OPTARG
            db="$OPTARG"
            echo "database $db deleted"
            exit 0
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            exit 1
            ;;
    esac
done

table="use $db; CREATE TABLE IF NOT EXISTS TRACKS (ID INT (5) NOT NULL AUTO_INCREMENT,TITLE VARCHAR (20) NOT NULL,SINGER VARCHAR (20) NOT NULL,PRIMARY KEY ( ID ));"

user_table="use $db; CREATE TABLE IF NOT EXISTS USERS (ID INT (5) NOT NULL AUTO_INCREMENT,NAME VARCHAR (20) NOT NULL,PASSWORD VARCHAR (20) NOT NULL, EMAIL VARCHAR(60) NOT NULL, PRIMARY KEY ( ID ), UNIQUE (email));"

mysql -hlocalhost -uroot -pnyanyanya -e "$table"
echo "table TRACKS created"
mysql -hlocalhost -uroot -pnyanyanya -e "$user_table"
echo "table USERS created"
