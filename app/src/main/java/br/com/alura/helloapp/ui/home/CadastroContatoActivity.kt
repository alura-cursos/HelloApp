package br.com.alura.helloapp.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.alura.helloapp.R
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

class CadastroContatoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    CadastroScreen()
                }
            }
        }
    }
}

@Composable
fun CadastroScreen() {
    Scaffold(
        topBar = { CadastroAppBar() },
    ) { paddingValues ->
        CadastroContent(Modifier.padding(paddingValues))
    }
}

@Composable
fun CadastroContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        CarregaFotoDialog(onDismiss = {
            showDialog.value = false
        }) { urlImage ->
            Toast.makeText(context, "Url iamgem: $urlImage", Toast.LENGTH_SHORT).show()
            showDialog.value = false
        }
    }

    Column(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            AsyncImage(
                modifier = modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .clickable {
                        Toast
                            .makeText(context, "Adicionar foto", Toast.LENGTH_SHORT)
                            .show()
                        showDialog.value = true
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.default_profile_picture).build(),
                placeholder = painterResource(R.drawable.default_profile_picture),
                error = painterResource(R.drawable.default_profile_picture),
                contentDescription = "Foto de perfil do contato",
            )
            Text(
                text = "Adicionar foto", style = MaterialTheme.typography.subtitle1
            )
        }
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .weight(2f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var nome by remember { mutableStateOf("") }
            var sobrenome by remember { mutableStateOf("") }
            var telefone by remember { mutableStateOf("") }

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person, contentDescription = null
                    )
                },
                value = nome,
                onValueChange = { nome = it },
                label = { Text(stringResource(id = R.string.nome)) })

            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = sobrenome,
                onValueChange = { sobrenome = it },
                label = { Text(stringResource(id = R.string.sobrenome)) })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone, contentDescription = null
                    )
                },
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text(stringResource(id = R.string.telefone)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(Modifier.height(16.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp), onClick = { /*TODO*/ }) {
                Text(text = stringResource(R.string.salvar))
            }
        }

    }
}

@Composable
fun CadastroAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.title_activity_cadastro_contato)) },
    )
}


@Composable
fun CarregaFotoDialog(onDismiss: () -> Unit, onConfirmButton: (urlImagem: String) -> Unit) {
    var urlImagem by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }, content = {
        Column(
            Modifier
                .clip(RoundedCornerShape(5))
                .heightIn(250.dp)
                .widthIn(200.dp)
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(5, 5)),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.default_profile_picture).build(),
                placeholder = painterResource(R.drawable.default_profile_picture),
                error = painterResource(R.drawable.default_profile_picture),
                contentDescription = "Foto de perfil do contato",
            )

            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
                value = urlImagem,
                onValueChange = { urlImagem = it },
                label = { Text(stringResource(id = R.string.link_imagem)) })

            Button(
                onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.carregar))
            }

            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancelar")
                }

                TextButton(onClick = { onConfirmButton(urlImagem) }) {
                    Text(text = "Salvar")
                }
            }
        }
    })

}



@Composable
fun CarregaFotoDialogPrev() {
    CarregaFotoDialog({}, {})
}

@Preview
@Composable
fun CadastroScreenPrev() {
    CadastroScreen()
}