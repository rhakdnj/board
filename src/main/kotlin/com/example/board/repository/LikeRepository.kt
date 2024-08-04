package com.example.board.repository

import com.example.board.domain.Like
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LikeRepository : JpaRepository<Like, UUID>
