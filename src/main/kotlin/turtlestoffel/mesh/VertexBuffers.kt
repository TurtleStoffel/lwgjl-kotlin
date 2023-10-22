package turtlestoffel.mesh

import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays

fun generateVao(vertices: List<Vertex>, indices: List<Int>): Int {
    val vao = glGenVertexArrays()
    glBindVertexArray(vao)
    storeData(vertices)
    bindIndices(indices)
    glBindVertexArray(0)
    return vao
}

private fun storeData(vertices: List<Vertex>) {
    val vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    val buffer = createFloatBuffer(vertices)
    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
    // Configure Vertex Positions
    glVertexAttribPointer(0, 3, GL_FLOAT, false, VERTEX_BYTE_SIZE, VERTEX_POSITION_OFFSET)
    glEnableVertexAttribArray(0)
    // Configure Vertex Normals
    glVertexAttribPointer(1, 3, GL_FLOAT, false, VERTEX_BYTE_SIZE, VERTEX_NORMAL_OFFSET)
    glEnableVertexAttribArray(1)
    // Configure Vertex Colors
    glVertexAttribPointer(2, 3, GL_FLOAT, false, VERTEX_BYTE_SIZE, VERTEX_COLOR_OFFSET)
    glEnableVertexAttribArray(2)

    glBindBuffer(GL_ARRAY_BUFFER, 0)
}

private fun bindIndices(data: List<Int>) {
    val vbo = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo)
    val buffer = createIntBuffer(data)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
}
