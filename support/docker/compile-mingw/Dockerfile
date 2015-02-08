FROM ubuntu:trusty
MAINTAINER Jun Tsai <jcai@ganshane.com>

ENV TEMPDIR   /tmp

RUN sed -i 's/archive.ubuntu.com/mirrors.sohu.com/g' /etc/apt/sources.list && \
    apt-get -qq update && \
    apt-get install -qqy mingw-w64 g++-mingw-w64 git-core make wget cmake swig openjdk-7-jdk

#ssh_config
RUN echo "StrictHostKeyChecking no" >> /etc/ssh/ssh_config
RUN echo "UserKnownHostsFile /dev/null" >> /etc/ssh/ssh_config

WORKDIR /build

ADD Toolchain-cross-mingw32-linux.cmake /build/Toolchain-cross-mingw32-linux.cmake
RUN git clone --depth=1 --branch=bitcoin-fork https://github.com/bitcoin/leveldb.git

RUN mkdir /build/mingw
RUN mkdir /build/w32
RUN mkdir /build/w64


#build =============> 64bit
ENV HOST x86_64-w64-mingw32
ENV ARCH w64
ENV CROSSPREFIX x86_64-w64-mingw32-

#compile leveldb
RUN { \
  cd /build/leveldb \
  && CC=/usr/bin/${CROSSPREFIX}gcc CXX=${CROSSPREFIX}g++ TARGET_OS=OS_WINDOWS_CROSSCOMPILE make clean all \
  && cp libleveldb.a /build/${ARCH} \
  && cp -rp include/leveldb /build/mingw/ ;\
}

#build =============> 32bit
ENV HOST i686-w64-mingw32
ENV ARCH w32
ENV CROSSPREFIX i686-w64-mingw32-

#compile leveldb
RUN { \
  cd /build/leveldb \
  && CC=/usr/bin/${CROSSPREFIX}gcc CXX=${CROSSPREFIX}g++ TARGET_OS=OS_WINDOWS_CROSSCOMPILE make clean all \
  && cp libleveldb.a /build/${ARCH} \
  && cp -rp include/leveldb /build/mingw/ ;\
}

#clean some file
RUN { \
 rm -rf /build/leveldb* ;\
}

WORKDIR /build/monad

ADD compile.sh /compile.sh