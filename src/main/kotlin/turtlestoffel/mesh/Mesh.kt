package turtlestoffel.mesh

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30.glBindVertexArray

data class RawMesh(
    val vertices: List<Vertex>,
    val indices: List<Int>
)

class Mesh(
    private val vao: Int,
    private val vertexCount: Int
) {
    fun render() {
        glBindVertexArray(vao)
        glEnableVertexAttribArray(0)
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }
}
