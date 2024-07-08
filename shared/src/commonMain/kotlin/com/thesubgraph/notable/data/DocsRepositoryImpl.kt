package com.thesubgraph.notable.data

import com.thesubgraph.notable.KmmFlow
import com.thesubgraph.notable.asKmmFlow
import com.thesubgraph.notable.data.common.ValueResult
import com.thesubgraph.notable.data.common.apiCall
import com.thesubgraph.notable.data.common.serialization.PhotoDto
import com.thesubgraph.notable.domain.model.Photo
import com.thesubgraph.notable.domain.repository.DocsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import kotlinx.coroutines.flow.flow

class DocsRepositoryImpl(
    private val client: HttpClient,
) : DocsRepository {
    override fun getKtorDocs(pageSize: Int, pageNUmber: Int): KmmFlow<ValueResult<List<Photo>>> =
        flow {
            val response =
                client.apiCall<List<PhotoDto>, List<Photo>>(
                    mapper = { photos -> photos.map { it.mapToDomain() } },
                ) {
                    url("https://api.unsplash.com/photos?page = $pageNUmber&per_page = $pageSize")
                    method = HttpMethod.Get
                    header("Authorization", "Client-ID uxQ_VELbYVLsW95L9HsRfHYvScJ2pS0bjRH4FuW-5yo")
                }
            println("response: $response")
            emit(response)
        }.asKmmFlow()
}
