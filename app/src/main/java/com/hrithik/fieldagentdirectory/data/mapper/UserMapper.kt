package com.hrithik.fieldagentdirectory.data.mapper

import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity
import com.hrithik.fieldagentdirectory.data.remote.UserDto

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        image = image
    )
}
