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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.alura.helloapp.R
import br.com.alura.helloapp.extensions.converteParaString
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import br.com.alura.helloapp.util.FORMATO_DATA_DIA_MES_ANO
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun caixaDialogoData(
    context: Context,
    dataAtual: Date?,
    onClickDispensar: () -> Unit = {},
    onClickDataSelecionada: (dataSelecionada: String) -> Unit = {}
) {
    val formatadorDeData = DateTimeFormatter.ofPattern(FORMATO_DATA_DIA_MES_ANO)
    val dataLocal = if (dataAtual == null) LocalDate.now()
    else LocalDate.parse(dataAtual.converteParaString(), formatadorDeData)

    val anoAtual = dataLocal.year
    val mesAtual = dataLocal.monthValue
    val diaAtual = dataLocal.dayOfMonth

    val datePickerDialog = DatePickerDialog(
        context, { _: DatePicker, ano, mes, dia ->
            val dataSelecionada = LocalDate.parse("$dia/${mes + 1}/$ano", formatadorDeData)
            onClickDataSelecionada(dataSelecionada.format(formatadorDeData))
        }, anoAtual, (mesAtual - 1), diaAtual
    )

    datePickerDialog.setOnDismissListener {
        onClickDispensar()
    }
    datePickerDialog.show()
}

@Composable
fun CaixaDialogoImagem(
    fotoPerfil: String,
    modifier: Modifier = Modifier,
    onFotoPerfilMudou: (String) -> Unit = {},
    onClickDispensar: () -> Unit = {},
    onClickSalvar: (urlImagem: String) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onClickDispensar,
        content = {
            Column(
                modifier
                    .clip(RoundedCornerShape(5))
                    .heightIn(250.dp, 400.dp)
                    .widthIn(200.dp)
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImagePerfil(
                    urlImagem = fotoPerfil,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(5, 5))
                )

                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .heightIn(max = 80.dp),
                    value = fotoPerfil,
                    onValueChange = onFotoPerfilMudou,
                    label = { Text(stringResource(id = R.string.link_imagem)) })

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onClickDispensar) {
                        Text(text = stringResource(R.string.cancelar))
                    }
                    TextButton(onClick = { onClickSalvar(fotoPerfil) }) {
                        Text(text = stringResource(R.string.salvar))
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun CaixaDialogoImagemPreview() {
    HelloAppTheme {
        CaixaDialogoImagem("")
    }
}