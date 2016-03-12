#!/bin/bash

set -e

if [[ -z "$DB_PORT" ]]; then
    DB_PORT=5432
fi

PSQL="psql -h $DB_HOST -p $DB_PORT -U postgres -At"

retries=10
while ! $PSQL -c 'SELECT 1' > /dev/null; do
    retries=$((retries - 1))
    if [[ $retries -eq 0 ]]; then
        echo >&2 "Could not connect to PostgreSQL on $DB_HOST:$DB_PORT."
        exit 1
    fi
    sleep 1
done

if [[ -z "$($PSQL -c "SELECT usename FROM pg_user WHERE usename = '$DB_USER'")" ]]; then
    $PSQL -c "CREATE USER $DB_USER WITH PASSWORD '$DB_PASSWORD'"
    echo >&2 "Created the PostgreSQL user $DB_USER."
fi

if [[ -z "$($PSQL -c "SELECT datname FROM pg_database WHERE datname = '$DB_NAME'")" ]]; then
    $PSQL -c "CREATE DATABASE $DB_NAME WITH OWNER $DB_USER"
    echo >&2 "Created the PostgreSQL database $DB_NAME."
fi
