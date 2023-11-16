package app.duss.easyproject.utils

import app.duss.easyproject.utils.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val appDispatchers: AppDispatchers = object: AppDispatchers {
    override val main: CoroutineDispatcher = Dispatchers.Main.immediate
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}