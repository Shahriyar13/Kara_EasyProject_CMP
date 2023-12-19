package app.duss.easyproject.data.repository

import app.duss.easyproject.data.database.dao.RegionCityDao
import app.duss.easyproject.data.database.dao.RegionCountryDao
import app.duss.easyproject.data.database.dao.RegionStateDao
import app.duss.easyproject.data.mapToDatabaseEntity
import app.duss.easyproject.data.mapToDomainEntity
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

    private val cityDao by inject<RegionCityDao>()
    private val stateDao by inject<RegionStateDao>()
    private val countryDao by inject<RegionCountryDao>()

    override suspend fun getAllCities(query: String?, page: Int?): Result<List<RegionCity>> {
        return try {
            var cities = cityDao.getAll().map { it.mapToDomainEntity() }
            if (cities.isEmpty()) {
                val response = client.getAllCities(query = query, page = page ?: -1)
                response.data?.forEach {
                    cityDao.set(it.mapToDatabaseEntity())
                }
                cities = cityDao.getAll().map { it.mapToDomainEntity() }
            }
            Result.success(cities)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getCityById(id: Long): Result<RegionCity> {
        return try {
            var city = cityDao.getById(id)
            if (city == null) {
                val response = client.getCityById(id = id)
                if (response.data != null) {
                    cityDao.set(response.data.mapToDatabaseEntity())
                }
                city = cityDao.getById(id)
            }
            if (city != null) {
                Result.success(city.mapToDomainEntity())
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllStates(query: String?, page: Int?): Result<List<RegionState>> {
        return try {
            var states = stateDao.getAll().map { it.mapToDomainEntity() }
            if (states.isEmpty()) {
                val response = client.getAllStates(query = query, page = page ?: -1)
                response.data?.forEach {
                    stateDao.set(it.mapToDatabaseEntity())
                }
                states = stateDao.getAll().map { it.mapToDomainEntity() }
            }
            Result.success(states)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getStateById(id: Long): Result<RegionState> {
        return try {
            var state = stateDao.getById(id)
            if (state == null) {
                val response = client.getStateById(id = id)
                if (response.data != null) {
                    stateDao.set(response.data.mapToDatabaseEntity())
                }
                state = stateDao.getById(id)
            }
            if (state != null) {
                Result.success(state.mapToDomainEntity())
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllCountries(query: String?, page: Int?): Result<List<RegionCountry>> {
        return try {
            var countries = countryDao.getAll().map { it.mapToDomainEntity() }
            if (countries.isEmpty()) {
                val response = client.getAllCountries(query = query, page = page ?: -1)
                response.data?.forEach {
                    countryDao.set(it.mapToDatabaseEntity())
                }
                countries = countryDao.getAll().map { it.mapToDomainEntity() }
            }
            Result.success(countries)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getCountryById(id: Long): Result<RegionCountry> {
        return try {
            var country = countryDao.getById(id)
            if (country == null) {
                val response = client.getCountryById(id = id)
                if (response.data != null) {
                    countryDao.set(response.data.mapToDatabaseEntity())
                }
                country = countryDao.getById(id)
            }
            if (country != null) {
                Result.success(country.mapToDomainEntity())
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllCustomsPorts(
        query: String?,
        page: Int?
    ): Result<List<RegionCustomsPort>> {
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