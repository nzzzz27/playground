FROM nginx

COPY /nginx.conf /usr/local/nginx/conf/nginx.conf
COPY ./html /usr/share/nginx/html

EXPOSE 8000 80

CMD ["nginx","-g","daemon off;"]
