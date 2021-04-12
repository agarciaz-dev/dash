package com.eelseth.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val coverImageUrl: String,
    val nextCloseTimestamp: Long?,
    val nextOpenTimestamp: Long?,
)