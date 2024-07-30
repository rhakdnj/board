package com.example.board.domain

import com.example.board.exception.CommentNotUpdatableException
import com.example.board.service.dto.CommentUpdateRequestDto
import com.fasterxml.uuid.Generators
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
class Comment(
    @Id
    val id: UUID = Generators.timeBasedEpochRandomGenerator().generate(),
    post: Post,
    content: String,
    createdBy: String,
) : BaseEntity(createdBy = createdBy) {
    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    var post = post
        private set

    fun update(requestDto: CommentUpdateRequestDto) {
        if (requestDto.updatedBy != this.createdBy) {
            throw CommentNotUpdatableException()
        }
        this.content = requestDto.content
        super.updatedBy(requestDto.updatedBy)
    }
}
