   LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)
   LOCAL_MODULE    := ddsLib
   LOCAL_SRC_FILES := main.cpp dds.cpp
   #Init.cpp ABsearch.cpp ABstats.cpp CalcTables.cpp DealerPar.cpp LaterTricks.cpp Moves.cpp Par.cpp PBN.cpp PlayAnalyser.cpp QuickTricks.cpp SolveBoard.cpp SolverIF.cpp Stats.cpp Timer.cpp TransTable.cpp
   LOCAL_LDLIBS    := -llog
   #LOCAL_CFLAGS  += -fopenmp
   #LOCAL_LDFLAGS += -fopenmp

   include $(BUILD_SHARED_LIBRARY)