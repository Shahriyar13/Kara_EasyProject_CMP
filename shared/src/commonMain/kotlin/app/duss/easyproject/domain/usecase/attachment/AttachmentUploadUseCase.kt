package app.duss.easyproject.domain.usecase.attachment

import app.duss.easyproject.domain.entity.FileAttachment
import app.duss.easyproject.domain.params.FileAttachmentUpdateRequest
import app.duss.easyproject.domain.repository.AttachmentRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class AttachmentUploadUseCase(
    private val attachmentRepository: AttachmentRepository,
): BaseCoroutinesUseCase<FileAttachmentUpdateRequest, List<FileAttachment>>() {
    override suspend fun execute(param: FileAttachmentUpdateRequest): Result<List<FileAttachment>> =
        attachmentRepository.upload(param)

}