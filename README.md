StateAwareDialogFragment
====

[![Download](https://api.bintray.com/packages/kazakago/maven/stateawaredialogfragment/images/download.svg)](https://bintray.com/kazakago/maven/stateawaredialogfragment/_latestVersion)
[![Build Status](https://www.bitrise.io/app/098c9dcea98343e4/status.svg?token=eZd2CxtpCemKoGW-mRZ7cw)](https://www.bitrise.io/app/098c9dcea98343e4)
[![license](https://img.shields.io/github/license/kazakago/stateawaredialogfragment.svg)](LICENSE.md)

Wrapper class of Android's DialogFragment that can automatically restore the state of callback listener after Activity recreation.

## Requirement

- Android 4.0.3 (API 15) or later

## Install

Add the following gradle dependency exchanging x.x.x for the latest release.

```groovy
implementation 'com.kazakago.stateawaredialogfragment:stateawaredialogfragment:x.x.x'
```

## Usage

implement `StateAwareDialogFragment` instead of `DialogFragment`  
and specify the callback listener as the generics for `StateAwareDialogFragment`.  

You can access the listener's entity from `StateAwareDialogFragment.callbackListener`.  

```kotlin
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
        return AlertDialog.Builder(activity!!)
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
```

Dialog's callback listener **MUST** be implemented in **Activity or Fragment**.  

```kotlin
class MainActivity : AppCompatActivity(), NoticeDialog.DialogCallbackListener {

    ...

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
```

## Advanced

By default, implementation other than Activity or Fragment will crash at run time.  
The following code can be controlled so as not to crash at run time, but the listener does not restore at Activity recreation.  
```kotlin
class NoticeDialog : StateAwareDialogFragment<NoticeDialog.DialogCallbackListener>() {

    override var connectableUnSupportedClass = false
    
}
```
## License

MIT License

Copyright (c) 2018 KazaKago

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
