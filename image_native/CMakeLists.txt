cmake_minimum_required(VERSION 3.4.1)

##Recommended configuration
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fno-rtti -fexceptions -Wall")

add_library(image-c
        SHARED
        src/main/cpp/image-c.cpp)

add_library(png SHARED IMPORTED)

set_target_properties(png
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libpng.so)

add_library(zip SHARED IMPORTED)

set_target_properties(zip
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libz.so)


add_library(jpeg SHARED IMPORTED)

set_target_properties(jpeg
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libjpeg.so)

add_library(jpeg-turbo SHARED IMPORTED)

set_target_properties(jpeg-turbo
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libturbojpeg.so
        )

#头文件
include_directories(${CMAKE_SOURCE_DIR}/../shared_libs/png/include)
include_directories(${CMAKE_SOURCE_DIR}/../shared_libs/jpeg/include)
include_directories(${CMAKE_SOURCE_DIR}/../shared_libs/jpeg-turbo/include)
include_directories(${CMAKE_SOURCE_DIR}/../shared_libs/zlib/include)

target_link_libraries(image-c
        png
        jpeg
        android
        zip
        log
        jpeg-turbo
        )


