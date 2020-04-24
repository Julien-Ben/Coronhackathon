#!/bin/bash

# Script
echo "Starting deploy script"

# Scp to instance
cd ..
chmod 400 amazonkey-coronhackathon.pem

scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -i "amazonkey-coronhackathon.pem" /home/travis/build/JulienB-Epfl/Coronhackathon/back/build/libs/backend-0.0.1-SNAPSHOT.jar ec2-user@18.234.142.249:/home/ec2-user
ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -i "amazonkey-coronhackathon.pem" ec2-user@ec2-18-234-142-249.compute-1.amazonaws.com < remote-script.sh

exit 0
