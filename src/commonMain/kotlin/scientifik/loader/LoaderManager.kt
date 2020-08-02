package scientifik.loader

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.KClass

object LoaderManager {
    private val loaders = HashMap<KClass<*>, MultiLoader<*>>()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> loaderFor(type: KClass<T>): MultiLoader<T> =
        loaders.getOrPut(type) { MultiLoader<T>(scope) } as MultiLoader<T>
}

suspend inline fun <reified T : Any> LoaderManager.load(id: String, paths: Collection<Pair<String, Loader<T>>>): T =
    loaderFor(T::class).load(id, paths)

suspend inline fun <reified T : Any> LoaderManager.load(id: String, loader: Loader<T>, vararg paths: String): T =
    loaderFor(T::class).load(id, loader, *paths)