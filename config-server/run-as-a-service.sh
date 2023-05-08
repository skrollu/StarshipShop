#!/bin/sh

# Copy service file, incase if there are any changes
sudo cp config-server.service /etc/systemd/system/config-server.service
# reload configurations incase if service file has changed
sudo systemctl daemon-reload
# restart the service
sudo systemctl restart config-server
# start of VM restart
sudo systemctl enable config-server