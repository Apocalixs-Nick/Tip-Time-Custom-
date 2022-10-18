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
const val euroValue = 1.03
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
    }

    /**
     * The private calculateTip function allows you to calculate the tip (possibly round it up) based on the cost of the service and the experience of the service
     */
    private fun calculateTip() {
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (cost != null) {
            val tipPercentage = getTipPercentage()
            var tip = tipPercentage * cost
            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }
            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
            binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
            if(binding.convertToEuroSwitch.isChecked){
                currencyExchange(cost, tipPercentage)
            } else {
                setTextToEmptyCurrencyEuro()
            }
        } else showErrorMessage(R.string.error_cost)
        //binding.tipResult.text = getString(R.string.tip_amount)
    }

    /**
     * The private function currencyExchange converts the cost of the service (in this case in dollars) in euro (referring to Italy,
     * displaying on screen the cost with the tip rounded and not
     */
    private fun currencyExchange(cost: Double, tipPercentage: Double) {
        var exchange = cost.times(euroValue)
        val formattedExchange = NumberFormat.getCurrencyInstance(Locale.ITALY).format(exchange)
        binding.textCurrencyExchangeCost.text =
            getString(R.string.currency_exchange_cost, formattedExchange)
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        exchange = exchange.plus(tip)
        val formattedTip = NumberFormat.getCurrencyInstance(Locale.ITALY).format(exchange)
        if (binding.roundUpSwitch.isChecked) {
            binding.textCurrencyExchange.text =
                getString(R.string.currency_exchange_tip, formattedTip)
        } else binding.textCurrencyExchange.text =
            getString(R.string.currency_exchange, formattedTip)
    }

    /**
     * The private function showErrorMessage prints an alert with the error message
     */
    private fun showErrorMessage(errorMessageId: Int) {
        Toast.makeText(applicationContext, getString(errorMessageId), Toast.LENGTH_SHORT).show()
    }

    /**
     * Set the currency exchange text to an empty one if the user before wanted the
     * result in euro currency and after he turned to disabled the switch
     */
    private fun setTextToEmptyCurrencyEuro(){
        binding.textCurrencyExchange.text = ""
        binding.textCurrencyExchangeCost.text = ""
    }

    /**
     * Get the tip percentage by checking what radio button id is pressed and then
     * returns a double
     */
    private fun getTipPercentage(): Double{
        return when (binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
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
}