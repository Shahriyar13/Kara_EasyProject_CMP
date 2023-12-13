package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.RegionClient
import app.duss.easyproject.domain.entity.RegionCity
import app.duss.easyproject.domain.entity.RegionCountry
import app.duss.easyproject.domain.entity.RegionCustomsPort
import app.duss.easyproject.domain.entity.RegionState
import app.duss.easyproject.domain.repository.RegionRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegionRepositoryImpl : RegionRepository, KoinComponent {

    private val client by inject<RegionClient>()

    override suspend fun getAllCities(query: String?, page: Int?): Result<List<RegionCity>> {
        return try {
            val response = client.getAllCities(query = query, page = page ?: -1)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getCityById(id: Long): Result<RegionCity> {
        return try {
            val response = client.getCityById(id = id)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getAllStates(query: String?, page: Int?): Result<List<RegionState>> {
        return try {
            val response = client.getAllStates(query = query, page = page ?: -1)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getStateById(id: Long): Result<RegionState> {
        return try {
            val response = client.getStateById(id = id)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getAllCountries(query: String?, page: Int?): Result<List<RegionCountry>> {
        return try {
            val response = client.getAllCountries(query = query, page = page ?: -1)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getCountryById(id: Long): Result<RegionCountry> {
        return try {
            val response = client.getCountryById(id = id)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getAllCustomsPorts(query: String?, page: Int?): Result<List<RegionCustomsPort>> {
        return try {
            val response = client.getAllCustomsPorts(query = query, page = page ?: -1)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getCustomsPortById(id: Long): Result<RegionCustomsPort> {
        return try {
            val response = client.getCustomsPortById(id = id)
            if (response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}