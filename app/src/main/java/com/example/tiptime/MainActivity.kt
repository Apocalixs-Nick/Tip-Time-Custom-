package com.example.tiptime

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //At the click of the button the private calculateTip function is called
        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        //At the click of the button the private currencyExchange function is called
        binding.buttonCurrencyExchange.setOnClickListener { currencyExchange() }
    }

    /**
     * The private calculateTip function allows you to calculate the tip (possibly round it up) based on the cost of the service and the experience of the service
     */
    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost != null) {
            val selectedId = binding.tipOptions.checkedRadioButtonId
            val tipPercentage = when (selectedId) {
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                else -> 0.15
            }
            var tip = tipPercentage * cost
            val roundUp = binding.roundUpSwitch.isChecked
            if (roundUp) {
                tip = kotlin.math.ceil(tip)
            }
            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
            binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
        } else showErrorMessage(R.string.error_cost)
        //binding.tipResult.text = getString(R.string.tip_amount)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    /**
     * The private function currencyExchange converts the cost of the service (in this case in dollars) in euro (referring to Italy,
     * displaying on screen the cost with the tip rounded and not
     */
    private fun currencyExchange() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost != null) {
            val euroValue = 1.03
            var exchange = cost.times(euroValue)
            val formattedExchange = NumberFormat.getCurrencyInstance(Locale.ITALY).format(exchange)
            binding.textCurrencyExchangeCost.text =
                getString(R.string.currency_exchange_cost, formattedExchange)
            val selectedId = binding.tipOptions.checkedRadioButtonId
            val tipPercentage = when (selectedId) {
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                else -> 0.15
            }
            var tip = tipPercentage * cost
            val roundUp = binding.roundUpSwitch.isChecked
            if (roundUp) {
                tip = kotlin.math.ceil(tip)
            }
            exchange = exchange.plus(tip)
            val formattedTip = NumberFormat.getCurrencyInstance(Locale.ITALY).format(exchange)
            if (roundUp) {
                binding.textCurrencyExchange.text =
                    getString(R.string.currency_exchange_tip, formattedTip)
            } else binding.textCurrencyExchange.text =
                getString(R.string.currency_exchange, formattedTip)
        } else showErrorMessage(R.string.error_change)
    }

    /**
     * The private function showErrorMessage prints an alert with the error message
     */
    private fun showErrorMessage(errorMessageId: Int) {
        Toast.makeText(applicationContext, getString(errorMessageId), Toast.LENGTH_SHORT).show()
    }
}