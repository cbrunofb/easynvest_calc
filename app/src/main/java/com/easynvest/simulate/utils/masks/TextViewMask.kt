package com.easynvest.simulate.utils.masks

import android.widget.TextView
import com.easynvest.simulate.utils.enums.MaskType

class TextViewMask(textView: TextView, maskType: MaskType) {

    private val textView = textView
    private val maskType = maskType

    fun applyMask() {
        val textViewValue = textView.text.toString()
        when (this.maskType) {
            MaskType.CURRENCY -> {
                val formattedValue = String.format("R$ %s", textViewValue).replace(".", ",")
                textView.text =
                    if (formattedValue.split(",").last().length < 2)
                        String.format("%S0", formattedValue)
                    else
                        formattedValue
            }
            MaskType.DATE -> {
                val splittedValue = textViewValue.split("-")
                textView.text = String.format("%s/%s/%s", splittedValue[2].substring(0,2)
                    .substring(0,2), splittedValue[1].substring(0,2), splittedValue[0].substring(0,4))
            }
            MaskType.PERCENTAGE -> {
                val cdiPercentageValue = textView.text
                textView.text = String.format("%s%%", cdiPercentageValue)
            }
        }
    }
}