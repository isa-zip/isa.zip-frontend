package com.example.zipfront

import android.text.Editable
import android.text.TextWatcher

class DynamicTextWatcher (
    private val afterChanged: ((Editable?) -> Unit) = {},
    private val beforeChanged: ((CharSequence?, Int, Int, Int) -> Unit) = { _, _, _, _ -> },
    private val onChanged: ((CharSequence?, Int, Int, Int) -> Unit) = { _, _, _, _, -> }
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        afterChanged(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeChanged(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onChanged(s, start, before, count)
    }
}