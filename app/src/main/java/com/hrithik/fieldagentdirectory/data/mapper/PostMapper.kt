package com.hrithik.fieldagentdirectory.data.mapper


import com.hrithik.fieldagentdirectory.data.local.entity.PostEntity
import com.hrithik.fieldagentdirectory.data.remote.PostDto

fun PostDto.toEntity(): PostEntity = PostEntity(
    id = id,
    userId = userId,
    title = title,
    body = body
)
