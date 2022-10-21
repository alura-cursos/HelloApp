package br.com.alura.helloapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.helloapp.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier, onClickEntrar: () -> Unit, onClickCriar: () -> Unit
) {

    Column(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            Image(
                painter = painterResource(id = R.drawable.helloapp_logo_blue),
                modifier = modifier
                    .size(180.dp),
                contentDescription = "Logo aplicativo",
            )
            Text(
                text = "HelloApp", style = MaterialTheme.typography.h6
            )
        }
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .weight(2f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var usuario by remember { mutableStateOf("") }
            var senha by remember { mutableStateOf("") }

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person, contentDescription = null
                    )
                },
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text(stringResource(id = R.string.usuario)) })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock, contentDescription = null
                    )
                },
                value = senha,
                onValueChange = { senha = it },
                label = { Text(stringResource(id = R.string.senha)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(56.dp), onClick = onClickEntrar
            ) {
                Text(text = stringResource(R.string.entrar))
            }
            TextButton(onClick = onClickCriar, Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.criar_nova_conta))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPrev() {
    LoginScreen(modifier = Modifier, {}, {})
}