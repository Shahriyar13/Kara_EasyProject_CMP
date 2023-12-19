package app.duss.easyproject.data

import app.duss.easyproject.data.network.dto.UserLoginDto
import app.duss.easyproject.domain.entity.RegionCity
import app.duss.easyproject.domain.entity.RegionCountry
import app.duss.easyproject.domain.entity.RegionState
import app.duss.easyproject.domain.entity.User
import app.duss.easyproject.domain.enums.Role
import app.duss.easyproject.domain.enums.Title
import appdusseasyproject.RegionCityEntity
import appdusseasyproject.RegionCountryEntity
import appdusseasyproject.RegionStateEntity
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

fun RegionCity.mapToDatabaseEntity() = RegionCityEntity(
    id = id!!,
    name = name,
    stateId = state!!.id!!,
)

fun RegionCityEntity.mapToDomainEntity() = RegionCity(
    id = id,
    name = name,
    state = null,
)

fun RegionState.mapToDatabaseEntity() = RegionStateEntity(
    id = id!!,
    name = name,
    countryId = country!!.id!!
)

fun RegionStateEntity.mapToDomainEntity(cities: List<RegionCityEntity>? = null) = RegionState(
    id = id,
    name = name,
    country = null,
    cities = cities?.map { it.mapToDomainEntity() }
)

fun RegionCountry.mapToDatabaseEntity() = RegionCountryEntity(
    id = id!!,
    name = name,
    code = code,
    region = region,
    subregion = subregion,
    phoneCode = phoneCode,
    nationality = nationality,
    emoji = emoji,
    emojiU = emojiU,
)

fun RegionCountryEntity.mapToDomainEntity(states: List<RegionStateEntity>? = null) = RegionCountry(
    id = id,
    name = name,
    code = code,
    region = region,
    subregion = subregion,
    phoneCode = phoneCode,
    nationality = nationality,
    emoji = emoji,
    emojiU = emojiU,
    states = states?.map { it.mapToDomainEntity() }
)