FROM node:16.5.0
LABEL maintainer="Gabriel Gonoring Borges"
COPY ./best-developers-frontend /opt
WORKDIR /opt
RUN npm install
ENTRYPOINT npm start