package turtlestoffel

import org.joml.Matrix4f
import turtlestoffel.mesh.Mesh

class GameObject(val mesh: Mesh) {
    val modelMatrix = Matrix4f()

    fun update() {
        modelMatrix.rotate(0.01f, 0.0f, 0.0f, 1.0f)
    }
}