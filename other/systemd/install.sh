#!/usr/bin/env sh
BASEDIR=$(dirname "$0")
cd "$BASEDIR" || exit 1

SERVICE=$1

cp "solarthing-$SERVICE.service" /etc/systemd/system
