package turtlestoffel

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import turtlestoffel.mesh.PlaneBuilder

class Scene {
    private val camera = Camera()
    private val objects: List<GameObject> = listOf(GameObject(PlaneBuilder().build()))
    private val shader: Int = Shader.createShader()

    fun render(time: Double) {
        camera.update()

        objects.forEach { it.update(time) }

        val viewProjectionMatrix = camera.getViewProjectionMatrix()
        val mvpLocation = glGetUniformLocation(shader, "viewProjectionMatrix")
        glUniformMatrix4fv(mvpLocation, false, viewProjectionMatrix)

        // --- Render
        val modelMatrixLocation = glGetUniformLocation(shader, "modelMatrix")

        objects.forEach {
            glUniformMatrix4fv(modelMatrixLocation, false, it.modelMatrix.get(BufferUtils.createFloatBuffer(16)))
            it.mesh.render()
        }
    }

    fun handleKeyInput(
        key: Int,
        action: Int,
    ) {
        camera.handleKeyInput(key, action)
    }
}
