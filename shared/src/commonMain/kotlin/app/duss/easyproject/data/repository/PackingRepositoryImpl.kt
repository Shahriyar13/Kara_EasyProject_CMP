package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.PackingClient
import app.duss.easyproject.domain.entity.Packing
import app.duss.easyproject.domain.params.PackingRequest
import app.duss.easyproject.domain.repository.PackingRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PackingRepositoryImpl : PackingRepository, KoinComponent {

    private val client by inject<PackingClient>()

    override suspend fun getAll(query: String?, page: Int): Result<List<Packing>> {
        return try {
            val response = client.getAll(query = query, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
//
//        val qItems = listOf(
//            QuotationItem(
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        name = "mock item 1"
//                    )
//                ),
//                price = 23.00,
//                quantity = 100
//            ),
//            QuotationItem(
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        name = "mock item 2"
//                    )
//                ),
//                price = 20.00,
//                quantity = 50
//            ),
//            QuotationItem(
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        name = "mock item 3"
//                    )
//                ),
//                price = 200.00,
//                quantity = 20
//            ),
//            QuotationItem(
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        name = "mock item 4"
//                    )
//                ),
//                price = 250.00,
//                quantity = 20
//            )
//        )
//        val mockBoxes = listOf(
//            BoxOfItem(
//                boxItems = listOf(
//                    BoxItem(
//                        quotationItem = qItems[0],
//                        quantity = 50,
//                    ),
//                    BoxItem(
//                        quotationItem = qItems[1],
//                        quantity = 50,
//                    )
//                )
//            )
//        )
//        return Result.success(listOf(
//            Packing(
//                id = 1,
//                quotationItems = qItems,
//                boxes = mockBoxes,
//                containers = listOf()
//            ),
//        ))
    }

    override suspend fun getAllByProjectId(
        projectId: Long,
        page: Int
    ): Result<List<Packing>> {
        return try {
            val response = client.getAllByProjectId(projectId = projectId, page = page)
            Result.success(response.data ?: emptyList())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun validateCode(code: String): Result<Boolean> {
        return try {
            val response = client.validateCode(code = code)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getById(id: Long): Result<Packing> {
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

//        val qItems = listOf(
//            QuotationItem(
//                id = 1,
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        id = 1,
//                        name = "mock item 1"
//                    )
//                ),
//                price = 23.00,
//                quantity = 100
//            ),
//            QuotationItem(
//                id = 2,
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        id = 2,
//                        name = "mock item 2"
//                    )
//                ),
//                price = 20.00,
//                quantity = 50
//            ),
//            QuotationItem(
//                id = 3,
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        id = 3,
//                        name = "mock item 3"
//                    )
//                ),
//                price = 200.00,
//                quantity = 20
//            ),
//            QuotationItem(
//                id = 4,
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        id = 4,
//                        name = "mock item 4"
//                    )
//                ),
//                price = 250.00,
//                quantity = 20
//            ),
//            QuotationItem(
//                id = 5,
//                customerEnquiryItem = CustomerEnquiryItem(
//                    Item(
//                        id = 5,
//                        name = "mock item 5"
//                    )
//                ),
//                price = 250.00,
//                quantity = 50
//            ),
//        )
//        val mockBoxes = listOf(
//            BoxOfItem(
//                id = 1,
//                boxItems = listOf(
//                    BoxItem(
//                        id = 1,
//                        quotationItem = qItems[3],
//                        quantity = 20,
//                    ),
//                    BoxItem(
//                        id = 2,
//                        quotationItem = qItems[4],
//                        quantity = 50,
//                    )
//                )
//            )
//        )
//        return Result.success(
//            Packing(
//                id = 1,
//                quotationItems = qItems,
//                boxes = mockBoxes,
//                containers = listOf()
//            ),
//        )
    }

    override suspend fun create(param: PackingRequest): Result<Packing> {
        return try {
            val response = client.create(params = param)
            response.data?.let { Result.success(it) }
                ?: Result.failure(Exception("Error in Creating"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun update(param: PackingRequest): Result<Packing> {
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