package training_book.chapter2

import processing.core.PApplet

fun main() = PApplet.main("training_book.chapter2.IfBranch")

class IfBranch : PApplet() {
    override fun settings() {
        size(1000, 1000)
    }

    override fun setup() {
        colorMode(HSB)
        background(0)

        var count = 0L

        for (i in 0..10000) {
            val x = random(width.toFloat())
            val y = random(height.toFloat())

            // 中心点からの2点の距離
            val distance = dist(x, y, width/2F, height/2F)

            if (distance < height/2) {
                noStroke()
                fill(63F, 127F, 255F)
                count++
            } else {
                noFill()
                fill(31F, 127F, 255F)
            }
            ellipse(x, y, 10F, 10F)
        }

        println("all dots = 10000")
        println("inside circle = $count")
        println("π =${(count * 4 /10000F)}")
    }

    override fun draw() {

    }
}