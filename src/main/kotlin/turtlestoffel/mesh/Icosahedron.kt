package turtlestoffel.mesh

import org.joml.Vector3f
import kotlin.math.sqrt

class Icosahedron {
    companion object {
        private val d = (1.0f + sqrt(5.0f))/2.0f

        val vertices = arrayOf(
            generateVertex(Vector3f(-1.0f, d, 0.0f).normalize()),
            generateVertex(Vector3f( 1.0f,  d, 0.0f).normalize()),
            generateVertex(Vector3f(-1.0f, -d, 0.0f).normalize()),
            generateVertex(Vector3f( 1.0f, -d, 0.0f).normalize()),

            generateVertex(Vector3f(0.0f, -1.0f,  d).normalize()),
            generateVertex(Vector3f(0.0f,  1.0f,  d).normalize()),
            generateVertex(Vector3f(0.0f, -1.0f, -d).normalize()),
            generateVertex(Vector3f(0.0f,  1.0f, -d).normalize()),

            generateVertex(Vector3f( d, 0.0f, -1.0f).normalize()),
            generateVertex(Vector3f( d, 0.0f,  1.0f).normalize()),
            generateVertex(Vector3f(-d, 0.0f, -1.0f).normalize()),
            generateVertex(Vector3f(-d, 0.0f,  1.0f).normalize()),
        )
        private fun generateVertex(position: Vector3f): Vertex {
            return Vertex(
                position = position,
                // Position and normal are the same for a sphere
                normal = position,
                color = Vector3f(1.0f, 0.0f, 0.0f)
            )
        }

        val faces = arrayOf(
            Face( 0, 11,  5),
            Face( 0,  5,  1),
            Face( 0,  1,  7),
            Face( 0,  7, 10),
            Face( 0, 10, 11),

            Face( 1,  5,  9),
            Face( 5, 11,  4),
            Face(11, 10,  2),
            Face(10,  7,  6),
            Face( 7,  1,  8),

            Face( 3,  9,  4),
            Face( 3,  4,  2),
            Face( 3,  2,  6),
            Face( 3,  6,  8),
            Face( 3,  8,  9),

            Face( 4,  9,  5),
            Face( 2,  4, 11),
            Face( 6,  2, 10),
            Face( 8,  6,  7),
            Face( 9,  8,  1)
        )
    }
}