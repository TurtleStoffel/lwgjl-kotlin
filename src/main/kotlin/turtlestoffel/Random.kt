package turtlestoffel

import org.joml.Vector3f
import kotlin.random.Random.Default.nextFloat

fun randomColor(): Vector3f =
    Vector3f(
        nextFloat(),
        nextFloat(),
        nextFloat(),
    )
