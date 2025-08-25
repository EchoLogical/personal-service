#!/usr/bin/env sh
SERVICE_NAME='eureka-service'
IMAGE_NAME='eureka'
IMAGE_VERSION='latest'
IMAGE_REF="${IMAGE_NAME}:${IMAGE_VERSION}"

# Cek container ada atau tidak
if sudo docker ps -a --format '{{.Names}}' | grep -qw "${SERVICE_NAME}"; then
  # Jika container sedang berjalan, hentikan dulu
  if sudo docker ps --format '{{.Names}}' | grep -qw "${SERVICE_NAME}"; then
    sudo docker stop "${SERVICE_NAME}"
  fi
  sudo docker rm "${SERVICE_NAME}"
fi

# Cek image ada atau tidak
if sudo docker images --format '{{.Repository}}:{{.Tag}}' | grep -qw "${IMAGE_REF}"; then
  sudo docker rmi "${IMAGE_REF}"
fi

sudo docker build -t "${IMAGE_REF}" .

sudo docker run -d -p 8761:8761 --name "${SERVICE_NAME}" \
  -e "SPRING_PROFILES_ACTIVE=default" \
  "${IMAGE_REF}"