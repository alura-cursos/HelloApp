package br.com.alura.helloapp

import android.app.Application
import br.com.alura.helloapp.database.HelloAppDatabase

class HelloAppAplication : Application() {
    val database: HelloAppDatabase
        get() = HelloAppDatabase.getDatabase(this)
}