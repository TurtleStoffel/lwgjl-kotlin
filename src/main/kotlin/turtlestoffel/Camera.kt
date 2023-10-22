package turtlestoffel

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import java.nio.FloatBuffer
import kotlin.math.PI

const val CAMERA_SPEED = 0.3f

class Camera {
    private val position = Vector3f(0.0f, 0.0f, 3.0f)
    private val direction = Vector3f(0.0f, 0.0f, 0.0f)

    fun handleKeyInput(key: Int, action: Int) {
        if (key == GLFW_KEY_W && action == GLFW_PRESS) {
            direction.y += CAMERA_SPEED
        }
        if (key == GLFW_KEY_W && action == GLFW_RELEASE) {
            direction.y -= CAMERA_SPEED
        }

        if (key == GLFW_KEY_S && action == GLFW_PRESS) {
            direction.y -= CAMERA_SPEED
        }
        if (key == GLFW_KEY_S && action == GLFW_RELEASE) {
            direction.y += CAMERA_SPEED
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

    fun update() {
        position.add(direction)
    }

    fun getViewProjectionMatrix(): FloatBuffer {
        val cameraBuffer = BufferUtils.createFloatBuffer(16)
        Matrix4f()
            .perspective((PI /2).toFloat(), Engine.WINDOW_SIZE.first.toFloat()/ Engine.WINDOW_SIZE.second, 0.01f, 100.0f)
            .lookAt(position.x, position.y, position.z,
                0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f
            ).get(cameraBuffer)

        return cameraBuffer
    }
}