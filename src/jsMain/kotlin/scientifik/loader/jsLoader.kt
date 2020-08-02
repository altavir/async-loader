package scientifik.loader

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlin.js.Promise


/**
 * Load module using [ModuleLoader] and return a reference to it. Works only with modern browsers
 */
@JsExport
fun loadModule(id: String, vararg paths: String): Promise<dynamic> {
    return GlobalScope.async { LoaderManager.load(id, ModuleLoader, *paths) }.asPromise()
}

/**
 * Load a resource using fetch-inject. The resource is embedded and should be called directly, no via returned reference
 */
@JsExport
fun fetchModule(id: String, vararg paths: String): Promise<dynamic>{
    return GlobalScope.async { LoaderManager.load(id, FetchInjectLoader, *paths)}.asPromise()
}