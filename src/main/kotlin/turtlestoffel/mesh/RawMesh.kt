package turtlestoffel.mesh

/**
 * A collection of vertices and indices that can be rendered.
 * [vertices] and [indices] don't have to be denormalized (i.e. a vertex can be referenced multiple times).
 */
data class RawMesh(
    val vertices: List<Vertex>,
    val indices: List<Int>,
)
