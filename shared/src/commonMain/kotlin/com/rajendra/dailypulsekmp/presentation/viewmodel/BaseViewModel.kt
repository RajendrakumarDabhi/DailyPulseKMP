package com.rajendra.dailypulsekmp.presentation.viewmodel

import kotlinx.coroutines.CoroutineScope

expect open class BaseViewModel() {
    val scope: CoroutineScope
}