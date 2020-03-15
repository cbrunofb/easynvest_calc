package com.easynvest.simulate.utils.masks

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.easynvest.simulate.utils.enums.MaskType
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


class EditTextMask(editText: EditText?, maskType: MaskType) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText> = WeakReference<EditText>(editText)
    private val maskType = maskType
    private val mLocale = Locale("pt", "BR")
    private var current = ""
    private val ddmmyyyy = "DDMMYYYY"
    private val cal = Calendar.getInstance()
    private val actualDay = cal.get(Calendar.DAY_OF_MONTH)
    private val actualMonth = cal.get(Calendar.MONTH) + 1
    private val actualYear = cal.get(Calendar.YEAR)

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {}

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {}

    override fun afterTextChanged(editable: Editable) {
        val editText: EditText = this.editTextWeakReference.get() ?: return
        val s = editable.toString()

        when (this.maskType) {
            MaskType.CURRENCY -> {
                if (s.isEmpty()) return
                editText.removeTextChangedListener(this)
                val cleanString = s.replace("[$,.]".toRegex(), "")
                    .replace("R", "")
                    .trim()
                val parsed: BigDecimal = BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR)
                    .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
                val formatted: String = NumberFormat.getCurrencyInstance(mLocale).format(parsed)
                editText.setText(formatted)
                editText.setSelection(formatted.length)
                editText.addTextChangedListener(this)
            }
            MaskType.DATE -> {
                if (s != current) {
                    var clean = s.replace(Regex("[^\\d.]|\\."), "")
                    val cleanC = current.replace(Regex("[^\\d.]|\\."), "")

                    val cl = clean.length
                    var sel = cl

                    for (i in 2..cl step 2){
                        if (i < 6)
                            sel++
                        else
                            break
                    }

                    if (clean == cleanC) sel--

                    if (clean.length < 8){
                        clean += ddmmyyyy.substring(clean.length).replace("Y", "A")
                    }else{

                        var day  = Integer.parseInt(clean.substring(0,2))
                        var mon  = Integer.parseInt(clean.substring(2,4))
                        var year = Integer.parseInt(clean.substring(4,8))

                        mon = when {
                            mon < actualMonth -> actualMonth // Lowest month
                            mon > 12 -> 12 // Highest month
                            else -> mon
                        }
                        
                        cal.set(Calendar.MONTH, mon-1)
                        year = when {
                            year < actualYear -> actualYear // Lowest year
                            year > 2100 -> 2100 // Highest year
                            else -> year
                        }
                        
                        cal.set(Calendar.YEAR, year)
                        
                        day = when {
                            day > cal.getActualMaximum(Calendar.DATE) -> cal.getActualMaximum(Calendar.DATE)
                            day < actualDay -> {
                                if (mon > actualMonth || year > actualYear)
                                    day
                                else
                                    actualDay + 1 // Minimum day is tomorrow
                            }
                            else -> day
                        }
                        
                        clean = String.format("%02d%02d%02d",day, mon, year)
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8))

                    sel = when {
                        sel < 0 -> 0
                        else -> sel
                    }
                    
                    current = clean
                    editText.setText(current)
                    editText.setSelection(
                        when {
                            sel < current.length -> sel
                            else -> current.length
                        }
                    )
                }
            }
            MaskType.RATE -> {
                if (s.isEmpty()) return
                editText.removeTextChangedListener(this)
                val cleanString = s.replace("[$,.]".toRegex(), "")
                    .replace("%", "").trim()
                val formatted = if (cleanString.isNotEmpty())
                    "$cleanString%"
                else
                    ""
                current = formatted
                editText.setText(formatted)
                editText.setSelection(
                    if (formatted.length-1 > 0)
                        formatted.length-1
                    else
                        formatted.length
                )
                editText.addTextChangedListener(this)
            }
        }
    }
}