FROM nginx:alpine

COPY default.conf /etc/nginx/conf.d/default.conf
COPY target/index.html /usr/share/nginx/static/index.html
COPY src/main/resources/js /usr/share/nginx/static/js
COPY src/main/resources/css /usr/share/nginx/static/css
COPY target/images /usr/share/nginx/static/images