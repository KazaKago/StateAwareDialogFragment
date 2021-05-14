package com.kazakago.stateawaredialogfragment

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment

abstract class StateAwareDialogFragment<Interface> : AppCompatDialogFragment() {

    private enum class ListenerTargetType {
        ACTIVITY,
        FRAGMENT,
        OTHER,
        NONE,
    }

    private enum class Key {
        ConnectableUnSupportedClass,
        ListenerTargetType
    }

    open var connectableUnSupportedClass = false

    var callbackListener: Interface? = null
        set(value) {
            field = value
            listenerTargetType = classifyListenerTargetType(value)
        }
    private var listenerTargetType = ListenerTargetType.NONE

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            connectableUnSupportedClass =
                savedInstanceState.getBoolean(Key.ConnectableUnSupportedClass.name)
            listenerTargetType =
                savedInstanceState.getSerializable(Key.ListenerTargetType.name) as ListenerTargetType
            callbackListener = when (listenerTargetType) {
                ListenerTargetType.ACTIVITY -> context as? Interface
                ListenerTargetType.FRAGMENT -> targetFragment as? Interface
                else -> null
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Key.ConnectableUnSupportedClass.name, connectableUnSupportedClass)
        outState.putSerializable(Key.ListenerTargetType.name, listenerTargetType)
    }

    private fun classifyListenerTargetType(value: Interface?): ListenerTargetType {
        return when (value) {
            is Activity -> {
                setTargetFragment(null, 0)
                ListenerTargetType.ACTIVITY
            }
            is Fragment -> {
                setTargetFragment(value, 0)
                ListenerTargetType.FRAGMENT
            }
            null -> {
                setTargetFragment(null, 0)
                ListenerTargetType.NONE
            }
            else -> {
                if (!connectableUnSupportedClass) throw UnSupportedClassException()
                setTargetFragment(null, 0)
                ListenerTargetType.OTHER
            }
        }
    }

}
