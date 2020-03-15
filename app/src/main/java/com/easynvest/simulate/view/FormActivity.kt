package com.easynvest.simulate.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.easynvest.R
import com.easynvest.simulate.utils.enums.MaskType
import com.easynvest.simulate.utils.masks.Masks
import kotlinx.android.synthetic.main.activity_form.*
import kotlin.collections.ArrayList


class FormActivity : AppCompatActivity() {

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
        btn_simulate.isEnabled = checkFields()
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
            navigateToSimulate()
        }
    }

    private fun navigateToSimulate() {
        val intent = Intent(this, SimulateActivity::class.java)
        startActivity(intent)
    }
}
