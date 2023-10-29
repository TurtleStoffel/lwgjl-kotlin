package turtlestoffel

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryUtil.NULL
import turtlestoffel.mesh.PlaneBuilder
import turtlestoffel.mesh.RoofBuilder
import turtlestoffel.mesh.SphereBuilder

private fun createWindow(): Long {
    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) {
        throw IllegalStateException("Unable to initialize GLFW")
    }

    // Configure our window
    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1)

    // Create the window
    val window = glfwCreateWindow(Engine.WINDOW_SIZE.first, Engine.WINDOW_SIZE.second, "Hello World!", NULL, NULL)
    if (window == NULL) {
        throw RuntimeException("Failed to create the GLFW window")
    }

    return window
}

class Engine(
    private val window: Long = createWindow()
) {
    companion object {
        val WINDOW_SIZE = Pair(800, 600)
    }

    private var errorCallback : GLFWErrorCallback? = null
    private var keyCallback : GLFWKeyCallback? = null

    private val camera = Camera()
    private var frameTime = glfwGetTime()

    private fun init() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        errorCallback = glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err))

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        keyCallback = glfwSetKeyCallback(window, object : GLFWKeyCallback() {
            override fun invoke(window: Long,
                                key: Int,
                                scancode: Int,
                                action: Int,
                                mods: Int) {

                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
                    glfwSetWindowShouldClose(window, true)
                }

                camera.handleKeyInput(key, action)
            }
        })

        // Get the resolution of the primary monitor
        val videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

        // Center our window
        glfwSetWindowPos(
            window,
            (videoMode.width() - WINDOW_SIZE.first) / 2,
            (videoMode.height() - WINDOW_SIZE.second) / 2
        )

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)
        // Enable v-sync
        glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(window)
    }

    private fun loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        //val mesh = SphereBuilder().build()
        //val mesh = RoofBuilder().build()

        val leftPlane = GameObject(SphereBuilder().build())
        leftPlane.modelMatrix.translate(-3f, 0f, 0f)

        val rightPlane = GameObject(SphereBuilder().build())
        rightPlane.modelMatrix.translate(3f, 0f, 0f)

        val shader = Shader.createShader()

        glEnable(GL_CULL_FACE)
        glPolygonMode(GL_FRONT_AND_BACK, GL_POLYGON)

        glEnable(GL_DEPTH_TEST)

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            // Clear the framebuffer
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            camera.update()

            val viewProjectionMatrix = camera.getViewProjectionMatrix()
            val mvpLocation = glGetUniformLocation(shader, "viewProjectionMatrix")
            glUniformMatrix4fv(mvpLocation, false, viewProjectionMatrix)

            val modelMatrixLocation = glGetUniformLocation(shader, "modelMatrix")

            glUniformMatrix4fv(modelMatrixLocation, false, leftPlane.modelMatrix.get(BufferUtils.createFloatBuffer(16)))
            leftPlane.mesh.render()

            glUniformMatrix4fv(modelMatrixLocation, false, rightPlane.modelMatrix.get(BufferUtils.createFloatBuffer(16)))
            rightPlane.mesh.render()

            val timeDifference = glfwGetTime() - frameTime
            println("Time since last frame: $timeDifference")
            println("FPS: ${1/timeDifference}")
            frameTime = glfwGetTime()

            // Swap the color buffers
            glfwSwapBuffers(window)

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents()
        }
    }

    fun run() {
        try {
            init()
            loop()
            glfwDestroyWindow(window)
        } finally {
            glfwTerminate()
        }
    }
}