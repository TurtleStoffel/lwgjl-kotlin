package turtlestoffel.mesh

import org.joml.Vector3f
import org.joml.Vector3i

typealias Face = Vector3i

data class RawMesh(
    val vertices: List<Vertex>,
    val indices: List<Int>
)

class SphereBuilder(private val depth: Int = 1) {
    private val vertices = mutableListOf<Vertex>()

    private val midpointCache = mutableMapOf<Long, Int>()
    private var vertexIndex = 0

    fun build(): Mesh {
        val rawMesh = generate(depth)

        val vao = generateVao(rawMesh.vertices, rawMesh.indices)
        return Mesh(vao, rawMesh.indices.size)
    }

    private fun generate(depth: Int = 1): RawMesh {
        vertices.addAll(Icosahedron.vertices)
        var faces = listOf(*Icosahedron.faces)

        vertexIndex += vertices.size

        for (i in 1..depth) {
            faces = faces.flatMap {
                val a = getMidpoint(it.x, it.y)
                val b = getMidpoint(it.y, it.z)
                val c = getMidpoint(it.z, it.x)

                return@flatMap listOf(
                    Face(it.x, a, c),
                    Face(it.y, b, a),
                    Face(it.z, c, b),
                    Face(a,    b, c)
                )
            }
        }


        val indices = faces.flatMap { listOf(it.x, it.y, it.z) }
        return RawMesh(vertices, indices)
    }

    private fun getMidpoint(p1: Int, p2: Int): Int {
        val key = calculateCacheKey(p1, p2)

        val cacheResult = midpointCache[key]
        if (cacheResult != null) {
            return cacheResult
        }

        val v1 = vertices[p1]
        val v2 = vertices[p2]

        val position = Vector3f(
            (v1.position.x + v2.position.x)/2.0f,
            (v1.position.y + v2.position.y)/2.0f,
            (v1.position.z + v2.position.z)/2.0f
        ).normalize()

        val midpoint = Vertex(
            position = position,
            // Position and normal are the same for a sphere
            normal = position
        )

        vertices.add(midpoint)

        val currentVertexIndex = vertexIndex
        vertexIndex += 1

        midpointCache[key] = currentVertexIndex

        return currentVertexIndex
    }

    private fun calculateCacheKey(p1: Int, p2: Int): Long {
        val smallIndex: Int
        val bigIndex: Int
        if (p1 < p2) {
            smallIndex = p1
            bigIndex = p2
        } else {
            smallIndex = p2
            bigIndex = p1
        }

        return (smallIndex.toLong() shl 32) + bigIndex
    }
}
