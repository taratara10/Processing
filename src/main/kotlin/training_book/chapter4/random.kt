package training_book.chapter4

import processing.core.PApplet

fun main() = PApplet.main("training_book.chapter4.RandomInit")

class Random: PApplet() {

    val range = (0..1000)
    var x: MutableList<Float> = mutableListOf()
    var y: MutableList<Float> = mutableListOf()

    override fun settings() {
        size(800, 600, P2D)
    }

    override fun setup() {
        background(0)
        frameRate(60f)

        for (i in range) {
            x.add(width / 2f)
            y.add(height / 2f)
            point(x[i], y[i])
        }
    }

    override fun draw() {
        stroke(255) //点の色
        noFill()
        blendMode(ADD) // 色を加算で混ぜていく
        for (i in range) {
            point(x[i], y[i])
            x[i] = x[i] + random(-2.0f, 2.0f)
            y[i] = y[i] + random(-2.0f, 2.0f)
        }
        blendMode(BLEND) // 塗りつぶしを線形な混色にすることで、線形な混色に
        fill(0, 5f) // 半透明の黒をかぶせて、黒にフェードさせていく
        noStroke()
        rect(0f, 0f, width.toFloat(), height.toFloat()) // 画面全体を塗りつぶす

    }
}

class RandomOne: PApplet() {

    val range = (0..1000)
    var x = 0f
    var y = 0f

    override fun settings() {
        size(800, 600, P2D)
    }

    override fun setup() {
        background(0)
        frameRate(60f)
        x = width / 2f
        y = height / 2f
    }

    override fun draw() {
        stroke(255)
        noFill()
        point(x, y)
        blendMode(ADD) // 色を加算で混ぜていく
        x += random(-4f, 4f)
        y += random(-4f, 4f)
        blendMode(BLEND) // 塗りつぶしを線形な混色に
        fill(0, 5f) // 半透明の黒で塗る
        noStroke()
        rect(0f, 0f, width.toFloat(), height.toFloat()) // 画面全体を塗りつぶす

    }
}

class RandomInit: PApplet() {

    val range = (0..1000)
    var x: MutableList<Float> = mutableListOf()
    var y: MutableList<Float> = mutableListOf()

    override fun settings() {
        size(800, 600, P2D)
    }

    override fun setup() {
        background(0)
        frameRate(60f)

        for (i in range) {
            x.add(random(width.toFloat()))
            y.add(random(height.toFloat()))
            point(x[i], y[i])
        }
    }

    override fun draw() {
        stroke(255) //点の色
        noFill()
        blendMode(ADD) // 色を加算で混ぜていく
        for (i in range) {
            point(x[i], y[i])
            x[i] = x[i] + random(-1.0f, 1.5f)
            y[i] = y[i] + random(-0.5f, 1.2f)

            // 画面の端からはみ出さない処理
            if (x[i] < 0) x[i] = width.toFloat()
            if (x[i] > width) x[i] = 0f

            if (y[i] < 0) y[i] = height.toFloat()
            if (y[i] > height) y[i] = 0f
        }
        blendMode(BLEND) // 塗りつぶしを線形な混色にすることで、線形な混色に
        fill(0, 5f) // 半透明の黒をかぶせて、黒にフェードさせていく
        noStroke()
        rect(0f, 0f, width.toFloat(), height.toFloat()) // 画面全体を塗りつぶす

    }
}