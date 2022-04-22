package com.mj.booksearchapp.util

import java.text.NumberFormat

val Int.commaString: String
    get() = NumberFormat.getInstance().format(this)