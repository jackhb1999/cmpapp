package com.hb.annotation

@Target(AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY)
annotation class Comment(val value: String)