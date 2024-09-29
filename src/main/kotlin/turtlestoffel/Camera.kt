package turtlestoffel

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

const val CAMERA_SPEED = 0.1f

class Camera {
    private val position = Vector3f(0.0f, -5.0f, 3.0f)
    private val viewDirection = Vector3f(1.0f, 5.0f, -3.0f).normalize()
    private var horizontalAngle = 0.0f
    private var verticalAngle = 0.0f

    private val direction = Vector3f(0.0f, 0.0f, 0.0f)

    fun handleKeyInput(
        key: Int,
        action: Int,
    ) {
        if (key == GLFW_KEY_W && action == GLFW_PRESS) {
            direction.z += CAMERA_SPEED
        }
        if (key == GLFW_KEY_W && action == GLFW_RELEASE) {
            direction.z -= CAMERA_SPEED
        }

        if (key == GLFW_KEY_S && action == GLFW_PRESS) {
            direction.z -= CAMERA_SPEED
        }
        if (key == GLFW_KEY_S && action == GLFW_RELEASE) {
            direction.z += CAMERA_SPEED
        }

        if (key == GLFW_KEY_A && action == GLFW_PRESS) {
            direction.x -= CAMERA_SPEED
        }
        if (key == GLFW_KEY_A && action == GLFW_RELEASE) {
            direction.x += CAMERA_SPEED
        }

        if (key == GLFW_KEY_D && action == GLFW_PRESS) {
            direction.x += CAMERA_SPEED
        }
        if (key == GLFW_KEY_D && action == GLFW_RELEASE) {
            direction.x -= CAMERA_SPEED
        }
    }

    fun handleRightMouseDrag(
        x: Double,
        y: Double,
    ) {
        horizontalAngle += CAMERA_SPEED * (Engine.WINDOW_SIZE.first / 2 - x).toFloat()
        verticalAngle += CAMERA_SPEED * (Engine.WINDOW_SIZE.second / 2 - y).toFloat()

        viewDirection.set(
            Vector3f(
                cos(verticalAngle) * sin(horizontalAngle),
                sin(verticalAngle),
                cos(verticalAngle) * cos(horizontalAngle),
            ),
        )
    }

    fun update() {
        position.add(direction)
    }

    fun setViewProjectionMatrix(shader: Int) {
        val cameraBuffer = BufferUtils.createFloatBuffer(16)
        val viewProjectionMatrix =
            getViewProjectionMatrix()
                .get(cameraBuffer)

        val mvpLocation = glGetUniformLocation(shader, "viewProjectionMatrix")
        glUniformMatrix4fv(mvpLocation, false, viewProjectionMatrix)
    }

    fun getViewProjectionMatrix(): Matrix4f {
        val center = Vector3f(position).add(viewDirection)
        val up = Vector3f()
        position.cross(viewDirection, up)

        return Matrix4f()
            .perspective((PI / 2).toFloat(), Engine.WINDOW_SIZE.first.toFloat() / Engine.WINDOW_SIZE.second, 0.01f, 100.0f)
            .lookAt(position, center, up)
    }
}
