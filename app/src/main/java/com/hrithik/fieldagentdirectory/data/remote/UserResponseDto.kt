package com.hrithik.fieldagentdirectory.data.remote

data class UserResponseDto(
    val users: List<UserDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String
)
