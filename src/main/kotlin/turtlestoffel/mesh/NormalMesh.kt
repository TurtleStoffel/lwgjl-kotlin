package turtlestoffel.mesh

import org.lwjgl.opengl.GL11.GL_LINES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30.glBindVertexArray

class NormalMesh(
    private val mesh: IMesh,
    private val normalVao: Int,
    private val normalVertexCount: Int,
) : IMesh by mesh {
    companion object {
        fun build(
            mesh: IMesh,
            rawMesh: RawMesh,
        ): NormalMesh {
            var denormalizedMesh =
                RawMesh(
                    vertices = rawMesh.indices.map { rawMesh.vertices[it] },
                    indices = rawMesh.indices.indices.toList(),
                )

            denormalizedMesh = generateFlatShadingMesh(denormalizedMesh)
            val normalMesh = generateNormalMesh(denormalizedMesh)

            val normalVao = generateVao(normalMesh.vertices, normalMesh.indices)
            return NormalMesh(mesh, normalVao, normalMesh.indices.size)
        }
    }

    override fun render() {
        mesh.render()
        renderNormals()
    }

    private fun renderNormals() {
        glBindVertexArray(normalVao)
        glEnableVertexAttribArray(0)
        glDrawElements(GL_LINES, normalVertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }
}
