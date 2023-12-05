package app.duss.easyproject.domain.usecase

abstract class BaseCoroutinesUseCase<PARAM, RESULT>() {
    abstract suspend fun execute(param: PARAM): Result<RESULT>

}