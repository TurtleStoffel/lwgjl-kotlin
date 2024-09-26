package turtlestoffel.mesh

import org.joml.Vector3f
import turtlestoffel.randomColor

class PlaneBuilder {
    fun build(): Mesh {
        val originalVertex =
            Vertex(
                position = Vector3f(-1.0f, -1.0f, 0.0f),
                normal = Vector3f(0.0f, 0.0f, 1.0f),
            )
        val vertices =
            listOf(
                originalVertex.copy(
                    color = randomColor(),
                ),
                originalVertex.copy(
                    position = Vector3f(1.0f, 1.0f, 0.0f),
                    color = randomColor(),
                ),
                originalVertex.copy(
                    position = Vector3f(1.0f, -1.0f, 0.0f),
                    color = randomColor(),
                ),
                originalVertex.copy(
                    position = Vector3f(-1.0f, 1.0f, 0.0f),
                    color = randomColor(),
                ),
            )
        val indices =
            listOf(
                0,
                2,
                1,
                0,
                1,
                3,
            )
        val rawMesh =
            RawMesh(
                vertices,
                indices,
            )

        return Mesh.build(rawMesh)
    }
}
