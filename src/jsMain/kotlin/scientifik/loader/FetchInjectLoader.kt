package scientifik.loader

object FetchInjectLoader : Loader<Unit> {
    override suspend fun load(path: String) {
        fetchInject(arrayOf(path))
    }
}