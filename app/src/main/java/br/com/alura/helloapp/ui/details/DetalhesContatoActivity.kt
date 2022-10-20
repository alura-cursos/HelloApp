package br.com.alura.helloapp.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.helloapp.CHAVE_CONTATO_ID
import br.com.alura.helloapp.R
import br.com.alura.helloapp.converteParaString
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.ui.home.CadastroContatoActivity
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Calendar

class DetalhesContatoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val idContato = intent.getStringExtra(CHAVE_CONTATO_ID)
        val contatoAtual =
            Contato(1, idContato.toString(), "", "", "", Calendar.getInstance().time)

        setContent {
            HelloAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    DetalhesContatoScreen(contatoAtual, onBackClick = { finish() }, onEditClick = {
                        startActivity(Intent(this, CadastroContatoActivity::class.java))
                    })
                }
            }
        }
    }
}

@Composable
fun DetalhesContatoScreen(contato: Contato, onBackClick: () -> Unit, onEditClick: () -> Unit) {
    Scaffold(
        topBar = { DetalhesContatoAppBar(onBackClick = onBackClick, onEditClick = onEditClick) },
    ) { paddingValues ->
        DetalhesContatoContent(Modifier.padding(paddingValues), contato)
    }
}

@Composable
fun DetalhesContatoAppBar(onBackClick: () -> Unit, onEditClick: () -> Unit) {
    TopAppBar(title = { }, navigationIcon = {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = Color.White,
                contentDescription = "Voltar"
            )
        }
    }, actions = {
        IconButton(
            onClick = onEditClick
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                tint = Color.White,
                contentDescription = "Editar"
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = Color.White,
                contentDescription = "Deletar"
            )
        }
    })
}

@Composable
fun DetalhesContatoContent(modifier: Modifier = Modifier, contato: Contato) {

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current).data(contato.fotoPerfil).build(),
            placeholder = painterResource(R.drawable.default_profile_picture),
            error = painterResource(R.drawable.default_profile_picture),
            contentDescription = "Foto de perfil do contato",
        )
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = contato.nome,
            style = MaterialTheme.typography.h5
        )

        Divider(thickness = 1.dp)

        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {},
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, CenterVertically),
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    text = stringResource(R.string.ligar), color = MaterialTheme.colors.primary
                )
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {},
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, CenterVertically),
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
                Text(
                    text = stringResource(R.string.mensagem), color = MaterialTheme.colors.primary
                )
            }
        }
        Divider(thickness = 1.dp)

        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {

            Text(
                modifier = Modifier.padding(bottom = 22.dp),
                text = stringResource(R.string.informações),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )

            Text(
                text = "${contato.nome} ${contato.sobreNome}", style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.nome_completo),
                color = Color.Gray,
                style = MaterialTheme.typography.body2
            )


            Text(
                text = "4002-8922", style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                text = stringResource(id = R.string.telefone),
                color = Color.Gray,
                style = MaterialTheme.typography.body2
            )

            contato.aniversario?.let {
                Text(
                    text = contato.aniversario.converteParaString(),
                    style = MaterialTheme.typography.h6
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.aniversario),
                    color = Color.Gray,
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }
}

@Preview
@Composable
fun DetalhesContatoScreenPrev() {
    DetalhesContatoScreen(Contato(
        1, "Ana", "Lura", "", "", Calendar.getInstance().time
    ), {}, {})
}