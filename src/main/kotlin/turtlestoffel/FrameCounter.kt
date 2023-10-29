package turtlestoffel

import org.lwjgl.glfw.GLFW.glfwGetTime

class FrameCounter {
    private var frameTime = glfwGetTime()

    fun update() {
        val timeDifference = glfwGetTime() - frameTime
        println("Time since last frame: $timeDifference")
        println("FPS: ${1/timeDifference}")
        frameTime = glfwGetTime()
    }
}