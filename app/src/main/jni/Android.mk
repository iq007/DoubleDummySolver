   LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)
   LOCAL_MODULE    := ddsLib
   LOCAL_SRC_FILES := main.cpp dds.cpp
   LOCAL_LDLIBS    := -llog


   include $(BUILD_SHARED_LIBRARY)