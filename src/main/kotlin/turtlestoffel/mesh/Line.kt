package turtlestoffel.mesh

import org.joml.Vector3f
import org.lwjgl.opengl.GL11.GL_LINES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30.glBindVertexArray

class Line private constructor(
    private val vao: Int,
) : IMesh {
    companion object {
        fun build(
            start: Vector3f,
            end: Vector3f,
        ): Line {
            val vertices =
                listOf(
                    Vertex(start, Vector3f(1.0f, 0.0f, 0.0f), Vector3f(1.0f, 0.0f, 0.0f)),
                    Vertex(end, Vector3f(1.0f, 0.0f, 0.0f), Vector3f(1.0f, 0.0f, 0.0f)),
                )
            val indices = listOf(0, 1)
            val vao = generateVao(vertices, indices)
            return Line(vao)
        }
    }

    override fun render() {
        glBindVertexArray(vao)
        glEnableVertexAttribArray(0)
        glDrawElements(GL_LINES, 2, GL_UNSIGNED_INT, 0)
        glDisableVertexAttribArray(0)
        glBindVertexArray(0)
    }
}
