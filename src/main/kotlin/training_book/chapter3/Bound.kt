package training_book.chapter3

import processing.core.PApplet

fun main() = PApplet.main("training_book.chapter3.BoundAnimation")

class BoundAnimation : PApplet() {
    var locationX: Float = 0F

    // 移動する向き
    var direction: Int = -1

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        colorMode(HSB)
        background(0)
        frameRate(60f)
        noStroke()
    }

    override fun draw() {
        // 背景を描画して、前のフレームを削除する
        background(0)
        val radian = 20F
        ellipse(locationX, height / 2F, radian, radian)
        locationX += (10 * direction)

        if (locationX < 0 || locationX > width) {
            direction *= -1
        }
    }
}