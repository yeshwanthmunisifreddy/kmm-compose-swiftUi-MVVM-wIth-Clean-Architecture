package com.thesubgraph.notable.domain.usecase

import com.thesubgraph.notable.KmmSubscription
import com.thesubgraph.notable.data.common.ValueResult
import com.thesubgraph.notable.data.common.error.ErrorModel
import com.thesubgraph.notable.domain.model.Photo
import com.thesubgraph.notable.domain.repository.DocsRepository
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

class GetDocsUseCase(
    private val docsRepository: DocsRepository,
) {
    // Kotlin
    @OptIn(ExperimentalObjCRefinement::class)
    @HiddenFromObjC
    fun getKtorDocs(
        pageSize: Int = 30,
        pageNUmber: Int,
    ) = docsRepository.getKtorDocs(pageSize,pageNUmber)

    // Swift
    fun getKtorDocs(
        onEach: (ValueResult<List<Photo>>) -> Unit,
        onError: (ErrorModel?) -> Unit = {},
        pageSize: Int = 30,
        pageNUmber: Int,
    ): KmmSubscription = getKtorDocs(pageSize,pageNUmber).subscribe(onEach = onEach, onError = onError)
}
