package com.example.tiptime

import android.provider.Settings.System.getString
import java.text.NumberFormat
import java.util.*

class CurrencyExchangeUS {
    /*private fun Change(
        currentLocate: Locale,
        value: Double,
        valueFisrtExchange: Double,
        valueSecondExchange: Double,
        symbol: String?,
        cost: Double,
        tipPercentage: Double,
        localeFisrtExchange: Locale,
        localeSecondExchange: Locale
    ) {
        if (symbol == symbol) {
            //Calculate exchange by multiplying the cost of service per the euro value
            var exchange = cost.times(valueFisrtExchange)
            //Set the formatted Exchange to Euro
            val formattedExchange =
                NumberFormat.getCurrencyInstance(localeFisrtExchange).format(exchange)
            //Calculating the tip in Euro
            var tip = tipPercentage * exchange
            //If the round up tip switch is checked, it will round up the tip to the highest value possible
            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }
            exchange = exchange.plus(tip)
            val formattedTip =
                NumberFormat.getCurrencyInstance(localeFisrtExchange).format(exchange)
            val changeTip = NumberFormat.getCurrencyInstance(localeFisrtExchange).format(tip)
            //Set text of euro values on screen
            binding.tipResult.text = getString(R.string.tip_amount, changeTip)
            binding.textCurrencyExchangeCost.text =
                getString(R.string.currency_exchange_cost, formattedExchange)
            if (binding.roundUpSwitch.isChecked) {
                binding.textCurrencyExchange.text =
                    getString(R.string.currency_exchange_tip, formattedTip)
            } else binding.textCurrencyExchange.text =
                getString(R.string.currency_exchange, formattedTip)
            //Calculate exchange by multiplying the cost of service per the euro value
            var exchange2 = cost.times(valueSecondExchange)
            //Set the formatted Exchange to Euro
            val formattedExchange2 =
                NumberFormat.getCurrencyInstance(localeSecondExchange).format(exchange2)
            //Calculating the tip in Euro
            var tip2 = tipPercentage * exchange2
            //If the round up tip switch is checked, it will round up the tip to the highest value possible
            if (binding.roundUpSwitch.isChecked) {
                tip2 = kotlin.math.ceil(tip2)
            }
            exchange2 = exchange2.plus(tip2)
            val formattedTip2 =
                NumberFormat.getCurrencyInstance(localeSecondExchange).format(exchange2)
            val changeTip2 = NumberFormat.getCurrencyInstance(localeSecondExchange).format(tip2)
            //Set text of euro values on screen
            binding.tipResult2.text = getString(R.string.tip_amount, changeTip2)
            binding.textCurrencyExchangeCost2.text =
                getString(R.string.currency_exchange_cost, formattedExchange2)
            if (binding.roundUpSwitch.isChecked) {
                binding.textCurrencyExchange2.text =
                    getString(R.string.currency_exchange_tip, formattedTip2)
            } else binding.textCurrencyExchange2.text =
                getString(R.string.currency_exchange, formattedTip2)
        } else {
            //Calculate exchange by multiplying the cost of service per the euro value
            var exchange = cost * value
            //Set the formatted Exchange to Euro
            val formattedExchange = NumberFormat.getCurrencyInstance(currentLocate).format(exchange)
            //Calculating the tip in Euro
            var tip = tipPercentage * exchange
            //If the round up tip switch is checked, it will round up the tip to the highest value possible
            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }
            exchange = exchange.plus(tip)
            val formattedTip = NumberFormat.getCurrencyInstance(currentLocate).format(exchange)
            val changeTip = NumberFormat.getCurrencyInstance(currentLocate).format(tip)
            binding.tipResult.text = getString(R.string.tip_amount, changeTip)
            binding.textCurrencyExchangeCost.text =
                getString(R.string.currency_exchange_cost, formattedExchange)
            if (binding.roundUpSwitch.isChecked) {
                binding.textCurrencyExchange.text =
                    getString(R.string.currency_exchange_tip, formattedTip)
            } else binding.textCurrencyExchange.text =
                getString(R.string.currency_exchange, formattedTip)
        }
    }*/
}