package br.com.alura.helloapp.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class AuxilarStringResource {
    class StringResource(@StringRes val resId: Int) : AuxilarStringResource()

    @Composable
    fun asString(): String {
        return when (this) {
            is StringResource -> {
                stringResource(id = resId)
            }
        }
    }
}
