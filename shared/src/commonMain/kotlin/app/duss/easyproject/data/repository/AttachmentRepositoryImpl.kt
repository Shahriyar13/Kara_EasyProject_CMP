package app.duss.easyproject.data.repository

import app.duss.easyproject.data.network.client.AttachmentClient
import app.duss.easyproject.domain.entity.FileAttachment
import app.duss.easyproject.domain.params.FileAttachmentRequest
import app.duss.easyproject.domain.repository.AttachmentRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AttachmentRepositoryImpl: AttachmentRepository, KoinComponent {

    private val client by inject<AttachmentClient>()
//    private val pokemonInfoDao by inject<PokemonInfoDao>()

    override suspend fun upload(param: FileAttachmentRequest): Result<List<FileAttachment>> {
        return try {
                val response = client.upload(param)
                if (response.data != null) {
                    Result.success(response.data)
                } else {
                    Result.failure(Exception("Not found"))
                }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun delete(id: Long): Result<Boolean> {
        return try {
            val response = client.delete(id = id)
            Result.success(response.data ?: false)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}