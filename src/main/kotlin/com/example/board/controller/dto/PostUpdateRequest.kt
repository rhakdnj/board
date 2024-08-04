package com.example.board.controller.dto

import com.example.board.service.dto.PostUpdateDto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
    val tags: List<String> = emptyList(),
)

fun PostUpdateRequest.toDto() =
    PostUpdateDto(
        title = title,
        content = content,
        updatedBy = updatedBy,
        tags = tags,
    )
