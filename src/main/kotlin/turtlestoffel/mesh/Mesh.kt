package turtlestoffel.mesh

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30.glBindVertexArray

data class RawMesh(
    val vertices: List<Vertex>,
    val indices: List<Int>
)

class Mesh private constructor(
    private val vao: Int,
    private val vertexCount: Int,
    private val normalVao: Int?,
    private val normalVertexCount: Int?
) {
    companion object {
        fun build(rawMesh: RawMesh) : Mesh {
            var denormalizedMesh = RawMesh(
                vertices = rawMesh.indices.map { rawMesh.vertices[it] },
                indices = rawMesh.indices.indices.toList()
            )
            denormalizedMesh = updateNormals(denormalizedMesh)
            val normalMesh = generateNormalMesh(denormalizedMesh)

            val vao = generateVao(denormalizedMesh.vertices, denormalizedMesh.indices)
            val normalVao = generateVao(normalMesh.vertices, normalMesh.indices)
            return Mesh(vao, denormalizedMesh.indices.size, normalVao, normalMesh.indices.size)
        }
    }

    fun render() {
        glBindVertexArray(vao)
        glEnableVertexAttribArray(0)
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)

        if (normalVao != null) {
            glBindVertexArray(normalVao)
            glEnableVertexAttribArray(0)
            glDrawElements(GL_LINES, normalVertexCount!!, GL_UNSIGNED_INT, 0)
            glDisableVertexAttribArray(0)
            glBindVertexArray(0)
        }
    }
}
