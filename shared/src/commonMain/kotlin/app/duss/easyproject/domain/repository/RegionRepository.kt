package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.RegionCity
import app.duss.easyproject.domain.entity.RegionCountry
import app.duss.easyproject.domain.entity.RegionCustomsPort
import app.duss.easyproject.domain.entity.RegionState
import app.duss.easyproject.domain.entity.SupplierEnquiry
import app.duss.easyproject.domain.params.SupplierEnquiryRequest

interface RegionRepository {
    suspend fun getAllCountries(query: String?page: Int?): Result<List<RegionCountry>>
    suspend fun getAllStates(query: String?page: Int?): Result<List<RegionState>>
    suspend fun getAllCities(query: String?page: Int?): Result<List<RegionCity>>
    suspend fun getAllCustomsPorts(query: String?, page: Int?): Result<List<RegionCustomsPort>>
    suspend fun getCountryById(id: Long): Result<RegionCountry>
    suspend fun getStateById(id: Long): Result<RegionState>
    suspend fun getCityById(id: Long): Result<RegionCity>
    suspend fun getCustomsPortById(id: Long): Result<RegionCustomsPort>

}