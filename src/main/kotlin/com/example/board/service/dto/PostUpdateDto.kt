package com.example.board.service.dto

data class PostUpdateDto(
    val title: String,
    val content: String,
    val updatedBy: String,
    val tags: List<String> = emptyList(),
)
