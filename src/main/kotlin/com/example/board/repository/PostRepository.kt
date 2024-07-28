package com.example.board.repository

import com.example.board.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PostRepository : JpaRepository<Post, UUID>
