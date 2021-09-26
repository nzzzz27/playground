FROM alpine:3.1

RUN apk --update add \
    wget \
    tar \
    git \
    make \
    gcc \
    build-base \
    perl-dev \
    g++ \
    zlib-dev \
    imagemagick-dev \
    pcre-dev

WORKDIR work

# NGINXのソースファイルをダウンロードする
RUN wget https://nginx.org/download/nginx-1.20.1.tar.gz \
    && tar -xzf nginx-1.20.1.tar.gz \
    && rm -r nginx-1.20.1.tar.gz

# ngx_small_lightをclone、ビルドする
RUN git clone https://github.com/cubicdaiya/ngx_small_light.git \
    && cd ngx_small_light \
    && ./setup

# NGINXを定義、コンパイル、インストールする
RUN cd nginx-1.20.1 \
    && ./configure \
      --with-debug \
      --prefix=/usr/local/nginx \
      --conf-path=/usr/local/nginx/conf/nginx.conf \
      --sbin-path=/sbin/nginx \
      --add-module=../ngx_small_light \
    && make \
    && make install

COPY ./nginx.conf /usr/local/nginx/conf/nginx.conf
COPY ./html /usr/share/nginx/html

EXPOSE 8000 80

CMD ["/sbin/nginx", "-g", "daemon off;"]
