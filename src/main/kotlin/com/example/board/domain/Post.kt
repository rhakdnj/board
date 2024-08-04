package com.example.board.domain

import com.example.board.exception.PostNotUpdatableException
import com.example.board.service.dto.PostUpdateDto
import com.fasterxml.uuid.Generators
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
class Post(
    @Id
    val id: UUID = Generators.timeBasedEpochRandomGenerator().generate(),
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = [CascadeType.ALL])
    val comments: MutableList<Comment> = mutableListOf(),
    title: String,
    content: String,
    createdBy: String,
    tags: List<String> = emptyList(),
) : BaseEntity(createdBy = createdBy) {
    var title: String = title
        private set

    var content: String = content
        private set

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = [CascadeType.ALL])
    var tags: MutableList<Tag> =
        tags
            .map { Tag(post = this, name = it, createdBy = createdBy) }
            .toMutableList()
        private set

    fun update(updateDto: PostUpdateDto) {
        if (updateDto.updatedBy != this.createdBy) {
            throw PostNotUpdatableException()
        }
        this.title = updateDto.title
        this.content = updateDto.content
        replaceTags(updateDto.tags)
        super.updatedBy(updateDto.updatedBy)
    }

    /**
     * 리스트 비교 연산자
     * 	1.	리스트의 크기 비교: 먼저 두 리스트의 크기를 비교합니다. 크기가 다르면 false를 반환합니다.
     * 	2.	원소 값 비교: 각 원소를 순차적으로 비교합니다. 각 원소가 동일한지 확인합니다. 모든 원소가 같은 위치에 동일한 값을 가지고 있으면 true, 그렇지 않으면 false를 반환합니다.
     */
    private fun replaceTags(tags: List<String>) {
        if (this.tags.map { it.name } != tags) {
            this.tags.clear()
            this.tags.addAll(tags.map { Tag(post = this, name = it, createdBy = this.createdBy) })
        }
    }
}
