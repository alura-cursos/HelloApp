package br.com.alura.helloapp.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.alura.helloapp.R
import br.com.alura.helloapp.converteParaString
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.*

fun caixaDialogoData(
    context: Context,
    onClickDispensar: () -> Unit,
    onClickDataSelecionada: (dataSelecionada: String) -> Unit,
) {

    val calendario = Calendar.getInstance()
    val anoAtual = calendario.get(Calendar.YEAR)
    val mesAtual = calendario.get(Calendar.MONTH)
    val diaAtual = calendario.get(Calendar.DAY_OF_MONTH)
    calendario.time = Date()

    val datePickerDialog = DatePickerDialog(
        context, { _: DatePicker, ano, mes, dia ->
            calendario.set(ano, mes, dia)
            val dataSelecionada = calendario.time
            onClickDataSelecionada(dataSelecionada.converteParaString())
        }, anoAtual, mesAtual, diaAtual
    )

    datePickerDialog.setOnDismissListener {
        onClickDispensar()
    }
    datePickerDialog.show()
}


@Composable
fun CaixaDialogoImagem(
    imageAtual: String,
    onClickDispensar: () -> Unit,
    onClickSalvar: (urlImagem: String) -> Unit
) {
    var urlImagem by remember { mutableStateOf(imageAtual) }

    Dialog(onDismissRequest = { onClickDispensar() }, content = {
        Column(
            Modifier
                .clip(RoundedCornerShape(5))
                .heightIn(250.dp, 400.dp)
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
                model = ImageRequest.Builder(LocalContext.current).data(urlImagem).build(),
                placeholder = painterResource(R.drawable.default_profile_picture),
                error = painterResource(R.drawable.default_profile_picture),
                contentDescription = stringResource(R.string.foto_perfil_contato),
            )

            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .heightIn(max = 80.dp),
                value = urlImagem,
                onValueChange = { urlImagem = it },
                label = { Text(stringResource(id = R.string.link_imagem)) })

            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onClickDispensar) {
                    Text(text = stringResource(R.string.cancelar))
                }

                TextButton(onClick = { onClickSalvar(urlImagem.toString()) }) {
                    Text(text = stringResource(R.string.salvar))
                }
            }
        }
    })
}


@Preview
@Composable
fun CaixaDialogoImagemPreview() {
    CaixaDialogoImagem("", {}, {})
}