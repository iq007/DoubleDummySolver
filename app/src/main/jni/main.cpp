
#include "com_iq007_bridge_doubledummysolver_DoubleDummySolver_PlaceholderFragment.h"
#include "dll.h"
#include <iostream>
#include <string.h>
#include <android/log.h>

#define  LOG_TAG    "JNI"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

 int toInt(std::string bitString, int sLength){

        int tempInt;
        int num=0;
        for(int i=0; i<sLength; i++){
            tempInt=bitString[i]-'0';
            num=num+tempInt * pow((double)2,(sLength-1-i));
        }

        return num;
    }

JNIEXPORT jint JNICALL Java_com_iq007_bridge_doubledummysolver_DoubleDummySolver_00024PlaceholderFragment_SolveBoard
  (JNIEnv * env, jobject obj, jobject ibj2)
  {

        ddTableDeal tableDeal = {};
        /* 1st index hand (0-3), 2nd index suit (0-3), values as bitstring of ranks bit
        0=0, bit 1=0, bit 2=rank 2, ………. bit 14=rank 14, bit 15=0 for cards remaining after already played cards (cards already played to the current trick are not included in this bitstring).
         The decimal value for a card then range between 4 (=rank 2) and 16384  (Ace=rank 14). */


        ddTableResults tableResults = {};
        parResults pResults = {};

        LOGI("0/0:%d",toInt("0011100000000000",16));
        tableDeal.cards[0][0] = toInt("0111100000000000",16);
        tableDeal.cards[0][1] = toInt("0011100000000000",16);
        tableDeal.cards[0][2] = toInt("0011100000000000",16);
        tableDeal.cards[0][3] = toInt("0011100000000000",16);
        tableDeal.cards[1][0] = toInt("0000000000111100",16);
        tableDeal.cards[1][1] = toInt("0100000000001100",16);
        tableDeal.cards[1][2] = toInt("0100000000001100",16);
        tableDeal.cards[1][3] = toInt("0100000000001100",16);
        tableDeal.cards[2][0] = toInt("0000011110000000",16);
        tableDeal.cards[2][1] = toInt("0000011100000000",16);
        tableDeal.cards[2][2] = toInt("0000011100000000",16);
        tableDeal.cards[2][3] = toInt("0000011100000000",16);
        tableDeal.cards[3][0] = toInt("0000000001000000",16);
        tableDeal.cards[3][1] = toInt("0000000011110000",16);
        tableDeal.cards[3][2] = toInt("0000000011110000",16);
        tableDeal.cards[3][3] = toInt("0000000011110000",16);

        //InitStart(1,1);

        int returnResult = CalcDDtable(tableDeal, &tableResults);

        for(int i=0;i<5;i++)
            for (int j=0;j<4;j++){
                LOGI("Result[%d][%d]: %d", i,j, tableResults.resTable[i][j]);
            }

        return returnResult;
  }


