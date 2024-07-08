package com.thesubgraph.notable.data

import com.thesubgraph.notable.data.common.apiCall
import com.thesubgraph.notable.data.common.serialization.PhotoDto
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class ApiService(
    private val client: HttpClient,
) {
    companion object {
        private const val END_POINT =
            "https://api.unsplash.com/photos"
    }



}