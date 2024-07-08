package com.thesubgraph.notable.data.common

interface Mapper

interface ResponseDomainMapper<DomainModel> : Mapper {
    fun mapToDomain(): DomainModel?
}
