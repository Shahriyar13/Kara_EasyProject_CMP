package app.duss.easyproject.data

import app.duss.easyproject.core.model.Pokemon
import app.duss.easyproject.core.model.PokemonInfo
import appdusseasyproject.PokemonEntity
import appdusseasyproject.PokemonInfoEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Pokemon.toPokemonEntity(page: Long) = PokemonEntity(
    page = page,
    name = name,
    url = url
)

fun PokemonEntity.toPokemon() = Pokemon(
    page = page,
    name = name,
    url = url
)

fun PokemonInfo.toPokemonInfoEntity() = PokemonInfoEntity(
    id = id,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
    types = Json.encodeToString(types),
    stats = Json.encodeToString(stats),
    isFavorite = if (isFavorite) 1 else 0
)

fun PokemonInfoEntity.toPokemonInfo() = PokemonInfo(
    id = id,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
    types = Json.decodeFromString(types),
    stats = Json.decodeFromString(stats),
    isFavorite = isFavorite != 0L
)

fun PokemonInfoEntity.toPokemon() = Pokemon(
    name = name,
    url = "$id/"
)