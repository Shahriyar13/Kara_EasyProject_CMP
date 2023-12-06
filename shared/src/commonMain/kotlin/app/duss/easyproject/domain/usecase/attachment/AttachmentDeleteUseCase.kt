package app.duss.easyproject.domain.usecase.attachment

import app.duss.easyproject.domain.repository.AttachmentRepository
import app.duss.easyproject.domain.usecase.BaseCoroutinesUseCase

class AttachmentDeleteUseCase(
    private val attachmentRepository: AttachmentRepository,
): BaseCoroutinesUseCase<Long, Boolean>() {
    override suspend fun execute(param: Long): Result<Boolean> =
        attachmentRepository.delete(param)

}