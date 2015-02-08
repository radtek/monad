#!/bin/bash

sudo apt-get update

if [ $CROSSPREFIX ]; then
  sudo apt-get install -yy mingw-w64 g++-mingw-w64 swig \
  gcc-mingw-w64 g++-mingw-w64-x86-64 g++-mingw-w64-i686 gcc-mingw-w64-i686 gcc-mingw-w64-x86-64
  cd monad-c
  LIB_DIR=$(pwd)/_tmp
  mkdir -p $LIB_DIR
  mkdir -p ${LIB_DIR}/mingw
  mkdir -p ${LIB_DIR}/${ARCH}

  #compile leveldb
  cd $LIB_DIR
  git clone --depth=1 --branch=bitcoin-fork https://github.com/bitcoin/leveldb.git
  cd leveldb
  CC=/usr/bin/${CROSSPREFIX}gcc CXX=${CROSSPREFIX}g++ TARGET_OS=OS_WINDOWS_CROSSCOMPILE make clean all
  cp libleveldb.a ${LIB_DIR}/${ARCH}
  cp -rp include/leveldb ${LIB_DIR}/mingw/

  #compile 
  cd ${LIB_DIR}/..
  mkdir build-$ARCH
  cd build-$ARCH
  LDFLAGS="-L/${LIB_DIR}/${ARCH}" CXXFLAGS="-I${LIB_DIR}/mingw"   \
    cmake -DCMAKE_TOOLCHAIN_FILE=${LIB_DIR}/../../support/docker/compile-mingw/Toolchain-cross-mingw32-linux.cmake \
    -DHOST=$HOST  -DCMAKE_BUILD_TYPE=Release -DARCH=$ARCH \
    -DSWIG_DIR=/usr/share/swig2.0 \
    -DJAVA_AWT_LIBRARY=/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/amd64/libjawt.so \
    -DJAVA_INCLUDE_PATH=/usr/lib/jvm/java-7-openjdk-amd64/include \
    -DJAVA_AWT_INCLUDE_PATH=/usr/lib/jvm/java-7-openjdk-amd64/include \
    -DJAVA_INCLUDE_PATH2=/usr/lib/jvm/java-7-openjdk-amd64/include/linux \
    -DJAVA_JVM_LIBRARY=/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/amd64/libjava.so  ..
  make
else
  sudo apt-get -yy install libleveldb-dev swig libsnappy-dev
  cd monad-c && mkdir build && cd build && cmake -DCMAKE_BUILD_TYPE=Release .. && make
fi
