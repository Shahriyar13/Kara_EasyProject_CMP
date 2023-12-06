package app.duss.easyproject.domain.repository

import app.duss.easyproject.domain.entity.FileAttachment
import app.duss.easyproject.domain.params.FileAttachmentUpdateRequest

interface AttachmentRepository {

    suspend fun upload(param: FileAttachmentUpdateRequest): Result<List<FileAttachment>>

    suspend fun delete(id: Long): Result<Boolean>

}