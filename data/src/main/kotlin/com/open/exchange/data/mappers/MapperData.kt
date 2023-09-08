package com.open.exchange.data.mappers

interface MapperData<E, D> {
    fun mapFromEntity(type: E): D
}