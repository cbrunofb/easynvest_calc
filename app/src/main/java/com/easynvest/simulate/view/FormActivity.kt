package com.easynvest.simulate.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.easynvest.R
import com.easynvest.simulate.model.SimulateParams
import com.easynvest.simulate.utils.enums.MaskType
import com.easynvest.simulate.utils.masks.Masks
import kotlinx.android.synthetic.main.activity_form.*
import kotlin.collections.ArrayList


class FormActivity : AppCompatActivity() {

    companion object {
        const val TAG= "FORM_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setListeners()

    }

    private fun setListeners() {
        disableHintsWhenFocus()
        addOnTextChangedListeners() // For button enabling
        addFieldsMasks()
        addButtonListener()
    }

    private fun disableHintsWhenFocus() {
        for (et in getEditTexts()) {
            et.let {
                val hint = it.hint
                it.setOnFocusChangeListener { _, hasFocus ->
                    runOnUiThread {
                        it.hint =
                            if (hasFocus)
                                ""
                            else
                                hint
                    }
                }
            }
        }
    }

    private fun getEditTexts(): ArrayList<EditText> {
        return arrayListOf(
            et_invested_amount,
            et_maturity_date,
            et_rate
        )
    }

    private fun addOnTextChangedListeners() {
        for (et in getEditTexts()) {
            et.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    runOnUiThread {
                        buttonEnabling()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            })
        }
    }

    private fun buttonEnabling() {
        btn_simulate.isEnabled = checkFields() && isMaturityDateValid()
    }

    private fun checkFields(): Boolean {
        var areFieldsFilled = true

        for(et in getEditTexts()) {
            if (!isFieldFilled(et)) {
                areFieldsFilled = false
                return areFieldsFilled
            }
        }

        return areFieldsFilled
    }

    private fun isFieldFilled(editText: EditText): Boolean {
        return !editText.text.isNullOrEmpty()
    }

    private fun isMaturityDateValid(): Boolean {
        val etMaturyDateValue = et_maturity_date.text.toString()
        val cleanValid = etMaturyDateValue.replace("A", "")
            .replace("M", "")
            .replace("D", "")
            .replace("/", "")

        val isNumeric = cleanValid.matches("-?\\d+(\\.\\d+)?".toRegex())

        return isNumeric && cleanValid.length == 8
    }

    private fun addFieldsMasks() {
        addInvestedAmountMask()
        addMaturityDateMask()
        addRateMask()
    }

    private fun addInvestedAmountMask() {
        et_invested_amount.let {
            it.addTextChangedListener(
                Masks(
                    it, MaskType.CURRENCY
                )
            )
        }
    }

    private fun addMaturityDateMask() {
        et_maturity_date.let {
            it.addTextChangedListener(
                Masks(
                    it, MaskType.DATE
                )
            )
        }
    }

    private fun addRateMask() {
        et_rate.let {
            it.addTextChangedListener(
                Masks(
                    it, MaskType.RATE
                )
            )
        }
    }

    private fun addButtonListener() {
        btn_simulate.setOnClickListener {
            val simulateParams = prepareParams()
            navigateToSimulate(simulateParams)
        }
    }

    private fun prepareParams(): SimulateParams {
        // investedAmountValue
        val investedAmountValue = et_invested_amount.text.toString()
            .replace("R$", "")
            .replace(",", ".")
            .trim()

        // maturityDateValue
        val splittedMaturityDate = et_maturity_date.text.toString()
            .split("/")
        val maturityDateValue = String.format(
            "%s-%s-%s",
            splittedMaturityDate[2],
            splittedMaturityDate[1],
            splittedMaturityDate[0]
        )

        // rateValue
        val rateValue = et_rate.text.toString()
            .replace("%", "")
            .trim()

        return SimulateParams(
            investedAmount = investedAmountValue.toFloat(),
            maturityDate = maturityDateValue,
            rate = rateValue.toFloat()
        )
    }

    private fun navigateToSimulate(simulateParams: SimulateParams) {
        val intent = Intent(this, SimulateActivity::class.java)
        intent.putExtra("FORM_ACTIVITY", simulateParams)
        startActivity(intent)
    }
}
