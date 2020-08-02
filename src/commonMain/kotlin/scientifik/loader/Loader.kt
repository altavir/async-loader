package scientifik.loader

import kotlinx.coroutines.*
import mu.KLogger
import mu.KotlinLogging

/**
 * The loader is a build unit for asynchronous loader composition
 */
interface Loader<out T : Any> {
    suspend fun load(path: String): T
}

class MultiLoader<T : Any>(private val scope: CoroutineScope = GlobalScope) {

    private val logger: KLogger = KotlinLogging.logger(this.toString())

    private val loadJobs = HashMap<String, CompletableDeferred<T>>()

    private suspend fun startLoadJob(
        deferred: CompletableDeferred<T>,
        paths: Collection<Pair<String, Loader<T>>>
    ): Job {
        return scope.launch {
            paths.forEach { (path, loader) ->
                if (deferred.isActive) {
                    try {
                        val res = loader.load(path)
                        deferred.complete(res)
                    } catch (ex: Exception) {
                        logger.warn { "Failed to load data from $path with $loader: ${ex.message}" }
                    }
                }
            }
        }
    }

    /**
     * Load an object with given id an collection of paths. Only first successful load is used and then stored by id.
     * This method could be safely called multiple times, if load succeeded, then subsequent operations are ignored.
     */
    suspend fun load(id: String, paths: Collection<Pair<String, Loader<T>>>): T {
        val deferred = loadJobs.getOrPut(id) { CompletableDeferred() }

        startLoadJob(deferred, paths)

        return deferred.await()
    }

    /**
     * Remove loaded item from cache. All objects currently using the reference, keep using it. All new request must load it first
     */
    fun unload(id: String) {
        loadJobs.remove(id)?.cancel()
    }
}

/**
 * Load given list of paths with the same loader
 */
suspend fun <T : Any> MultiLoader<T>.load(id: String, loader: Loader<T>, vararg paths: String): T =
    load(id, paths.map { it to loader })