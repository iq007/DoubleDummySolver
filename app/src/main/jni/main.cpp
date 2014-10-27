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
