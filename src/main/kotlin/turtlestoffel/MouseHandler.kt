package turtlestoffel

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.glfwGetCursorPos
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback

class MouseHandler(
    window: Long,
    private val scene: Scene,
) {
    init {
        glfwSetMouseButtonCallback(window) { _, button, action, _ ->
            handleMouseButton(window, button, action)
        }
        glfwSetCursorPosCallback(window) { _, xPos, yPos ->
            handleMousePosition(xPos, yPos)
        }
    }

    private fun handleMouseButton(
        window: Long,
        button: Int,
        action: Int,
    ) {
        if (action == GLFW_PRESS && button == GLFW_MOUSE_BUTTON_RIGHT) {
            // Set the cursor to the center of the screen
            // Start calculating angle for camera movement
            println("Right mouse button pressed")
        } else if (action == GLFW_RELEASE && button == GLFW_MOUSE_BUTTON_RIGHT) {
            // Stop calculating angle for camera movement
            println("Right mouse button released")
        }
        if (action != GLFW_PRESS) {
            return
        }

        println("Mouse button pressed: $button, action: $action")
        val positionX = BufferUtils.createDoubleBuffer(1)
        val positionY = BufferUtils.createDoubleBuffer(1)

        glfwGetCursorPos(window, positionX, positionY)

        scene.handleMouseInput(positionX[0], positionY[0])
    }

    private fun handleMousePosition(
        xPos: Double,
        yPos: Double,
    ) {
        println("Mouse moved: x = $xPos, y = $yPos")
    }
}
