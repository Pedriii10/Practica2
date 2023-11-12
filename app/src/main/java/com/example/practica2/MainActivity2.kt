package com.example.practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica2.databinding.ActivityMain2Binding
import com.example.practica2.databinding.ActivityMainBinding

private const val WEIGHT_EXTRA = "weight"
private const val HEIGHT_EXTRA = "height"
private const val GENDER_EXTRA = "gender"


class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val weight = intent.getStringExtra(WEIGHT_EXTRA)
        val height = intent.getStringExtra(HEIGHT_EXTRA)
        val gender = intent.getStringExtra(GENDER_EXTRA)
        val IMC = intent.getDoubleExtra("IMC", 0.0) // Cambia a getDoubleExtra para IMC
        val state = intent.getStringExtra("state")

        binding.textViewResult.text = String.format("%.2f", IMC)
        binding.stateResult.text = state

        binding.secondReturnButton.setOnClickListener {
            finish()
        }
    }
}

