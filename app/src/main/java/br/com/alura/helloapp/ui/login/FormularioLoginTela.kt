package br.com.alura.helloapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import br.com.alura.helloapp.R
import br.com.alura.helloapp.util.preferences.PreferencesKeys
import br.com.alura.helloapp.util.preferences.dataStore
import kotlinx.coroutines.launch

@Composable
fun FormularioLoginTela(
    viewModel: FormularioLoginViewModel,
    modifier: Modifier = Modifier,
    onClickSalvar: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsState()
    val dataStore = LocalContext.current.dataStore
    val scope = rememberCoroutineScope()

    FormularioLoginTela(
        state = state,
        modifier = modifier,
        onSalvar = {
            scope.launch {
                dataStore.edit { preferences ->
                    with(PreferencesKeys) {
                        preferences[NOME] = state.nome
                        preferences[USUARIO] = state.usuario
                        preferences[SENHA] = state.senha
                        preferences[LOGADO] = true
                    }
                }
            }
            onClickSalvar()
        }
    )
}

@Composable
fun FormularioLoginTela(
    state: FormularioLoginUiState,
    modifier: Modifier = Modifier,
    onSalvar: () -> Unit = {}
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
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
            value = state.nome,
            onValueChange = state.onNomeMudou,
            label = { Text(stringResource(id = R.string.nome)) })

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
                .heightIn(56.dp),
            onClick = onSalvar
        ) {
            Text(text = stringResource(R.string.criar))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CadastroLoginScreenPreview() {
    FormularioLoginTela(state = FormularioLoginUiState())
}