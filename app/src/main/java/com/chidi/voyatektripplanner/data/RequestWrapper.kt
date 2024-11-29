package com.chidi.voyatektripplanner.data

import android.util.Log
import com.chidi.voyatektripplanner.data.models.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RequestWrapper<T>(
    private val call: Call<T>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun execute(): Flow<Response<T>> = flow {
        emit(Response.loading())
        try {
            Log.d("RequestWrapper", "Executing... ${call.request().url()}")
            val response = call.execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Response.success(it))
                    return@flow
                }
            }
            emit(Response.error(response.message()))
        } catch (e: IOException) {
            Log.e("RequestWrapper", e.message ?: e.toString())
            emit(Response.error(e.message ?: e.toString()))
        } catch (e: HttpException) {
            Log.e("RequestWrapper", e.message ?: e.toString())
            emit(Response.error(e.message ?: e.toString()))
        }
    }.flowOn(dispatcher)
}

class RequestAdapter<T>(private val responseType: Type) : CallAdapter<T, Any> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Any = RequestWrapper(call)
}

class RequestAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = try {
        val enclosingType = (returnType as ParameterizedType)
        if (enclosingType.rawType != RequestWrapper::class.java)
            null
        else {
            val type = enclosingType.actualTypeArguments[0]
            RequestAdapter<Any>(type)
        }
    } catch (ex: ClassCastException) {
        null
    }

    companion object {
        @JvmStatic
        fun create() = RequestAdapterFactory()
    }

}