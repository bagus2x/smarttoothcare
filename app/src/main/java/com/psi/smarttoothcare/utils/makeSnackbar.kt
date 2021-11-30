package com.psi.smarttoothcare.utils

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.makeSnackbar(text: String, duration: Int): Snackbar? {
    activity?.let {
        return Snackbar.make(it.findViewById(android.R.id.content), text, duration)
    }

    return null
}

fun BottomSheetDialogFragment.makeSnackbar(text: String, duration: Int): Snackbar? {
    dialog?.window?.decorView?.let {
        return Snackbar.make(it, text, duration)
    }

    return null
}