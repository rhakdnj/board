package com.example.board.service

import com.example.board.exception.CommentNotDeletableException
import com.example.board.exception.CommentNotFoundException
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.CommentRepository
import com.example.board.repository.PostRepository
import com.example.board.service.dto.CommentCreateRequestDto
import com.example.board.service.dto.CommentUpdateRequestDto
import com.example.board.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createComment(
        postId: UUID,
        requestDto: CommentCreateRequestDto,
    ): UUID {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
        return commentRepository.save(requestDto.toEntity(post)).id
    }

    @Transactional
    fun updateComment(
        id: UUID,
        requestDto: CommentUpdateRequestDto,
    ): UUID {
        val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
        comment.update(requestDto)
        return comment.id
    }

    @Transactional
    fun deleteComment(
        id: UUID,
        deletedBy: String,
    ): UUID {
        val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
        if (comment.createdBy != deletedBy) {
            throw CommentNotDeletableException()
        }
        commentRepository.delete(comment)
        return id
    }
}
