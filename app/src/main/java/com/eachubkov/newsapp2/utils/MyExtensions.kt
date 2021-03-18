package com.eachubkov.newsapp2.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast

fun hasInternetConnection(application: Application): Boolean {
    val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun Context.toast(message: Any?) = Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()

fun View.toast(message: Any?) = Toast.makeText(this.context, message.toString(), Toast.LENGTH_SHORT).show()

fun Application.toast(message: Any?) = Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()

fun View.show() { this.visibility = View.VISIBLE }

fun View.hide() { this.visibility = View.INVISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun TextView.clearTextAndFocus() {
    this.text = null
    this.clearFocus()
    this.context.hideKeyboard(this)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun TextView.addUnderline(text: String) {
    val newText = SpannableString(text)
    newText.setSpan(UnderlineSpan(), 0, text.length, 0)
    this.text = newText
}