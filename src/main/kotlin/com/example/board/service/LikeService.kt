package com.example.board.service

import com.example.board.domain.Like
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.LikeRepository
import com.example.board.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createLike(
        postId: UUID,
        createdBy: String,
    ): UUID {
        val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()

        return likeRepository
            .save(
                Like(
                    post = post,
                    createdBy = createdBy,
                ),
            ).id
    }
}
