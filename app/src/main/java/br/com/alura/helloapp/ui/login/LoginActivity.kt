package br.com.alura.helloapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.R
import br.com.alura.helloapp.data.Usuario
import br.com.alura.helloapp.preferences.PreferencesKeys.NOME_USUARIO
import br.com.alura.helloapp.preferences.PreferencesKeys.USUARIO_LOGADO
import br.com.alura.helloapp.preferences.dataStore
import br.com.alura.helloapp.ui.home.MainActivity
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Esse código faz com que a tela apareça alguns instantes e depois mude para a MainActivity
        // Temos duas soluções: A que será gravada: Mover para a main e redirecionar pra cá se necessario.
        // Ou: Implementar SplashScreen nas atividades
        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                if (preferences[USUARIO_LOGADO] == true) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }

        setContent {
            HelloAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    HomeLogin(onClickEntrar = {
                        startActivity(Intent(this, MainActivity::class.java))
                        this.finish()
                    }, onClickCriarLogin = { usuario ->
                        lifecycleScope.launch {
                            dataStore.edit { preferences ->
                                preferences[NOME_USUARIO] = usuario.nome
                                preferences[USUARIO_LOGADO] = true
                            }
                        }
                    })

                }
            }
        }
    }
}

@Composable
fun HomeLogin(onClickEntrar: () -> Unit, onClickCriarLogin: (usuario: Usuario) -> Unit) {
    var screenState: AppsScreens by remember {
        mutableStateOf(AppsScreens.Login)
    }

    when (screenState) {
        AppsScreens.Login -> LoginScreen(onClickEntrar = onClickEntrar) {
            screenState = AppsScreens.Cadastro
        }
        AppsScreens.Cadastro -> CadastroLoginScreen() {
            onClickCriarLogin(it)
            screenState = AppsScreens.Login
        }
    }

}

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
                painter = painterResource(id = R.drawable.ic_launcher_background),
                modifier = modifier
                    .size(180.dp)
                    .clip(CircleShape),
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

@Composable
fun CadastroLoginScreen(
    modifier: Modifier = Modifier,
    onClickCriarLogin: (usuario: Usuario) -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        var nome by remember { mutableStateOf("") }
        var usuario by remember { mutableStateOf("") }
        var senha by remember { mutableStateOf("") }

        Text(
            text = "Criar nova conta",
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
fun LoginPrev() {
    LoginScreen(modifier = Modifier, {}, {})
}

@Preview(showBackground = true)
@Composable
fun CadastroLoginScreenPrev() {
    CadastroLoginScreen {}
}

sealed class AppsScreens {
    object Login : AppsScreens()
    object Cadastro : AppsScreens()
}