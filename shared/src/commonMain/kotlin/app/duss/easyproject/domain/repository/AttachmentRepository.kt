package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.FileAttachment
import app.duss.easyproject.domain.params.FileAttachmentRequest

interface AttachmentRepository {

    suspend fun upload(param: FileAttachmentRequest): Result<List<FileAttachment>>

    suspend fun delete(id: Long): Result<Boolean>

}