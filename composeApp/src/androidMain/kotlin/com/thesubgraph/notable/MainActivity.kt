package com.thesubgraph.notable

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.thesubgraph.notable.theme.NotableTheme
import com.thesubgraph.notable.view.common.DestinationRouteProtocol
import com.thesubgraph.notable.view.common.Router

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotableTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotableApp(
                        applicationContext = applicationContext,
                        mainActivity = this@MainActivity
                    )
                }
            }
        }
    }
}


@Composable
fun NotableApp(applicationContext: Context, mainActivity: MainActivity) {
    val navController = rememberNavController()
    val router = Router(
        context = applicationContext,
        navController = navController,
        activity = mainActivity,
    )
    router.ComposeRouter(DestinationRouteProtocol.Destination.Home.route)
}
