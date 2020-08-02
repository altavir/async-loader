package scientifik.loader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path

object FileLoader: Loader<ByteArray> {
    override suspend fun load(path: String): ByteArray {
        return withContext(Dispatchers.IO) {
            val file = Path.of(path)
            Files.readAllBytes(file)
        }
    }
}