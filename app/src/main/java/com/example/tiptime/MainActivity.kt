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
const val usValue = 0.9765
const val poundsValue = 0.8765

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
        //Get current text inside the EditText of CostofService
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        //Get the tipPercentage by checking what radio button is pressed
        var tipPercentage = getTipPercentage()
        //Check if the cost of service is empty
        if (cost != null) {
            //If the CustomTip text is not empty, then get the text and use it as a percentage
            //Else set the text to 0 to prevent crashes
            if (!checkIfCustomTipIsEmpty()) {
                tipPercentage = binding.customTipEditText.text.toString().toDouble() / 100
            }
            //Calculate tip
            var tip = tipPercentage * cost
            //If the round up tip switch is checked, it will round up the tip to the highest value possible
            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }
            //Format tip to current location value
            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
            binding.firstTipResult.text = getString(R.string.tip_amount, formattedTip)
            //If the Euro Switch is checked, then call the method to do the currency exchange
            //Else set the text to currency euro to empty if the user don't want anymore this values
            if (binding.convertToEuroSwitch.isChecked) {
                currencyExchange(cost, tipPercentage)
            } else {
                setTextToEmptyCurrencyEuro()
            }
        } else binding.costOfServiceEditText.error = getString(R.string.cost_of_service_error)
        //binding.tipResult.text = getString(R.string.tip_amount)
    }

    /**
     * Private function for setting text, calculations and currency exchange
     */
    private fun Change(
        currentLocate: Locale,
        value: Double,
        valueFirstExchange: Double,
        valueSecondExchange: Double,
        symbol: String?,
        cost: Double,
        tipPercentage: Double,
        localeFirstExchange: Locale,
        localeSecondExchange: Locale
    ) {
        if (symbol == symbol) {
            //Calculate the exchange by multiplying the cost of the service by the first currency exchange
            var exchange = cost.times(valueFirstExchange)
            //Set the exchange format to the first currency exchange
            val formattedExchange =
                NumberFormat.getCurrencyInstance(localeFirstExchange).format(exchange)
            //Tip calculation based on the first currency exchange
            var tip = tipPercentage * exchange
            //If the round up tip switch is checked, it will round up the tip to the highest value possible
            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }
            exchange = exchange.plus(tip)
            val formattedTip =
                NumberFormat.getCurrencyInstance(localeFirstExchange).format(exchange)
            val changeTip = NumberFormat.getCurrencyInstance(localeFirstExchange).format(tip)
            //Set the text of values based on the first currency exchange on the screen
            binding.firstTipResult.text = getString(R.string.tip_amount, changeTip)
            binding.firstTextCurrencyExchangeCost.text =
                getString(R.string.currency_exchange_cost, formattedExchange)
            if (binding.roundUpSwitch.isChecked) {
                binding.firstTextCurrencyExchange.text =
                    getString(R.string.currency_exchange_tip, formattedTip)
            } else binding.firstTextCurrencyExchange.text =
                getString(R.string.currency_exchange, formattedTip)
            //Calculate the exchange by multiplying the cost of the service by the second currency exchange
            var exchange2 = cost.times(valueSecondExchange)
            //Set the exchange format to the second currency exchange
            val formattedExchange2 =
                NumberFormat.getCurrencyInstance(localeSecondExchange).format(exchange2)
            //Tip calculation based on the second currency exchange
            var tip2 = tipPercentage * exchange2
            //If the round up tip switch is checked, it will round up the tip to the highest value possible
            if (binding.roundUpSwitch.isChecked) {
                tip2 = kotlin.math.ceil(tip2)
            }
            exchange2 = exchange2.plus(tip2)
            val formattedTip2 =
                NumberFormat.getCurrencyInstance(localeSecondExchange).format(exchange2)
            val changeTip2 = NumberFormat.getCurrencyInstance(localeSecondExchange).format(tip2)
            //Set the text of values based on the second currency exchange on the screen
            binding.secondTipResult.text = getString(R.string.tip_amount, changeTip2)
            binding.secondTextCurrencyExchangeCost.text =
                getString(R.string.currency_exchange_cost, formattedExchange2)
            if (binding.roundUpSwitch.isChecked) {
                binding.secondTextCurrencyExchange.text =
                    getString(R.string.currency_exchange_tip, formattedTip2)
            } else binding.secondTextCurrencyExchange.text =
                getString(R.string.currency_exchange, formattedTip2)
        } else {
            //Calculate the exchange by multiplying the cost of the service by the current currency
            var exchange = cost * value
            //Set the exchange format to the current currency exchange
            val formattedExchange = NumberFormat.getCurrencyInstance(currentLocate).format(exchange)
            //Calculation of tip with current currency
            var tip = tipPercentage * exchange
            //If the round up tip switch is checked, it will round up the tip to the highest value possible
            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }
            exchange = exchange.plus(tip)
            val formattedTip = NumberFormat.getCurrencyInstance(currentLocate).format(exchange)
            val changeTip = NumberFormat.getCurrencyInstance(currentLocate).format(tip)
            //Set the value text based on the current currency on the screen
            binding.firstTipResult.text = getString(R.string.tip_amount, changeTip)
            binding.firstTextCurrencyExchangeCost.text =
                getString(R.string.currency_exchange_cost, formattedExchange)
            if (binding.roundUpSwitch.isChecked) {
                binding.firstTextCurrencyExchange.text =
                    getString(R.string.currency_exchange_tip, formattedTip)
            } else binding.firstTextCurrencyExchange.text =
                getString(R.string.currency_exchange, formattedTip)
        }
    }

    /**
     * The private function currencyExchange detects the currency of the device (€-$-£) converts the cost of the service (in this case in dollars)
     * in euro (with reference to Italy) and pounds (UK),
     * calling the private function Change
     */
    private fun currencyExchange(cost: Double, tipPercentage: Double) {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        val symbol = numberFormat.currency?.symbol
        if (symbol == "$") {
            Change(
                Locale.US,
                usValue,
                euroValue,
                poundsValue,
                symbol,
                cost,
                tipPercentage,
                Locale.ITALY,
                Locale.UK
            )
        } else if (symbol == "€") {
            Change(
                Locale.ITALY,
                euroValue,
                usValue,
                poundsValue,
                symbol,
                cost,
                tipPercentage,
                Locale.US,
                Locale.UK
            )
        } else if (symbol == "£") {
            Change(
                Locale.UK,
                poundsValue,
                usValue,
                euroValue,
                symbol,
                cost,
                tipPercentage,
                Locale.US,
                Locale.ITALY
            )
        }
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
    private fun setTextToEmptyCurrencyEuro() {
        binding.firstTextCurrencyExchange.text = ""
        binding.firstTextCurrencyExchangeCost.text = ""
        binding.secondTipResult.text = ""
        binding.secondTextCurrencyExchange.text = ""
        binding.secondTextCurrencyExchangeCost.text = ""
    }

    /**
     * Get the tip percentage by checking what radio button id is pressed and then
     * returns a double
     */
    private fun getTipPercentage(): Double {
        return when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else -> 0.00
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

    private fun checkIfCustomTipIsEmpty(): Boolean {
        return binding.customTipEditText.text.toString().isEmpty()
    }

    fun showCustomTipEditText(view: View) {
        if (binding.tipOptions.checkedRadioButtonId == R.id.option_custom_tip_percent) {
            binding.customTip.visibility = (View.VISIBLE)
            binding.customTipEditText.setText("0")
        } else {
            binding.customTip.visibility = (View.GONE)
            binding.customTipEditText.text?.clear()
        }
    }
}