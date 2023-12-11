package app.duss.easyproject.domain.usecase.attachment

import app.duss.easyproject.domain.entity.FileAttachment
import app.duss.easyproject.domain.params.FileAttachmentRequest
import app.duss.easyproject.domain.repository.AttachmentRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class AttachmentUploadUseCase(
    private val attachmentRepository: AttachmentRepository,
): BaseCoroutinesUseCase<FileAttachmentRequest, List<FileAttachment>>() {
    override suspend fun execute(param: FileAttachmentRequest): Result<List<FileAttachment>> =
        attachmentRepository.upload(param)

}