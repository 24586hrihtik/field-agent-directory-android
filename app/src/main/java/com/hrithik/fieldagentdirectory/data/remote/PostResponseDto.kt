package com.hrithik.fieldagentdirectory.data.remote

data class PostResponseDto(
    val posts: List<PostDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)