package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.PurchaseOrder
import app.duss.easyproject.domain.params.PurchaseOrderRequest

interface PurchaseOrderRepository {

    suspend fun getAll(query: String?, page: Int): Result<List<PurchaseOrder>>

    suspend fun getAllByProjectId(projectId: Long, page: Int): Result<List<PurchaseOrder>>

    suspend fun getById(id: Long): Result<PurchaseOrder>

    suspend fun validateCode(code: String): Result<Boolean>

    suspend fun create(param: PurchaseOrderRequest): Result<PurchaseOrder>

    suspend fun update(param: PurchaseOrderRequest): Result<PurchaseOrder>

    suspend fun delete(id: Long): Result<Boolean>

}