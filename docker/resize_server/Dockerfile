FROM alpine:3.1

RUN echo "now building..."

RUN apk --update add \
    wget \
    tar \
    git \
    gzip \
    make \
    gcc \
    imagemagick-dev \
    build-base \
    perl-dev \
    g++ \
    pcre-dev \
    zlib-dev

WORKDIR work

RUN wget https://nginx.org/download/nginx-1.20.1.tar.gz \
    && tar -xzf nginx-1.20.1.tar.gz

RUN git clone https://github.com/cubicdaiya/ngx_small_light.git \
    && cd ngx_small_light \
    && ./setup

RUN adduser -D -g 'nginx' nginx

RUN cd nginx-1.20.1 \
    && ./configure \
      --with-debug \
      --prefix=/usr/local/nginx/sbin \
      --conf-path=/etc/nginx/nginx.conf \
      --sbin-path=/usr/local/nginx/sbin \
      --user=nginx \
      --group=nginx \
      --add-module=../ngx_small_light \
    && make \
    && make install

COPY ./templates/nginx.conf /etc/nginx/nginx.conf
COPY ./html /usr/share/nginx/html

EXPOSE 8000 80

CMD ["/usr/local/nginx/sbin/nginx", "-g", "daemon off;"]
