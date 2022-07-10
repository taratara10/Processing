package training_book.chapter5

import processing.core.PApplet
import processing.core.PVector

fun main() = PApplet.main("training_book.chapter5.Movement")

class Movement : PApplet() {

    private val range = (0..1000)
    val mParticle: MutableList<Particle> = mutableListOf()

    override fun settings() {
        size(800, 600, P2D) // 高速な2D描画
    }

    override fun setup() {
        background(0)
        frameRate(60f)
        blendMode(ADD)
        noStroke()

        for (i in range) {
            // 8-32pxのランダム粒子を生成
            mParticle.add(Particle(random(8F, 32f)))
        }
    }

    override fun draw() {
        background(0)
        mParticle.forEach { it.draw() }
    }

    /**
     * 粒子クラス
     *
     * @constructor diameter 直径
     * */
    inner class Particle(val diameter: Float) {
        // 位置ベクトル
        val location = PVector(random(0f, width.toFloat()), random(0f, height.toFloat()))
        // 速度ベクトル
        val velocity = PVector(random(-4f, 4f), random(-4f, 4f))
        val color = color(random(255f), random(255f), random(255f))

        fun draw() {
            fill(color) // 色を設定
            // 位置ベクトルに円を描画
            ellipse(location.x, location.y, diameter, diameter)
            // 位置ベクトルに速度ベクトルを加算、次の位置になる
            location.add(velocity)

            // バウンド処理
            if (location.x < 0 || location.x > width) velocity.x *= -1
            if (location.y < 0 || location.y > height) velocity.y *= -1
        }
    }
}


