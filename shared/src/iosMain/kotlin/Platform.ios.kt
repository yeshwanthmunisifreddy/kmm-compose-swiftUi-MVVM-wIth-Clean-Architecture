import com.thesubgraph.notable.di.getDependency
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(BetaInteropApi::class)
fun <T> Koin.getDependency(objCClass: ObjCClass): T? =
    getOriginalKotlinClass(objCClass)?.let {
        getDependency(it)
    }
