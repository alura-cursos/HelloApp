package br.com.alura.helloapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import br.com.alura.helloapp.R
import br.com.alura.helloapp.util.preferences.PreferencesKeys
import br.com.alura.helloapp.util.preferences.dataStore
import kotlinx.coroutines.launch

@Composable
fun LoginTela(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    onClickLogar: () -> Unit = {},
    onClickCriarLogin: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val dataStore = LocalContext.current.dataStore
    val scope = rememberCoroutineScope()

    LoginTela(
        state = state,
        modifier = modifier,
        onClickLogar = {
            scope.launch {
                dataStore.data.collect { preferences ->
                    with(PreferencesKeys) {
                        val usuario = preferences[USUARIO]
                        val senha = preferences[SENHA]

                        if (usuario == state.usuario && state.senha == senha) {
                            dataStore.edit { preferences ->
                                preferences[LOGADO] = true

                            }
                            onClickLogar()
                        } else {
                            state.onErro(true)
                        }
                    }
                }
            }

        },
        onClickCriarLogin = onClickCriarLogin
    )
}

@Composable
fun LoginTela(
    state: LoginUiState,
    modifier: Modifier = Modifier,
    onClickLogar: () -> Unit = {},
    onClickCriarLogin: () -> Unit = {},
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
                contentDescription = stringResource(R.string.logo_do_app),
            )
            Text(
                text = stringResource(id = R.string.nome_do_app),
                style = MaterialTheme.typography.h6
            )
        }
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .weight(2f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (state.exibirErro) {
                Text(
                    text = stringResource(R.string.usuario_ou_senha_incorreto),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    style = MaterialTheme.typography.subtitle1,
                )
            }


            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person, contentDescription = null
                    )
                },
                value = state.usuario,
                onValueChange = state.onUsuarioMudou,
                label = { Text(stringResource(id = R.string.usuario)) })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock, contentDescription = null
                    )
                },
                value = state.senha,
                onValueChange = state.onSenhaMudou,
                label = { Text(stringResource(id = R.string.senha)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(56.dp), onClick = onClickLogar
            ) {
                Text(text = stringResource(R.string.entrar))
            }
            TextButton(onClick = onClickCriarLogin, Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.criar_nova_conta))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPrev() {
    LoginTela(state = LoginUiState())
}