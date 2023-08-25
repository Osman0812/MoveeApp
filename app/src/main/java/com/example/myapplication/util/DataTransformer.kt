package com.example.myapplication.util


object DataTransformer {
    inline fun <T, R> transformData(source: T, transformFunction: (T) -> R): R {
        return transformFunction(source)
    }
}