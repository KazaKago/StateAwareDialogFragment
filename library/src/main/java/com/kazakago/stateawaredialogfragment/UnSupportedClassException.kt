package com.kazakago.stateawaredialogfragment

class UnSupportedClassException(message: String = "DialogListener must be implemented in Activity/Fragment.") : ClassCastException(message)