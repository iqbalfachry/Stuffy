package com.example.stuffy.core.data


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class LocalResource<ResultType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        emitAll(loadFromDB().map {
            Resource.Success(it)
        })
    }

    protected abstract fun loadFromDB(): Flow<ResultType>

    fun asFlow(): Flow<Resource<ResultType>> = result
}