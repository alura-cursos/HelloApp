package br.com.alura.helloapp.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class AuxilarRecursoString {
    class RecursoString(@StringRes val resId: Int) : AuxilarRecursoString()

    @Composable
    fun asString(): String {
        return when (this) {
            is RecursoString -> {
                stringResource(id = resId)
            }
        }
    }
}
