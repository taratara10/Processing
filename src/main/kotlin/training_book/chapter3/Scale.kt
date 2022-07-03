package training_book.chapter3

import processing.core.PApplet

fun main() = PApplet.main("training_book.chapter3.Scale")

class Scale : PApplet() {
    // 直径
    private var diameter = 0F
    private var hue = 0F

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        background(0)
        frameRate(60f)
        noStroke()
        colorMode(HSB, 360F, 100F, 100F, 100F)
    }

    override fun draw() {
        // 背景を描画して、前のフレームを削除する
        background(0)

        // todo sinの仕組みよくわかってない
        diameter = 400 + sin(frameCount * 0.1F) * 200
        fill(hue, 100F, 100F)
        ellipse(width/2F, height/2F, diameter, diameter)
    }
}