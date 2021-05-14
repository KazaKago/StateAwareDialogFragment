package com.kazakago.stateawaredialogfragment.sample

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), NoticeDialog.DialogCallbackListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showDialogButton = findViewById<Button>(R.id.showDialogButton)
        showDialogButton.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialog = NoticeDialog.createInstance()
        dialog.callbackListener = this
        dialog.show(supportFragmentManager, "")
    }

    /* NoticeDialog.DialogCallbackListener */

    override fun onPositiveButtonClicked(tag: String?) {
        Toast.makeText(this, "onPositiveButtonClicked", Toast.LENGTH_SHORT).show()
    }

    override fun onNegativeButtonClicked(tag: String?) {
        Toast.makeText(this, "onNegativeButtonClicked", Toast.LENGTH_SHORT).show()
    }

}
