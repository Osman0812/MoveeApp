package com.example.myapplication.util


import java.text.SimpleDateFormat
import java.util.Locale


object DataTransformer {
    inline fun <T, R> transformData(source: T, transformFunction: (T) -> R): R {
        return transformFunction(source)
    }
    fun concatBornStatus(birthDate: String? = null, placeOfBirth: String? = null): String {
        val formattedBornDate = birthDate?.let { transformDateFormat(it) }
        return if (formattedBornDate != "" && placeOfBirth != "")
            "$formattedBornDate, $placeOfBirth."
        else
            ""
    }
    private fun transformDateFormat(birthDate: String): Any {
        return if (birthDate.isNotBlank()) {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val outputDateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
            val date = inputDateFormat.parse(birthDate)

            if (date != null) {
                outputDateFormat.format(date)
            } else {
                ""
            }
        } else {
            ""
        }
    }
}