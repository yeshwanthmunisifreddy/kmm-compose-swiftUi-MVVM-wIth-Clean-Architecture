package com.thesubgraph.notable

import com.thesubgraph.notable.data.common.error.ErrorModel
import com.thesubgraph.notable.data.common.error.toErrorDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

fun interface KmmSubscription {
    fun unsubscribe()
}

class KmmFlow<T>(private val source: Flow<T>) : Flow<T> by source {
    fun subscribe(onEach: (T) -> Unit, onError: (ErrorModel?) -> Unit = {},): KmmSubscription {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        source
            .onEach { value ->
                onEach(value)
            }
            .catch {error ->
                onError(error.message?.toErrorDomain())
            }
            .onCompletion {}
            .launchIn(scope)
        return KmmSubscription { scope.cancel() }
    }
}

fun <T> Flow<T>.asKmmFlow(): KmmFlow<T> = KmmFlow(this)