package com.example.board.controller.dto

import com.example.board.service.dto.CommentUpdateRequestDto

data class CommentUpdateRequest(
    val content: String,
    val updatedBy: String,
)

fun CommentUpdateRequest.toDto() = CommentUpdateRequestDto(content = content, updatedBy = updatedBy)
