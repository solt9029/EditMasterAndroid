package com.solt9029.editmasterandroid.entity

data class ValidationErrorBody(
        val message: String?,
        val errors: Map<String, List<String>>?
)
