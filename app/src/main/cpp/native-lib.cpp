#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
Java_com_newstore_favqs_common_SecureKeys_baseUrl(JNIEnv *env, jobject object) {
    std::string baseUrl = "https://favqs.com/api/";
    return env->NewStringUTF(baseUrl.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_newstore_favqs_common_SecureKeys_authorizationToken(JNIEnv *env, jobject object) {
    std::string token = "Token token=\"bd3919216b0f09cbf1ceea64368a6d5b\"";
    return env->NewStringUTF(token.c_str());
}