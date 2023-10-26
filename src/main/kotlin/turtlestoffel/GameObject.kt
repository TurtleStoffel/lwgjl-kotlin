package turtlestoffel

import org.joml.Matrix4f
import turtlestoffel.mesh.Mesh

class GameObject(val mesh: Mesh) {
    val modelMatrix = Matrix4f()
}