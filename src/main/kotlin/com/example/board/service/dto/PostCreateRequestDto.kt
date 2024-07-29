package com.example.board.service.dto

import com.example.board.domain.Post

data class PostCreateDto(
    val title: String,
    val content: String,
    val createdBy: String,
)

fun PostCreateDto.toEntity() =
    Post(
        title = title,
        content = content,
        createdBy = createdBy,
    )
