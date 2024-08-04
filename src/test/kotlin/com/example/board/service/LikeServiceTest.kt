package com.example.board.service

import com.example.board.domain.Post
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.LikeRepository
import com.example.board.repository.PostRepository
import com.fasterxml.uuid.Generators
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class LikeServiceTest(
    private val likeService: LikeService,
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
        given("좋아요 생성시") {
            val saved =
                postRepository
                    .save(
                        Post(
                            title = "title",
                            content = "content",
                            createdBy = "createdBy",
                        ),
                    )
            When("인풋이 정상적으로 들어오면") {
                val likeId = likeService.createLike(postId = saved.id, createdBy = "createdBy")
                then("좋아요가 정상적으로 생성됨을 확인한다.") {
                    val like = likeRepository.findByIdOrNull(likeId)
                    like shouldNotBe null
                    like?.createdBy shouldBe "createdBy"
                }
            }
            When("게시글이 존재하지 않으면") {
                then("존재하지 않은 게시글 예외가 발생한다.") {
                    shouldThrow<PostNotFoundException> {
                        likeService.createLike(
                            postId = Generators.timeBasedEpochRandomGenerator().generate(),
                            createdBy = "createdBy",
                        )
                    }
                }
            }
        }
    })
