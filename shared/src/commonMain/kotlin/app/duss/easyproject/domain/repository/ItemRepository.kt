package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.params.ItemRequest

interface ItemRepository {

    suspend fun getAll(page: Int): Result<List<Item>>

    suspend fun getById(id: Long): Result<Item>

    suspend fun getNew(): Result<Item>

    suspend fun validate(param: ItemRequest): Result<Boolean>

    suspend fun create(param: ItemRequest): Result<Item>

    suspend fun update(param: ItemRequest): Result<Item>

    suspend fun delete(id: Long): Result<Boolean>

}