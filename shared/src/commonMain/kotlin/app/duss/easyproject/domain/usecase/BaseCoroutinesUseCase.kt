package app.duss.easyproject.domain.usecase

abstract class BaseCoroutinesUseCase<PARAM, RESULT> {
    abstract suspend fun execute(param: PARAM): Result<RESULT>

}
abstract class BaseCoroutinesUseCaseMultiParam<PARAM1, PARAM2, RESULT> {
    abstract suspend fun execute(param1: PARAM1, param2: PARAM2): Result<RESULT>

}