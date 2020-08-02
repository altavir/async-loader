package scientifik.loader

import kotlinx.coroutines.await
import kotlin.js.Promise

/**
 * Available only on modern browsers
 */
object ModuleLoader : Loader<Any> {
    override suspend fun load(path: String): Any {
        //workaround for DCE bug
        return (eval("import(path)") as Promise<Any>).await()
    }
}

