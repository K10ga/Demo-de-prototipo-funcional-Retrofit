package mx.edu.utez.prototipojugueteria.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.prototipojugueteria.viewmodel.LoginViewModel


@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp,
            Alignment.CenterVertically)
    ){

    }
}
