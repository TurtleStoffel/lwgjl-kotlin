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
            val denormalizedMesh = RawMesh(
                vertices = rawMesh.indices.map { rawMesh.vertices[it] },
                indices = rawMesh.indices.indices.toList()
            )
            val normalMesh = generateNormals(denormalizedMesh)

            val vao = generateVao(denormalizedMesh.vertices, denormalizedMesh.indices)
            val normalVao = generateVao(normalMesh.vertices, normalMesh.indices)
            return Mesh(vao, denormalizedMesh.indices.size, normalVao, normalMesh.indices.size)
        }

        private fun generateNormals(sourceRawMesh: RawMesh) : RawMesh {
            val normalVertices = sourceRawMesh.vertices.flatMapIndexed { index: Int, _: Vertex ->
                if (index % 3 != 0) {
                    return@flatMapIndexed listOf()
                }

                val p1 = sourceRawMesh.vertices[index].position
                val p2 = sourceRawMesh.vertices[index + 1].position
                val p3 = sourceRawMesh.vertices[index + 2].position

                val v1 = Vector3f()
                p1.sub(p2, v1)
                val v2 = Vector3f()
                p1.sub(p3, v2)

                val normal = Vector3f()
                v1.cross(v2, normal)
                normal.normalize()

                val vertex1 = sourceRawMesh.vertices[index].copy()
                val position12 = Vector3f()
                vertex1.position.add(normal, position12)
                val vertex12 = vertex1.copy(
                    position = position12
                )

                val vertex2 = sourceRawMesh.vertices[index + 1].copy()
                val position22 = Vector3f()
                vertex2.position.add(normal, position22)
                val vertex22 = vertex2.copy(
                    position = position22
                )

                val vertex3 = sourceRawMesh.vertices[index + 2].copy()
                val position32 = Vector3f()
                vertex3.position.add(normal, position32)
                val vertex32 = vertex3.copy(
                    position = position32
                )

                listOf(
                    vertex1,
                    vertex12,
                    vertex2,
                    vertex22,
                    vertex3,
                    vertex32
                )


            }
            /*
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
            */

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
