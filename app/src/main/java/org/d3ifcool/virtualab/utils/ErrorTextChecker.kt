package org.d3ifcool.virtualab.utils

fun errorTextCheck(errorMessage: String?, customMessage: String): String {
    return when (errorMessage) {
        GenericMessage.applicationError -> "Gagal memuat data."
        GenericMessage.noInternetError -> "Gagal memuat data."
        else -> customMessage
    }
}