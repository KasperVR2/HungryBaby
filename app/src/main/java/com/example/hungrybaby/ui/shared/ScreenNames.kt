package com.example.hungrybaby.ui.shared

import androidx.annotation.StringRes
import com.example.hungrybaby.R

enum class ScreenNames(
    @StringRes val title: Int,
) {
    Start(title = R.string.app_name),
    Register(title = R.string.register_page),
}
