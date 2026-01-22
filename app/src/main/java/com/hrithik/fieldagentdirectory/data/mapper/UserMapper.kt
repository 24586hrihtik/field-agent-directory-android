package com.hrithik.fieldagentdirectory.data.mapper

import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity
import com.hrithik.fieldagentdirectory.data.remote.UserDto

fun UserDto.toEntity() = UserEntity(id, firstName, lastName, email)
