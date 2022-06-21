package com.LT.lesctor_texto

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.LT.lesctor_texto.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //Comunicacion con la interfaces
    private lateinit var interfacesActibity: ActivityMainBinding
    //libreria para hablar
    var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //incializamos el biding
        interfacesActibity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(interfacesActibity.root)

        //Incializamos el reproductor de audio y texto
        tts = TextToSpeech( this, this )

        //metodos de accion de la app
        mensajeBienvenida()

        interfacesActibity.btnHablar.setOnClickListener {
            recuperarTexto()
        }
    }

    private fun hablar(texto: String) {
        tts!!.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    //Se resupera el texto de la caja de texto en la interfaces principal y se envia al modulo de lectura
    fun recuperarTexto(){

        val textoRecuperado = interfacesActibity.TxtRecitar.text.toString()

        val mensaje: String = textoRecuperado.ifEmpty {
            "Es nesesario que agreges un mensaje"
        }

        hablar( mensaje )
    }

    fun mensajeBienvenida(){

        hablar( "Hola soy lizz una aplicación capas de hablar, bueno solo si ingresas un " +
                "texto en la parte de los 3 puntos" )
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS)
            tts!!.setLanguage(Locale("ES"))
    }

    override fun onDestroy() {

        if ( tts != null ) {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }
}