package training_book.chapter3

import processing.core.PApplet

fun main() = PApplet.main("training_book.chapter3.CircleAnimation")

class CircleAnimation : PApplet() {
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
        val x = random(width.toFloat())
        val y = random(height.toFloat())

        // 中心点からの2点の距離
        val distance = dist(x, y, width / 2F, height / 2F)

        // 直径
        val diameter = 30 - distance / 10F
        if (diameter > 0) {
            fill(63F, 127F, 255F)
            ellipse(x, y, diameter, diameter)
        }
    }
}