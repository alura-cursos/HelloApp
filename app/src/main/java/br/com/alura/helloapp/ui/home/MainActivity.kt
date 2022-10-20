package br.com.alura.helloapp.ui.home

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
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
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.*
import br.com.alura.helloapp.R
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.preferences.PreferencesKeys
import br.com.alura.helloapp.preferences.dataStore
import br.com.alura.helloapp.ui.details.DetalhesContatoActivity
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Adicionar campo de data de niver, para ter exemplo de converter com Room

        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                val nome = preferences[PreferencesKeys.NOME_USUARIO].toString()
                Toast.makeText(this@MainActivity, "O nome do usuário é $nome", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        setContent {
            HelloAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(abreTelaDetalhes = { contato ->

                        val intent = Intent(this, DetalhesContatoActivity::class.java)
                        intent.putExtra(CHAVE_CONTATO_ID, contato.nome + " Tem que ser o id aqui")
                        startActivity(intent)
                    }) {
                        val intent = Intent(this, CadastroContatoActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(abreTelaDetalhes: (Contato) -> Unit, abreTelaCadastro: () -> Unit) {
    Scaffold(
        topBar = { HomeAppBar() },
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
            items(contatosExemplo) { contato ->
                ContatoItem(contato) {
                    abreTelaDetalhes(contato)
                }
            }
        }
    }
}

@Composable
fun HomeAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    tint = Color.White,
                    contentDescription = "Configurações do app"
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
    HomeScreen({}, {})
}

@Preview
@Composable
fun ContatoItemPreview() {
    ContatoItem(contatosExemplo.first()) {}
}

private val contatosExemplo = listOf(
    Contato(
        id = "",
        nome = "Ana",
        sobreNome = "Clara",
        telefone = "123",
        fotoPerfil = "https://images.pexels.com/photos/3922294/pexels-photo-3922294.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",

        ),
    Contato(
        id = "",
        nome = "Bill",
        sobreNome = "Lima",
        telefone = "321",
        fotoPerfil = "https://images.pexels.com/photos/1415882/pexels-photo-1415882.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
    ),
    Contato(
        id = "",
        nome = "Odes",
        sobreNome = "Conhecido",
        telefone = "321",
        fotoPerfil = "urlTeste2"
    )
)