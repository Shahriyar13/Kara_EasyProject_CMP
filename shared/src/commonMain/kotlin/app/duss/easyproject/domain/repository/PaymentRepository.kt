//package app.duss.easyproject.domain.repository
//
//import app.duss.easyproject.domain.entity.Person
//import app.duss.easyproject.domain.params.PersonRequest
//
//interface PaymentRepository {
//
//    suspend fun getAll(page: Int): Result<List<Payment>>
//
//    suspend fun getById(id: Long): Result<Payment>
//
//    suspend fun getNew(): Result<Payment>
//
//    suspend fun validate(param: PaymentRequest): Result<Boolean>
//
//    suspend fun create(param: PaymentRequest): Result<Payment>
//
//    suspend fun update(param: PaymentRequest): Result<Payment>
//
//    suspend fun delete(id: Long): Result<Boolean>
//
//}