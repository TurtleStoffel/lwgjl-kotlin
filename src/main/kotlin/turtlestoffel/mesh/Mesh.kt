package turtlestoffel.mesh

import org.joml.Vector3f
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
            val normalMesh = generateNormals(rawMesh)

            val vao = generateVao(rawMesh.vertices, rawMesh.indices)
            val normalVao = generateVao(normalMesh.vertices, normalMesh.indices)
            return Mesh(vao, rawMesh.indices.size, normalVao, normalMesh.indices.size)
        }

        private fun generateNormals(sourceRawMesh: RawMesh) : RawMesh {
            val normalVertices = sourceRawMesh.vertices.flatMap { vertex ->
                val vertex1 = vertex.copy()
                val position2 = Vector3f()
                vertex.position.add(vertex.normal, position2)
                val vertex2 = vertex.copy(
                    position = position2
                )

                listOf(
                    vertex1,
                    vertex2
                )
            }

            val normalIndices = normalVertices.indices.toList()

            return RawMesh(normalVertices, normalIndices)
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
