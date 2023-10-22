package turtlestoffel.mesh

import org.joml.Vector3f

const val VERTEX_FLOAT_COUNT = 9
const val VERTEX_BYTE_SIZE = VERTEX_FLOAT_COUNT * Float.SIZE_BYTES

const val VERTEX_POSITION_OFFSET = 0L
const val VERTEX_NORMAL_OFFSET = (3 * Float.SIZE_BYTES).toLong()
const val VERTEX_COLOR_OFFSET = (6 * Float.SIZE_BYTES).toLong()

data class Vertex(
    val position: Vector3f,
    val normal: Vector3f,
    val color: Vector3f = Vector3f(1.0f, 0.0f, 0.0f),
)
