package app.duss.easyproject.data

import app.duss.easyproject.data.network.dto.UserLoginDto
import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.enums.Role
import app.duss.easyproject.domain.enums.Title
import appdusseasyproject.UserEntity

fun User.mapToDatabaseEntity(token: String) = UserEntity(
    id = id ?: -1,
    username = username,
    token = token,
    title = title?.name,
    firstName = firstName,
    lastName = lastName,
    jobTitle = jobTitle,
    email = email,
    telephone = telephone,
    mobile = mobile,
    role = role.name,
    creationTime = creationTime,
    modificationTime = modificationTime,
    createdBy = createdBy,
    modifiedBy = modifiedBy,
    creatorId = creatorId,
    modifierId = modifierId,
)

fun UserLoginDto.mapToDatabaseEntity() = user.mapToDatabaseEntity(token)

fun UserEntity.mapToDatabaseEntity() = UserLoginDto(
    user = this.mapToDomainEntity(),
    token = this.token
)

fun UserEntity.mapToDomainEntity() = User(
    id = id,
    username = username,
    title = Title.valueOf(title ?: Title.Unknown.name),
    firstName = firstName,
    lastName = lastName,
    jobTitle = jobTitle,
    email = email,
    telephone = telephone,
    mobile = mobile,
    role = Role.valueOf(role),
    creationTime = creationTime,
    modificationTime = modificationTime,
    createdBy = createdBy,
    modifiedBy = modifiedBy,
    creatorId = creatorId,
    modifierId = modifierId,
)