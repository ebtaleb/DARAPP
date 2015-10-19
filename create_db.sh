#!/bin/bash

db=""

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
            break
            ;;
        d)
            mysqladmin -uroot -pnyanyanya drop $OPTARG
            exit 0
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            exit 1
            ;;
    esac
done

table="use $db; CREATE TABLE IF NOT EXISTS TRACKS (ID INT (5) NOT NULL,TITLE VARCHAR (20) NOT NULL,SINGER VARCHAR (20) NOT NULL,PRIMARY KEY ( ID ));"

mysql -hlocalhost -uroot -pnyanyanya -e "$table"
echo "database $db created, table TRACKS created"
