package com.example.board.domain

import com.example.board.exception.PostNotUpdatableException
import com.example.board.service.dto.PostUpdateDto
import com.fasterxml.uuid.Generators
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Post(
    @Id
    val id: UUID = Generators.timeBasedEpochRandomGenerator().generate(),
    title: String,
    content: String,
    createdBy: String,
) : BaseEntity(createdBy = createdBy) {
    var title: String = title
        private set

    var content: String = content
        private set

    fun update(updateDto: PostUpdateDto) {
        if (updateDto.updatedBy != this.createdBy) {
            throw PostNotUpdatableException()
        }
        this.title = updateDto.title
        this.content = updateDto.content
        super.updatedBy(updateDto.updatedBy)
    }
}
