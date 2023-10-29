package turtlestoffel.mesh

import org.joml.Vector3f

fun generateNormalMesh(sourceMesh: RawMesh): RawMesh {
    val normalVertices = sourceMesh.vertices.flatMapIndexed { index: Int, _: Vertex ->
        if (index % 3 != 0) {
            return@flatMapIndexed listOf()
        }

        val p1 = sourceMesh.vertices[index].position
        val p2 = sourceMesh.vertices[index + 1].position
        val p3 = sourceMesh.vertices[index + 2].position

        val v1 = Vector3f()
        p1.sub(p2, v1)
        val v2 = Vector3f()
        p1.sub(p3, v2)

        val normal = Vector3f()
        v1.cross(v2, normal)
        normal.normalize()

        val vertex1 = sourceMesh.vertices[index].copy()
        val position12 = Vector3f()
        vertex1.position.add(normal, position12)
        val vertex12 = vertex1.copy(
            position = position12
        )

        val vertex2 = sourceMesh.vertices[index + 1].copy()
        val position22 = Vector3f()
        vertex2.position.add(normal, position22)
        val vertex22 = vertex2.copy(
            position = position22
        )

        val vertex3 = sourceMesh.vertices[index + 2].copy()
        val position32 = Vector3f()
        vertex3.position.add(normal, position32)
        val vertex32 = vertex3.copy(
            position = position32
        )

        listOf(
            vertex1,
            vertex12,
            vertex2,
            vertex22,
            vertex3,
            vertex32
        )


    }

    val normalIndices = normalVertices.indices.toList()

    return RawMesh(normalVertices, normalIndices)
}