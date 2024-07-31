package com.example.board.domain

import com.fasterxml.uuid.Generators
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
class Tag(
    @Id
    val id: UUID = Generators.timeBasedEpochRandomGenerator().generate(),
    post: Post,
    name: String,
    createdBy: String,
) : BaseEntity(createdBy = createdBy) {
    @ManyToOne(fetch = FetchType.LAZY)
    var post = post
        private set

    var name = name
        private set
}
