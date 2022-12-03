package com.orels.samples.book_notes.data.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer


class LogInterceptor : Interceptor {

    private val tag = "LogInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val buffer = Buffer()
        request.body?.writeTo(buffer)
        Log.v(
            tag,
            "HTTP Request: ${response.request.url}," +
                    " method: ${response.request.method}," +
                    " status: ${response.code}," +
                    " request body: ${buffer.readUtf8()}"
        )
        try {
            if (!response.isSuccessful) {
                Log.e(
                    tag,
                    "\n${response.request.url}, method: ${
                        response.request.method
                    }, Failed with error: ${response.message}\n",
//                    attributes = mapOf("response" to response.peekBody(2048).string())
                )
            } else {
                Log.v(tag, "\nResponse: ${response.peekBody(2048).string()}")
            }
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }
        return response
    }
}