package training_book.chapter8

import processing.core.PApplet
import processing.core.PVector
import kotlin.math.roundToLong

fun main() = PApplet.main("training_book.chapter8.Noise")

class Noise : PApplet() {
    val particles: MutableList<ParticleVector3> = mutableListOf()
    var noiseScale = 0.01f

    override fun settings() {
        size(800, 600, P3D)
    }

    override fun setup() {
        background(255)

        for (i in 0..2000) {
            particles.add(
                ParticleVector3(
                    location = PVector(random(width.toFloat()), random(height.toFloat()), random(height / 2f)),
                    gravity = PVector(0f, 0f, 0f),
                    friction = 0.1f,
                    radius = 1f
                )
            )
        }
    }

    override fun draw() {
        noStroke()
        fill(0, 3f)

        val noiseStrength = 0.1f
        particles.forEach { particle ->
            val noise = noise(
                particle.location.x * noiseScale,
                particle.location.y * noiseScale,
                particle.location.z * noiseScale
            )
            val forceX = cos(noise * PI * 4f)
            val forceY = sin(noise * PI * 4f)
            val force = PVector(forceX, forceY)
            force.mult(noiseStrength) // 乗算
            particle.apply {
                addForce(force)
                update()
                draw()
                throughWalls()
            }


        }
    }

    // noiseを初期化
    override fun mousePressed() {
        super.mousePressed()
        noiseSeed(round(random(1000f)).toFloat().roundToLong())
        noiseScale = 0.01f
        particles.forEach {
            it.location = PVector(random(width.toFloat()), random(height.toFloat()), random(height/2f))
        }
        background(255)
    }

    fun generateGrayNoise(step: Float, noiseScale: Float) {
        noStroke()
        for (j in 0 until height) {
            for (i in 0 until width) {
                // noise()を使うと完全ランダムより、粗さを滑らかにできる
                // fill で変換するための255
                val gray = noise(i * noiseScale, j * noiseScale) * 255
                fill(gray)
                rect(i.toFloat(), j.toFloat(), step, step)
            }
        }
    }

    fun generateGrayAngle(step: Float, noiseScale: Float) {
        stroke(1)
        strokeWeight(1f)
        for (j in 0 until height) {
            for (i in 0 until width) {
                // noise()を使うと完全ランダムより、粗さを滑らかにできる
                val angle = noise(i * noiseScale, j * noiseScale) * PI * 2.0f
                pushMatrix()
                translate(i.toFloat(), j.toFloat())
                rotate(angle)
                line(i.toFloat(), j.toFloat(), i + step / 2.0f, j.toFloat())
                popMatrix()
            }
        }
    }

    inner class ParticleVector3(
        var location: PVector = PVector(0f, 0f, 0f),
        var acceleration: PVector = PVector(0f, 0f, 0f),
        var gravity: PVector = PVector(0f, 0f, 0f),
        var friction: Float = 0.01f,
        val radius: Float = 4f,
        val mass: Float = 1.0f
    ) {
        var velocity = PVector(0f, 0f)

        fun update() {
            acceleration.add(gravity)
            velocity.add(acceleration)
            velocity.mult(1f - friction)
            location.add(velocity)
            acceleration.set(0f, 0f, 0f)
        }

        fun draw() {
            pushMatrix()  // 座標を保存する
            translate(location.x, location.y, location.z) // 回転軸
            ellipse(0f, 0f, mass * radius * 2f, mass * radius * 2f)
            popMatrix()
        }

        fun addForce(force: PVector) {
            force.div(mass) // F = ma
            acceleration.add(force)
        }

        /**
         * 引力を作る
         *
         * @param center 吸引力の中心点
         * @param _mass 吸引する物体の質量
         * @param min 計算する距離の最小値 近すぎると引力が発散するので制限
         * @param max 計算する距離の最大値 遠すぎはほぼ0なので、非効率
         * */
        fun attract(center: PVector, _mass: Float, min: Float, max: Float) {
            // 重力定数
            val G = 1.0f

            var distance = PVector.dist(center, location)
            // 値を指定した範囲内に収める
            distance = constrain(distance, min, max)
            // 引力の強さ (F = G(Mm/r*2))
            val strength = G * (mass * _mass) / (distance * distance)
            // 2点の位置ベクトルの差分
            val force = PVector.sub(center, location)
            // ベクトルを正規化 (方向は同じで、長さが1の単位ベクトル)
            force.normalize()
            // 向きだけの単位ベクトルに強さを乗算する
            force.mult(strength)
            addForce(force)
        }

        val max = PVector(width.toFloat(), height.toFloat(), height.toFloat() / 2F)
        val min = PVector(0f, 0f, 0f)

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
            if (location.z > max.z) {
                location.z = max.z
                velocity.z *= -1
            }
            if (location.z < min.z) {
                location.z = min.z
                velocity.z *= -1
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
            if (location.z > max.z) {
                location.z = min.z
            }
            if (location.z < min.z) {
                location.z = max.z
            }
        }
    }

}