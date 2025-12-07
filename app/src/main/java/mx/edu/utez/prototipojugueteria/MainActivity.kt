package mx.edu.utez.prototipojugueteria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.utez.prototipojugueteria.ui.Navigation
import mx.edu.utez.prototipojugueteria.ui.theme.PrototipoJugueteriaTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrototipoJugueteriaTheme {
                Navigation()
            }
        }
    }
}