FROM nginx
ADD dist/ /usr/share/nginx/html/
LABEL maintainer=hongtao
VOLUME [ "/var/log/nginx/" ]
RUN rm /etc/nginx/conf.d/default.conf
ADD /conf/default.conf /etc/nginx/conf.d/
ADD dist /usr/share/nginx/html/
