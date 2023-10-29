package turtlestoffel.mesh

import org.joml.Vector3f

fun generateNormalMesh(sourceMesh: RawMesh): RawMesh {
    val normalVertices = sourceMesh.vertices.flatMapIndexed { index: Int, _: Vertex ->
        // Only calculate per triangle which starts on every 3rd vertex
        if (index % 3 != 0) {
            return@flatMapIndexed listOf()
        }

        val normal = calculateNormal(
            sourceMesh.vertices[index].position,
            sourceMesh.vertices[index + 1].position,
            sourceMesh.vertices[index + 2].position,
        )

        /**
         * Generate the normal vertices (p, p+N) for each vertex
         */
        return@flatMapIndexed 0.until(3).flatMap {i ->
            val vertex = sourceMesh.vertices[index + i].copy()
            val position2 = Vector3f()
            vertex.position.add(normal, position2)
            val vertex12 = vertex.copy(
                position = position2
            )
            listOf(
                vertex,
                vertex12
            )
        }
    }

    val normalIndices = normalVertices.indices.toList()

    return RawMesh(normalVertices, normalIndices)
}

private fun calculateNormal(p1: Vector3f, p2: Vector3f, p3: Vector3f): Vector3f {
    val v1 = Vector3f()
    p1.sub(p2, v1)
    val v2 = Vector3f()
    p1.sub(p3, v2)

    val normal = Vector3f()
    v1.cross(v2, normal)
    normal.normalize()
    return normal
}