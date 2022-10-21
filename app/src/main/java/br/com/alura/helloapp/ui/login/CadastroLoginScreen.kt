package br.com.alura.helloapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.helloapp.R
import br.com.alura.helloapp.data.Usuario

@Composable
fun CadastroLoginScreen(
    modifier: Modifier = Modifier,
    onClickCriarLogin: (usuario: Usuario) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        var nome by remember { mutableStateOf("") }
        var usuario by remember { mutableStateOf("") }
        var senha by remember { mutableStateOf("") }

        Text(
            text = stringResource(id = R.string.criar_nova_conta),
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.h6
        )

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Face, contentDescription = null
                )
            },
            value = nome,
            onValueChange = { nome = it },
            label = { Text(stringResource(id = R.string.nome)) })

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

        Button(modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
            onClick = {
                val novoUsuario = Usuario(
                    nome = nome,
                    usuario = usuario,
                    senha = senha
                )
                onClickCriarLogin(novoUsuario)
            }) {
            Text(text = stringResource(R.string.criar))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CadastroLoginScreenPreview() {
    CadastroLoginScreen {}
}