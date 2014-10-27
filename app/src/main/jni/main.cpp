#include "com_iq007_bridge_doubledummysolver_DoubleDummySolver_BridgeWorker.h"

#include "dll.h"
#include <iostream>
#include <string>
#include <cstring>
#include <android/log.h>

#define  LOG_TAG    "JNI"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)


JNIEXPORT jint JNICALL Java_com_iq007_bridge_doubledummysolver_DoubleDummySolver_00024BridgeWorker_SolveBoard
  (JNIEnv * env, jobject obj1, jobject obj2)
  {
        return 0;
  }

JNIEXPORT jobjectArray JNICALL Java_com_iq007_bridge_doubledummysolver_DoubleDummySolver_00024BridgeWorker_CalcDDtablePBN
  (JNIEnv * env, jobject obj1, jstring jDealPBNString){

    jobjectArray result;
    jclass objectClass = env->FindClass("java/lang/Object");
    result = env->NewObjectArray(5, objectClass, 0);

    const char *dealPBNString = env->GetStringUTFChars(jDealPBNString, JNI_FALSE);
    ddTableDealPBN tableDealPBN;
    strlcpy(tableDealPBN.cards, dealPBNString, sizeof(tableDealPBN.cards));

    LOGI("tableDealPBN: %s", tableDealPBN.cards);

    ddTableResults tableResults;

    InitStart(0,0);

    int returnResult = CalcDDtablePBN(tableDealPBN, &tableResults);

    LOGI("CalcDDtablePBN returned: %d", returnResult);

    for(int i=0;i<5;i++){
        jintArray strainsArray1;
        strainsArray1 = env->NewIntArray(4);
        env->SetIntArrayRegion(strainsArray1, 0, 4, tableResults.resTable[i]);
        env->SetObjectArrayElement(result, i, strainsArray1);
        for (int j=0;j<4;j++){
          LOGI("Result[%d][%d]: %d", i,j, tableResults.resTable[i][j]);
      }
    }

    env->ReleaseStringUTFChars(jDealPBNString, dealPBNString);

    return result;

}

JNIEXPORT jobjectArray JNICALL Java_com_iq007_bridge_doubledummysolver_DoubleDummySolver_00024BridgeWorker_CalcParPBN
  (JNIEnv * env, jobject obj1, jstring jDealPBNString, jint jVulnerability)
  {
        jobjectArray result;
        jclass objectClass = env->FindClass("java/lang/Object");
        result = env->NewObjectArray(4, objectClass, 0);

        int vulnerability = (int) jVulnerability;

        const char *dealPBNString = env->GetStringUTFChars(jDealPBNString, JNI_FALSE);
        ddTableDealPBN tableDealPBN;
        strlcpy(tableDealPBN.cards, dealPBNString, sizeof(tableDealPBN.cards));

        LOGI("tableDealPBN: %s", tableDealPBN.cards);

        ddTableResults  tableResults;
        parResults      tableParResults;

        InitStart(0,0);

        int returnResultCalcDDtable = CalcDDtablePBN(tableDealPBN, &tableResults);
        LOGI("CalcDDtablePBN returned: %d", returnResultCalcDDtable);

        int returnResultCalcPar = Par(&tableResults, &tableParResults, vulnerability);
        LOGI("Par returned: %d", returnResultCalcPar);

        jchar jcharBuffer1[2][16];// = new jcharArray[2][16];
        for(int i=0;i<2;i++){
            for (int j = 0; j<16; j++) {
                jcharBuffer1[i][j] = (jchar)tableParResults.parScore[i][j];
            }
        }

        jchar jcharBuffer2[2][128];// = new jcharArray[128];
        for(int i=0;i<2;i++){
            for (int j = 0; j<128; j++) {
                jcharBuffer2[i][j] = (jchar)tableParResults.parContractsString[i][j];
            }
        }

        for(int i=0;i<2;i++){
            jcharArray strainsArray1;
            strainsArray1 = env->NewCharArray(16);
            env->SetCharArrayRegion(strainsArray1, 0, 16, jcharBuffer1[i]);
            env->SetObjectArrayElement(result, i, strainsArray1);
            for (int j=0;j<16;j++){
              LOGI("Result[%d][%d]: %d", i,j, tableParResults.parScore[i]);
          }
        }
        for(int i=2;i<4;i++){
            jcharArray strainsArray2;
            strainsArray2 = env->NewCharArray(128);
            env->SetCharArrayRegion(strainsArray2, 0, 128, jcharBuffer2[i-2]);
            env->SetObjectArrayElement(result, i, strainsArray2);
            for (int j=0;j<128;j++){
              LOGI("ResultPar[%d][%d]: %d", i-2,j, tableParResults.parContractsString[i-2][j]);
          }
        }

        env->ReleaseStringUTFChars(jDealPBNString, dealPBNString);

        return result;
  }
