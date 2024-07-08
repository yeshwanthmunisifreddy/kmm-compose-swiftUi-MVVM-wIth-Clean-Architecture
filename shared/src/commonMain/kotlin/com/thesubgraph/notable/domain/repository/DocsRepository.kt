package com.thesubgraph.notable.domain.repository

import com.thesubgraph.notable.KmmFlow
import com.thesubgraph.notable.data.common.ValueResult
import com.thesubgraph.notable.domain.model.Photo

interface DocsRepository {
     fun getKtorDocs(pageSize: Int, pageNUmber: Int): KmmFlow<ValueResult<List<Photo>>>
}