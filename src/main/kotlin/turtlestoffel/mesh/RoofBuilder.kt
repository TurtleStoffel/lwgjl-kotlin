package turtlestoffel.mesh

import org.joml.Vector3f

class RoofBuilder {
    fun build(): Mesh {
        val bottomLeftVertex = Vertex(
            position = Vector3f(-1.0f, -0.5f, 0.0f),
            normal = Vector3f(-1.0f, 0.0f, 1.0f).normalize(),
            color = Vector3f(1.0f, 0.0f, 0.0f)
        )
        val topVertex = bottomLeftVertex.copy(
            position = Vector3f(0.0f, -0.5f, 1.0f),
            normal = Vector3f(0.0f, 0.0f, 1.0f),
        )
        val bottomRightVertex = bottomLeftVertex.copy(
            position = Vector3f(1.0f, -0.5f, 0.0f),
            normal = Vector3f(1.0f, 0.0f, 1.0f).normalize(),
        )
        val vertices = listOf(
            // Bottom left
            bottomLeftVertex.copy(),
            bottomLeftVertex.copy(position = Vector3f(-1.0f, 0.5f, 0.0f)),
            // Top
            topVertex.copy(),
            topVertex.copy(position = Vector3f(0.0f, 0.5f, 1.0f)),
            // Bottom right
            bottomRightVertex.copy(),
            bottomRightVertex.copy(
                position = Vector3f(1.0f, 0.5f, 0.0f),
            ),
        )
        val indices = listOf(
            0, 2, 1,
            1, 2, 3,
            2, 4, 3,
            3, 4, 5,
        )
        val rawMesh = RawMesh(
            vertices,
            indices
        )

        return Mesh.build(rawMesh)
    }
}
