package com.kazakago.stateawaredialogfragment.sample

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.kazakago.stateawaredialogfragment.StateAwareDialogFragment

class NoticeDialog : StateAwareDialogFragment<NoticeDialog.DialogCallbackListener>() {

    interface DialogCallbackListener {
        fun onPositiveButtonClicked(tag: String?)
        fun onNegativeButtonClicked(tag: String?)
    }

    companion object {
        fun createInstance(): NoticeDialog {
            return NoticeDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
                .setTitle("Notice!")
                .setMessage("you can rotate device & callback button.")
                .setPositiveButton("OK") { _, _ ->
                    callbackListener?.onPositiveButtonClicked(tag)
                }
                .setNegativeButton("Cancel") { _, _ ->
                    callbackListener?.onNegativeButtonClicked(tag)
                }
                .create()
    }

}
