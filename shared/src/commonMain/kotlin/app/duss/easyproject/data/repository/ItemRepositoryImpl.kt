package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.ItemClient
import app.duss.easyproject.domain.entity.Item
import app.duss.easyproject.domain.params.ItemRequest
import app.duss.easyproject.domain.repository.ItemRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ItemRepositoryImpl : ItemRepository, KoinComponent {

    private val client by inject<ItemClient>()

    override suspend fun getAll(query: String?, page: Int): Result<List<Item>> {
        return try {
            val response = client.getAll(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
    override suspend fun getById(id: Long): Result<Item> {
        return try {
            val response = client.getById(id = id)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun create(param: ItemRequest): Result<Item> {
        return try {
            val response = client.create(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Creating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(param: ItemRequest): Result<Item> {
        return try {
            val response = client.update(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Updating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(id: Long): Result<Boolean> {
        return try {
            val response = client.deleteById(id = id)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}