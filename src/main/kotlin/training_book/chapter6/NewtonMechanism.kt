package training_book.chapter6

import processing.core.PApplet
import processing.core.PVector


fun main() = PApplet.main("training_book.chapter6.NewtonMechanism")

class NewtonMechanism : PApplet() {

    lateinit var force: PVector
    lateinit var acceleration: PVector
    var velocity: PVector = PVector(0f, 0f)

    var location = PVector(0f, 0f)
    var mass = 1.0f
    val friction = 0.01f
    val gravity = PVector(0f, 1.0f)

    val particles: MutableList<ParticleVector> = mutableListOf()

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        frameRate(60f)
        background(0)

        for (i in 1..1000) {
            particles.add(ParticleVector(
                location = PVector(width / 2f, height / 2f),
                acceleration = generateRadialRandom(),
                gravity = PVector(0f, 0.01f),
                friction = 0.01f
            ))
        }
    }

    override fun draw() {
        // 背景をフェードさせる
        fill(0f, 31f)
        rect(0f, 0f, width.toFloat(), height.toFloat())
        fill(255)
        noStroke()

        particles.forEach {
            it.update()
            it.draw()
            it.bounceOfWalls()
        }
    }

    fun generateRadialRandom(): PVector {
        val angle = random(PI * 2.0f)
        val length = random(20f)
        val posX = cos(angle) * length
        val posY = sin(angle) * length
        return PVector(posX, posY)
    }

    inner class ParticleVector(
        var location: PVector = PVector(0f, 0f),
        var acceleration: PVector = PVector(0f, 0f),
        var gravity: PVector = PVector(0f, 0f),
        var friction: Float = 0.01f
    ) {
        val radius = 4f
        val mass = 1.0f
        var velocity = PVector(0f, 0f)

        fun update() {
            acceleration.add(gravity)
            velocity.add(acceleration)
            velocity.mult(1f - friction)
            location.add(velocity)
            acceleration.set(0f, 0f)
        }

        fun draw() {
            ellipse(location.x, location.y, mass * radius * 2f, mass * radius * 2f)
        }

        val max = PVector(width.toFloat(), height.toFloat())
        val min = PVector(0f, 0f)

        fun bounceOfWalls() {
            if (location.x > max.x) {
                location.x = max.x
                velocity.x *= -1
            }
            if (location.x < min.x) {
                location.x = min.x
                velocity.x *= -1
            }
            if (location.y > max.y) {
                location.y = max.y
                velocity.y *= -1
            }
            if (location.y < min.y) {
                location.y = min.y
                velocity.y *= -1
            }
        }

        fun throughWalls() {
            if (location.x > max.x) {
                location.x = min.x
            }
            if (location.x < min.x) {
                location.x = max.x
            }
            if (location.y > max.y) {
                location.y = min.y
            }
            if (location.y < min.y) {
                location.y = max.y
            }
        }
    }
}