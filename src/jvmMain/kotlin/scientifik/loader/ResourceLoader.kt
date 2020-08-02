package scientifik.loader

object ResourceLoader: Loader<ByteArray> {
    override suspend fun load(path: String): ByteArray {
        return javaClass.classLoader.getResourceAsStream(path)?.use {
            it.readAllBytes()
        }?: error("Not resolved on the classpath")
    }
}