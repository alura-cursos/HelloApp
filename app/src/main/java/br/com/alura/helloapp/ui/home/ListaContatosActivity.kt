package br.com.alura.helloapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.alura.helloapp.ui.HelloAppNavHost
import br.com.alura.helloapp.ui.form.FormularioContatoViewModel
import br.com.alura.helloapp.ui.theme.HelloAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListaContatosActivity : ComponentActivity() {

//    private val contatoDao by lazy {
//        HelloAppDatabase.getDatabase(this).contatoDao()
//    }


    @Inject
    lateinit var formularioContatoTelaViewmodelFactory: FormularioContatoViewModel.FormularioContatoViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloAppTheme {
                val navController = rememberNavController()
                HelloAppNavHost(
                    navController = navController,
                    formularioContatoTelaViewmodelFactory = formularioContatoTelaViewmodelFactory
                )
            }
        }
    }
}