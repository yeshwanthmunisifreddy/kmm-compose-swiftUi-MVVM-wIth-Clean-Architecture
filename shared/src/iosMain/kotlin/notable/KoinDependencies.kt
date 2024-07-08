package notable

import com.thesubgraph.notable.domain.repository.DocsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinDependencies : KoinComponent {
    val docsRepository: DocsRepository by inject()
}