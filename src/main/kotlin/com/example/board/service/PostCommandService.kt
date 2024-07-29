package com.example.board.service

import com.example.board.exception.PostNotDeletableException
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostCreateDto
import com.example.board.service.dto.PostUpdateDto
import com.example.board.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class PostCommandService(
    private val postRepository: PostRepository,
) {
    fun createPost(createDto: PostCreateDto): UUID = postRepository.save(createDto.toEntity()).id

    fun updatePost(
        id: UUID,
        updateDto: PostUpdateDto,
    ): UUID {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        post.update(updateDto)
        return id
    }

    fun deletePost(
        id: UUID,
        deletedBy: String,
    ): UUID {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        if (post.createdBy != deletedBy) {
            throw PostNotDeletableException()
        }
        postRepository.delete(post)
        return id
    }
}
