@file:Suppress("NAME_SHADOWING")

package br.com.alura.helloapp.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import br.com.alura.helloapp.CHAVE_CONTATO_ID
import br.com.alura.helloapp.R
import br.com.alura.helloapp.converteParaDate
import br.com.alura.helloapp.converteParaString
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.database.HelloAppDatabase
import br.com.alura.helloapp.ui.componets.CarregaFotoDialog
import br.com.alura.helloapp.ui.componets.DataPickerDialogTest
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

class CadastroContatoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = HelloAppDatabase.getDatabase(this)
        val contatoDao = database.contatoDao()

        val intent = intent
        val idContato = intent.getLongExtra(CHAVE_CONTATO_ID, 0L)

        val tituloAppBar =
            if (idContato == 0L) getString(R.string.title_activity_cadastro_contato) else getString(
                R.string.title_activity_editar_contato
            )
        setContent {
            HelloAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    // Checar com o Alex o que ele acha disso depois, "Contato" tem todos campos com valor padrão
                    var contatoCarregado by remember {
                        mutableStateOf(Contato())
                    }

                    val coroutineScope = rememberCoroutineScope()

                    LaunchedEffect(coroutineScope) {
                        contatoDao.buscaPorId(idContato).collect {
                            contatoCarregado = it

                            setContent {
                                CadastroScreen(
                                    contatoCarregado,
                                    tituloAppBar,
                                    onClickSalvar = { contato ->
                                        lifecycleScope.launch {

                                            contatoDao.insere(contato)


                                        }
                                        finish()
                                    })
                            }


                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CadastroScreen(
    contatoCarregado: Contato,
    tituloAppBar: String,
    onClickSalvar: (contato: Contato) -> Unit
) {
    Scaffold(
        topBar = { CadastroAppBar(tituloAppBar) },
    ) { paddingValues ->
        CadastroContent(contatoCarregado,
            Modifier.padding(paddingValues),
            onClickSalvar = { onClickSalvar(it) }
        )
    }
}

@Composable
fun CadastroContent(
    contatoCarregado: Contato,
    modifier: Modifier = Modifier,
    onClickSalvar: (contato: Contato) -> Unit
) {
    val context = LocalContext.current
    val showImageDialog = remember { mutableStateOf(false) }
    val showDateDialog = remember { mutableStateOf(false) }

    var nome by remember { mutableStateOf(contatoCarregado.nome) }
    var sobrenome by remember { mutableStateOf(contatoCarregado.sobreNome) }
    var telefone by remember { mutableStateOf(contatoCarregado.telefone) }
    var imagemPeril by remember { mutableStateOf(contatoCarregado.fotoPerfil) }

    val textoAniversario = if (contatoCarregado.aniversario == null)
        "Aniversário" else contatoCarregado.aniversario.converteParaString()
    var aniversario by remember { mutableStateOf(textoAniversario) }

    if (showImageDialog.value) {
        CarregaFotoDialog(onDismiss = {
            showImageDialog.value = false
        }) { urlImage ->
            imagemPeril = urlImage
            showImageDialog.value = false
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
                        showImageDialog.value = true
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imagemPeril).build(),
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

            if (showDateDialog.value) {
                DataPickerDialogTest(
                    context,
                    onDismiss = { showDateDialog.value = false },
                    onClickDataSelecionada =
                    { dataSelecionada ->
                        aniversario = dataSelecionada.converteParaString()
                    }
                )
            }

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

            OutlinedButton(
                onClick = { showDateDialog.value = true }, modifier = Modifier
                    .fillMaxWidth()

            ) {
                Icon(
                    imageVector = Icons.Default.DateRange, contentDescription = null,
                    Modifier.padding(8.dp)
                )
                Text(text = aniversario)
            }

            Spacer(Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(56.dp),
                onClick = {
                    onClickSalvar(
                        Contato(
                            contatoCarregado.id,
                            nome = nome,
                            sobreNome = sobrenome,
                            telefone = telefone,
                            fotoPerfil = imagemPeril,
                            aniversario = aniversario.converteParaDate()
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.salvar))
            }
        }

    }
}

@Composable
fun CadastroAppBar(tituloApprBar: String) {
    TopAppBar(
        title = { Text(text = tituloApprBar) },
    )
}


@Composable
fun CarregaFotoDialogPrev() {
    CarregaFotoDialog({}, {})
}

@Preview
@Composable
fun CadastroScreenPrev() {
    CadastroScreen(Contato(), stringResource(id = R.string.title_activity_cadastro_contato), {})
}