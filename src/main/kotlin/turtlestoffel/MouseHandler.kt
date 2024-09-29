package turtlestoffel

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwGetCursorPos
import org.lwjgl.glfw.GLFWMouseButtonCallback

class MouseHandler(
    private val scene: Scene,
) : GLFWMouseButtonCallback() {
    override fun invoke(
        window: Long,
        button: Int,
        action: Int,
        mods: Int,
    ) {
        if (action != GLFW_PRESS) {
            return
        }

        println("Mouse button pressed: $button, action: $action")
        val positionX = BufferUtils.createDoubleBuffer(1)
        val positionY = BufferUtils.createDoubleBuffer(1)

        glfwGetCursorPos(window, positionX, positionY)

        scene.handleMouseInput(positionX[0], positionY[0])
    }
}
