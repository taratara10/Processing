package training_book.chapter2

import processing.core.PApplet

fun main() = PApplet.main("training_book.chapter2.Fundamental")

class Fundamental : PApplet() {
    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        colorMode(HSB)
        background(0)
        noStroke()

        // 直径
        val diameter = width / 2F
        // 色相
        val hue = 200F
        // 彩度
        val saturation = 0F
        // 明度
        var brightness = 20F
        val alpha = 90F

        for (i in 0..10) {
            val color = color(hue, saturation, brightness, alpha)
            fill(color)
            val x = width / 10 * i + diameter / 4
            ellipse(x, height / 2F, diameter, diameter)
            brightness += 10
        }

    }

    override fun draw() {

    }
}