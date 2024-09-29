package turtlestoffel

import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import turtlestoffel.mesh.Line
import turtlestoffel.mesh.PlaneBuilder
import kotlin.properties.Delegates

class Scene {
    private val camera = Camera()
    private val objects = mutableListOf<GameObject>()
    private var shader by Delegates.notNull<Int>()

    /**
     * Generate the shader program
     */
    fun init() {
        shader = Shader.createShader()
        objects.addAll(
            listOf(
                GameObject(PlaneBuilder.build()),
                GameObject(Line.build(Vector3f(0.0f, 0.0f, 0.0f), Vector3f(1.0f, 1.0f, 1.0f))),
            ),
        )
    }

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

        val origin = Vector3f()
        val direction = Vector3f()

        camera.getViewProjectionMatrix().unprojectRay(
            positionX.toFloat(),
            invertedY.toFloat(),
            viewport,
            origin,
            direction,
        )
        println("Origin: (${origin.x()}, ${origin.y()}, ${origin.z()})")
        println("Direction: (${direction.x()}, ${direction.y()}, ${direction.z()})")
        val end = origin.add(direction.mul(10000.0f))
        println("End: (${end.x()}, ${end.y()}, ${end.z()})")
        val endOpposite = origin.add(direction.mul(-1000.0f))

        objects.add(GameObject(Line.build(origin, end)))
        objects.add(GameObject(Line.build(origin, endOpposite)))
        objects.add(GameObject(Line.build(Vector3f(0.0f, 0.0f, 0.0f), origin)))
    }

    fun handleRightMouseDrag(
        positionX: Double,
        positionY: Double,
    ) {
        println("Right mouse drag: ($positionX, $positionY)")
        camera.handleRightMouseDrag(positionX, positionY)
    }
}
