package com.example.hungrybaby.ui.shared

import androidx.annotation.StringRes
import com.example.hungrybaby.R

enum class ScreenNames(
    @StringRes val title: Int,
) {
    Start(title = R.string.app_name),
    Register(title = R.string.register_page),
    RegisterFirst(title = R.string.register_first_page),
    About(title = R.string.about_page),
    Settings(title = R.string.settings_page),
    News(title = R.string.news_page),
}
