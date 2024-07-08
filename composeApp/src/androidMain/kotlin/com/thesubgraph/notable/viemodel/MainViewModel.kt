package com.thesubgraph.notable.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thesubgraph.notable.data.common.ValueResult
import com.thesubgraph.notable.domain.model.Photo
import com.thesubgraph.notable.domain.usecase.GetDocsUseCase
import com.thesubgraph.notable.view.common.PaginationState
import com.thesubgraph.notable.view.common.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: GetDocsUseCase,
) : ViewModel() {
    private val _viewState: MutableStateFlow<ViewState<Unit>> =
        MutableStateFlow(ViewState.Initial)
    val viewState by lazy { _viewState.asStateFlow() }
    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(listOf())
    val photos by lazy { _photos.asStateFlow() }
    private var pagination = PaginationState()

    init {
        getPhotos()
    }

    fun getPhotos(refresh: Boolean = false) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading
            if (refresh) {
                pagination = PaginationState()
                _photos.value = listOf()
            }
            if (pagination.reachedEnd) {
                return@launch
            }
            val currentPage = pagination.page
            val currentSize = _photos.value.size
            useCase
                .getKtorDocs(pageNUmber = currentPage, pageSize = pagination.pageSize)
                .flowOn(Dispatchers.IO)
                .collectLatest { result ->
                    when (result) {
                        is ValueResult.Success -> {
                            val currentPhotos = _photos.value.take(currentSize)
                            val duplicatePhotos =
                                result.data.filter { photo -> currentPhotos.any { it.id == photo.id } }
                            val filteredPhotos =
                                result.data.filter { photo -> !duplicatePhotos.any { it.id == photo.id } }
                            val updatedPhotos =
                                currentPhotos.map { oldPhoto ->
                                    duplicatePhotos.firstOrNull { it.id == oldPhoto.id }
                                        ?: oldPhoto
                                }
                            _photos.value = updatedPhotos + filteredPhotos
                            _viewState.value = ViewState.Success(Unit)
                            pagination =
                                if (result.data.isEmpty()) {
                                    pagination.copy(reachedEnd = true)
                                } else {
                                    pagination.copy(page = currentPage + 1)
                                }
                        }

                        is ValueResult.Failure -> {
                            _viewState.value = ViewState.Error(result.error)
                        }
                    }
                }
        }
    }
}
