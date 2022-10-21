package br.com.alura.helloapp.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.alura.helloapp.R

@Composable
fun AppBarBase() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
    )
}