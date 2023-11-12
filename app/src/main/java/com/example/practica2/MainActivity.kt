package com.example.practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import com.example.practica2.databinding.ActivityMainBinding

private const val WEIGHT_EXTRA = "weight"
private const val HEIGHT_EXTRA = "height"
private const val GENDER_EXTRA = "gender"
private var weight: Double = 0.0
private var height: Double = 0.0
private var gender: String = ""



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calcButton.setOnClickListener {
            if (isFieldsValid()) {
                saveDataAndCalculateIMC()
            }
        }
    }

    private fun sendInfoToSecondActivity(IMC: Double, state: String) {
        val myIntent = Intent(this, MainActivity2::class.java)
        myIntent.putExtra(WEIGHT_EXTRA, binding.weightText.text.toString())
        myIntent.putExtra(HEIGHT_EXTRA, binding.heightText.text.toString())
        myIntent.putExtra(GENDER_EXTRA, getSelectedGenderText())
        myIntent.putExtra("IMC", IMC)
        myIntent.putExtra("state", state)
        startActivity(myIntent)
    }


    private fun isFieldsValid(): Boolean {
        if (TextUtils.isEmpty(binding.heightText.text.toString()) ||
            TextUtils.isEmpty(binding.weightText.text.toString()) ||
            (!binding.genderOptionMale.isChecked && !binding.genderOptionFemale.isChecked)
        ) {
            binding.textError.text = "Datos sin completar"
            return false
        }
        return true
    }

    private fun getSelectedGenderText(): String {
        return if (binding.genderOptionMale.isChecked) {
            getString(R.string.radioButton_male_name)
        } else {
            getString(R.string.radioButton_female_name)
        }
    }

    private fun saveDataAndCalculateIMC() {
        // Elimina la siguiente línea, ya que declara una variable local que sobrescribe la variable de clase
        // val weight = binding.weightText.text.toString().toDouble()
        val weight = binding.weightText.text.toString().toDouble()
        val height = binding.heightText.text.toString().toDouble()
        val gender = getSelectedGenderText()

        val IMC = calculateIMC(weight, height, gender)

        // Envía los resultados a la siguiente actividad
        sendInfoToSecondActivity(IMC, getIMCState(IMC, gender))
    }


    private fun calculateIMC(weight: Double, height: Double, gender: String): Double {
        return weight / (height * height)
    }

    private fun getIMCState(IMC: Double, gender: String): String {
        return when {
            gender.equals("Male", ignoreCase = true) -> {
                when {
                    IMC < 18.5 -> "Peso inferior al normal"
                    IMC <= 24.9 -> "Normal"
                    IMC <= 29.9 -> "Sobrepeso"
                    else -> "Obesidad"
                }
            }
            else -> {
                when {
                    IMC < 18.5 -> "Peso inferior al normal"
                    IMC <= 23.9 -> "Normal"
                    IMC <= 28.9 -> "Sobrepeso"
                    else -> "Obesidad"
                }
            }
        }
    }
}

