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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.alura.helloapp.R
import br.com.alura.helloapp.data.Contato
import br.com.alura.helloapp.ui.components.CaixaDialogoImagem
import br.com.alura.helloapp.ui.components.caixaDialogoData
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CadastroContatoTela(
    modifier: Modifier = Modifier,
    viewModel: CadastroContatoViewModel = viewModel(),
    onClickSalvar: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            state.tituloAppbar?.let { titulo ->
                CadastroContatoAppBar(stringResource(id = titulo))
            }
        },

        ) { paddingValues ->

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
                            viewModel.abriCaixaImagem()
                        },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.contato.fotoPerfil).build(),
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
                val focuAtual = LocalFocusManager.current
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    value = state.contato.nome,
                    onValueChange = state.onNomeMudou,
                    label = { Text(stringResource(id = R.string.nome)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = (KeyboardActions(onNext = { focuAtual.moveFocus(FocusDirection.Next) }))
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.contato.sobrenome,
                    onValueChange = state.onSobrenomeMudou,
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
                    value = state.contato.telefone,
                    onValueChange = state.onTelefoneMudou,
                    label = { Text(stringResource(id = R.string.telefone)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = (KeyboardActions(onNext = { focuAtual.clearFocus() }))
                )

                OutlinedButton(
                    onClick = { viewModel.mostrarCaixaData() },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        Modifier.padding(8.dp)
                    )
                    Text(text = viewModel.defineTextoAniversario(stringResource(id = R.string.aniversario)))
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(56.dp),
                    onClick = {
                        onClickSalvar()
                        viewModel.salvarContato(
                            Contato(
                                state.contato.id,
                                nome = state.contato.nome,
                                sobrenome = state.contato.sobrenome,
                                telefone = state.contato.telefone,
                                fotoPerfil = state.contato.fotoPerfil,
                                aniversario = state.contato.aniversario
                            )
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.salvar))
                }
            }

            if (state.mostrarCaixaDialogoImagem) {
                CaixaDialogoImagem(state.contato.fotoPerfil,
                    onFotoPerfilMudou = state.onFotoPerfilMudou,
                    onClickDispensar = {
                        viewModel.fecharCaixaImagem()
                    },
                    onClickSalvar = {
                        viewModel.carregaImagem(it)
                    }
                )
            }

            if (state.mostrarCaixaDialogoData) {
                caixaDialogoData(
                    LocalContext.current,
                    dataAtual = state.contato.aniversario,
                    onClickDispensar = { viewModel.fecharCaixaData() },
                    onClickDataSelecionada = state.onAniversarioMudou
                )
            }
        }
    }
}

@Composable
fun CadastroContatoAppBar(tituloApprBar: String) {
    TopAppBar(
        title = { Text(text = tituloApprBar) },
    )
}

@Preview
@Composable
fun TelaCadastroPreview() {
    CadastroContatoTela(
        Modifier,
        viewModel(),
        {}
    )
}