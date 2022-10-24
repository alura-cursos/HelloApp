package br.com.alura.helloapp.ui.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.helloapp.R
import br.com.alura.helloapp.converteParaDate
import br.com.alura.helloapp.converteParaString
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.ui.components.CaixaDialogoImagem
import br.com.alura.helloapp.ui.components.caixaDialogoData
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun TelaCadastro(
    modifier: Modifier = Modifier,
    contatoCarregado: Contato,
    tituloAppBar: String,
    onClickSalvar: (contato: Contato) -> Unit
) {
    Scaffold(
        topBar = { CadastroAppBar(tituloAppBar) },
    ) { paddingValues ->
        val context = LocalContext.current
        val mostrarCaixaDialogoImagem = remember { mutableStateOf(false) }
        val mostrarCaixaDialogoData = remember { mutableStateOf(false) }

        var nome by remember { mutableStateOf(contatoCarregado.nome) }
        var sobrenome by remember { mutableStateOf(contatoCarregado.sobrenome) }
        var telefone by remember { mutableStateOf(contatoCarregado.telefone) }
        var imagemPeril by remember { mutableStateOf(contatoCarregado.fotoPerfil) }

        val textoAniversario =
            if (contatoCarregado.aniversario == null) stringResource(id = R.string.aniversario) else contatoCarregado.aniversario.converteParaString()
        var aniversario by remember { mutableStateOf(textoAniversario) }

        if (mostrarCaixaDialogoImagem.value) {
            CaixaDialogoImagem(onClickDispensar = {
                mostrarCaixaDialogoImagem.value = false
            }) { urlImage ->
                imagemPeril = urlImage
                mostrarCaixaDialogoImagem.value = false
            }
        }

        Column(
            modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .clickable {
                            mostrarCaixaDialogoImagem.value = true
                        },
                    model = ImageRequest.Builder(LocalContext.current).data(imagemPeril).build(),
                    placeholder = painterResource(R.drawable.default_profile_picture),
                    error = painterResource(R.drawable.default_profile_picture),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.foto_perfil_contato),
                )
                Text(
                    text = stringResource(R.string.adicionar_foto),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .weight(2f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (mostrarCaixaDialogoData.value) {
                    caixaDialogoData(context,
                        onClickDispensar = { mostrarCaixaDialogoData.value = false },
                        onClickDataSelecionada = { dataSelecionada ->
                            aniversario = dataSelecionada.converteParaString()
                        })
                }
                val focuAtual = LocalFocusManager.current

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text(stringResource(id = R.string.nome)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = (KeyboardActions(onNext = { focuAtual.moveFocus(FocusDirection.Next) }))
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = sobrenome,
                    onValueChange = { sobrenome = it },
                    label = { Text(stringResource(id = R.string.sobrenome)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = (KeyboardActions(onNext = { focuAtual.moveFocus(FocusDirection.Next) }))
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null
                        )
                    },
                    value = telefone,
                    onValueChange = { telefone = it },
                    label = { Text(stringResource(id = R.string.telefone)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = (KeyboardActions(onNext = { focuAtual.clearFocus() }))
                )

                OutlinedButton(
                    onClick = { mostrarCaixaDialogoData.value = true },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
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
                                sobrenome = sobrenome,
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
}

@Composable
fun CadastroAppBar(tituloApprBar: String) {
    TopAppBar(
        title = { Text(text = tituloApprBar) },
    )
}

@Preview
@Composable
fun TelaCadastroPreview() {
    TelaCadastro(
        Modifier,
        Contato(),
        stringResource(id = R.string.title_activity_cadastro_contato)
    ) {}
}