package com.example.board.controller.dto

import com.example.board.service.dto.PostCreateDto

data class PostCreateRequest(
    val title: String,
    val content: String,
    val createdBy: String,
)

fun PostCreateRequest.toDto() =
    PostCreateDto(
        title = title,
        content = content,
        createdBy = createdBy,
    )
