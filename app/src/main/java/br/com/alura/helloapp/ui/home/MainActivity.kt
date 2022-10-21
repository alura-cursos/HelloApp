package br.com.alura.helloapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.*
import br.com.alura.helloapp.R
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.util.preferences.PreferencesKeys
import br.com.alura.helloapp.util.preferences.dataStore
import br.com.alura.helloapp.ui.form.DetalhesContatoActivity
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.datastore.preferences.core.edit
import br.com.alura.helloapp.ui.components.OnResumeCicloVidaAtual
import br.com.alura.helloapp.ui.form.CadastroContatoActivity
import br.com.alura.helloapp.ui.login.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = HelloAppDatabase.getDatabase(this)
        val contatoDao = database.contatoDao()

        lifecycleScope.launch {
            val contatosBanco = contatoDao.buscaTodos()
            Log.i("contatosBanco", "onCreate: ${contatosBanco.toString()} ")
        }

        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                val nome = preferences[PreferencesKeys.NOME_USUARIO].toString()
                Toast.makeText(this@MainActivity, "Olá $nome", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        setContent {
            HelloAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    var contatosBuscados by remember {
                        mutableStateOf(emptyList<Contato>())
                    }

                    val coroutineScope = rememberCoroutineScope()
                    OnResumeCicloVidaAtual(LocalLifecycleOwner.current) {
                        coroutineScope.launch {
                            contatoDao.buscaTodos().collect() {
                                contatosBuscados = it
                            }
                        }
                    }

                    HomeScreen(
                        contatosBuscados = contatosBuscados,
                        onClickDesloga = {
                            lifecycleScope.launch {
                                dataStore.edit {
                                    it.remove(PreferencesKeys.USUARIO_LOGADO)
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            LoginActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            }
                        },
                        abreTelaDetalhes = { contato ->
                            val intent = Intent(this, DetalhesContatoActivity::class.java)
                            intent.putExtra(CHAVE_CONTATO_ID, contato.id)
                            startActivity(intent)
                        }
                    ) {
                        val intent = Intent(this, CadastroContatoActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    contatosBuscados: List<Contato>,
    onClickDesloga: () -> Unit,
    abreTelaDetalhes: (Contato) -> Unit,
    abreTelaCadastro: () -> Unit
) {
    Scaffold(
        topBar = { HomeAppBar(onClickDesloga = onClickDesloga) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { abreTelaCadastro() },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar novo contato"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(contatosBuscados) { contato ->
                ContatoItem(contato) {
                    abreTelaDetalhes(contato)
                }
            }
        }
    }
}

@Composable
fun HomeAppBar(onClickDesloga: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(
                onClick = onClickDesloga
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    tint = Color.White,
                    contentDescription = "Deslogar"
                )
            }
        }
    )
}

@Composable
fun ContatoItem(contato: Contato, modifier: Modifier = Modifier, onClick: (Contato) -> Unit) {
    Card(
        modifier.clickable { onClick(contato) },
        backgroundColor = Color.White
    ) {
        Row(
            Modifier.padding(16.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(contato.fotoPerfil).build(),
                placeholder = painterResource(R.drawable.default_profile_picture),
                error = painterResource(R.drawable.default_profile_picture),
                contentDescription = "Foto de pefil do contato",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(48.dp)
                    .clip(CircleShape)

            )

            Column(
                modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = contato.nome,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = contato.sobreNome
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(contatosExemplo, {}, {}, {})
}

@Preview
@Composable
fun ContatoItemPreview() {
    ContatoItem(contatosExemplo.first()) {}
}

private var contatosExemplo: List<Contato> = listOf(
    Contato(
        nome = "Ana",
        sobreNome = "Clara",
        telefone = "123",
        fotoPerfil = "https://images.pexels.com/photos/3922294/pexels-photo-3922294.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",

        ),
    Contato(

        nome = "Bill",
        sobreNome = "Lima",
        telefone = "321",
        fotoPerfil = "https://images.pexels.com/photos/1415882/pexels-photo-1415882.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
        aniversario = Calendar.getInstance().time
    ),
    Contato(
        nome = "Odes",
        sobreNome = "Conhecido",
        telefone = "321",
        fotoPerfil = "urlTeste2"
    )
)