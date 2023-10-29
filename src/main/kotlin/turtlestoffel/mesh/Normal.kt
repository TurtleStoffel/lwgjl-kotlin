package turtlestoffel.mesh

import org.joml.Vector3f

/**
 * Generates a mesh for flat shading (each vertex in a triangle has the same normal).
 * It is assumed the input Mesh has already duplicated each shared vertex.
 */
fun generateFlatShadingMesh(mesh: RawMesh): RawMesh {
    val vertices = mesh.vertices.flatMapIndexed { index: Int, _: Vertex ->
        // Only calculate per triangle which starts on every 3rd vertex
        if (index % 3 != 0) {
            return@flatMapIndexed listOf()
        }

        val normal = calculateNormal(
            mesh.vertices[index].position,
            mesh.vertices[index + 1].position,
            mesh.vertices[index + 2].position,
        )

        // Update the normal of each vertex in the Triangle
        0.until(3).map {
            mesh.vertices[index + it].copy(
                normal = normal
            )
        }
    }

    return RawMesh(
        vertices = vertices,
        indices = mesh.indices
    )
}

/**
 * Generates a mesh with normals for each vertex
 */
fun generateNormalMesh(sourceMesh: RawMesh): RawMesh {
    val normalVertices = sourceMesh.vertices.flatMap {vertex ->
        val p2 = Vector3f()
        vertex.position.add(vertex.normal, p2)
        val v2 = vertex.copy(
            position = p2
        )
        listOf(
            vertex.copy(),
            v2
        )
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