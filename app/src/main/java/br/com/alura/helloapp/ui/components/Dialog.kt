package br.com.alura.helloapp.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.*

fun dataPickerDialog(
    context: Context,
    onDismiss: () -> Unit,
    onClickDataSelecionada: (dataSelecionada: Date) -> Unit,
) {

    val calendario = Calendar.getInstance()
    val ano = calendario.get(Calendar.YEAR)
    val mes = calendario.get(Calendar.MONTH)
    val dia = calendario.get(Calendar.DAY_OF_MONTH)
    calendario.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, _: Int, _: Int, _: Int ->
            calendario.set(ano, mes, dia)
            val dataSelecionada = calendario.time
            onClickDataSelecionada(dataSelecionada)
        },
        ano, mes, dia
    )

    datePickerDialog.setOnDismissListener {
        onDismiss()
    }
    datePickerDialog.show()

}


@Composable
fun CarregaFotoDialog(
    onDismiss: () -> Unit,
    onConfirmButton: (urlImagem: String) -> Unit
) {
    var urlImagem by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
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
                        .data(urlImagem).build(),
                    placeholder = painterResource(R.drawable.default_profile_picture),
                    error = painterResource(R.drawable.default_profile_picture),
                    contentDescription = stringResource(R.string.foto_perfil_contato),
                )


                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                    value = urlImagem,
                    maxLines = 1,
                    onValueChange = { urlImagem = it },
                    label = { Text(stringResource(id = R.string.link_imagem)) })

//                Button(
//                    onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(text = stringResource(R.string.carregar))
//                }

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.cancelar))
                    }

                    TextButton(onClick = { onConfirmButton(urlImagem) }) {
                        Text(text = stringResource(R.string.salvar))
                    }
                }
            }
        }
    )

}


@Preview
@Composable
fun CarregaFotoDialogPreview() {
    CarregaFotoDialog(onDismiss = { /*TODO*/ }, onConfirmButton = {})
}