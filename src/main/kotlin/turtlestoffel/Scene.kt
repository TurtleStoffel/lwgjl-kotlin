package turtlestoffel

import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import turtlestoffel.mesh.PlaneBuilder

class Scene {
    private val camera = Camera()
    private val objects: List<GameObject> = listOf(GameObject(PlaneBuilder.build()))
    private val shader: Int = Shader.createShader()

    fun render(time: Double) {
        camera.update()

        objects.forEach { it.update(time) }

        camera.setViewProjectionMatrix(shader)

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

    fun handleMouseInput(
        positionX: Double,
        positionY: Double,
    ) {
        println("Mouse position: ($positionX, $positionY)")
        val invertedY = Engine.WINDOW_SIZE.second - positionY
        val viewport = IntArray(4)
        viewport[0] = 0
        viewport[1] = 0
        viewport[2] = Engine.WINDOW_SIZE.first
        viewport[3] = Engine.WINDOW_SIZE.second

        val nearPosition = Vector3f()

        camera.getViewProjectionMatrix().unproject(
            positionX.toFloat(),
            invertedY.toFloat(),
            0.0f,
            viewport,
            nearPosition,
        )
        println("Near position: (${nearPosition.x()}, ${nearPosition.y()}, ${nearPosition.z()})")

        val farPosition = Vector3f()

        camera.getViewProjectionMatrix().unproject(
            positionX.toFloat(),
            invertedY.toFloat(),
            1.0f,
            viewport,
            farPosition,
        )
        println("Far position: (${farPosition.x()}, ${farPosition.y()}, ${farPosition.z()})")
    }
}
