package com.example.board.domain

import com.fasterxml.uuid.Generators
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "likes")
class Like(
    @Id
    val id: UUID = Generators.timeBasedEpochRandomGenerator().generate(),
    post: Post,
    createdBy: String,
) : BaseEntity(createdBy = createdBy) {
    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        private set
}
