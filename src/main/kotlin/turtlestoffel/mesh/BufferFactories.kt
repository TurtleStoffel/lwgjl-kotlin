package turtlestoffel.mesh

import org.lwjgl.BufferUtils
import java.nio.FloatBuffer
import java.nio.IntBuffer

internal fun createFloatBuffer(vertices: List<Vertex>): FloatBuffer {
    val buffer = BufferUtils.createFloatBuffer(vertices.size * VERTEX_FLOAT_COUNT)

    buffer.put(
        vertices
            .map {
                listOf(
                    it.position.x,
                    it.position.y,
                    it.position.z,
                    it.normal.x,
                    it.normal.y,
                    it.normal.z,
                    it.color.x,
                    it.color.y,
                    it.color.z,
                )
            }.flatten()
            .toFloatArray(),
    )
    // Put buffer into read-mode
    buffer.flip()
    return buffer
}

internal fun createIntBuffer(data: List<Int>): IntBuffer {
    val buffer = BufferUtils.createIntBuffer(data.size)
    buffer.put(data.toIntArray())
    // Put buffer into read-mode
    buffer.flip()
    return buffer
}
