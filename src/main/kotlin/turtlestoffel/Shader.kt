package turtlestoffel

import org.lwjgl.opengl.GL20.*

class Shader {
    companion object {
        fun createShader(): Int {
            val vertexShaderSource = Shader::class.java.getResource("/vertex.glsl")?.readText()
            val vertexShader = glCreateShader(GL_VERTEX_SHADER)
            glShaderSource(vertexShader, vertexShaderSource)
            glCompileShader(vertexShader)

            val fragmentShaderSource = Shader::class.java.getResource("/fragment.glsl")?.readText()
            val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
            glShaderSource(fragmentShader, fragmentShaderSource)
            glCompileShader(fragmentShader)

            val shaderProgram = glCreateProgram()
            glAttachShader(shaderProgram, vertexShader)
            glAttachShader(shaderProgram, fragmentShader)
            glLinkProgram(shaderProgram)

            glUseProgram(shaderProgram)

            return shaderProgram
        }
    }
}