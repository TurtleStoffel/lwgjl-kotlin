package turtlestoffel

import org.joml.Matrix4f
import org.joml.Vector3f
import turtlestoffel.mesh.Mesh
import kotlin.math.sin

class GameObject(val mesh: Mesh) {
    val modelMatrix = Matrix4f()
    val translationVector = Vector3f()
    private val bounceVector = Vector3f()

    fun update(time: Double) {
        val translation = sin(time*10).toFloat()
        bounceVector.set(0.0f, 0.0f, translation)

        modelMatrix.identity().translate(translationVector).translate(bounceVector)
    }
}