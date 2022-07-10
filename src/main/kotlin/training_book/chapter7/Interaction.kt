package training_book.chapter7

import processing.core.PApplet
import processing.core.PVector

fun main() = PApplet.main("training_book.chapter7.Interaction")

class Interaction : PApplet() {
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
                gravity = PVector(0f, 0f),
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


        // マウスを描画
        fill(color(255, 0, 0))
        noStroke()
        ellipse(mouseX.toFloat(), mouseY.toFloat(), 10f, 10f)
    }

//    override fun mouseReleased() {
//        super.mouseReleased() // superが必要っぽい
//        particles.forEach {
//            it.addForce(generateRadialRandom())
//        }
//    }

    override fun mouseDragged() {
        super.mouseDragged()
        val mouseLoc = PVector(mouseX.toFloat(), mouseY.toFloat())
        particles.forEach {
            it.attract(mouseLoc, 100f, 5f, 20f)
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

        fun addForce(force: PVector) {
            force.div(mass)
            acceleration.add(force)
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