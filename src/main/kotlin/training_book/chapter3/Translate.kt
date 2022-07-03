package training_book.chapter3

import processing.core.PApplet


fun main() = PApplet.main("training_book.chapter3.Translate")

class Translate : PApplet() {
    // 回転角度(ラジアン)0 ~ 2π
    private var angle = 0F

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        background(0)
        frameRate(60f)
        fill(0F, 127F, 255F)
        noStroke()
    }

    override fun draw() {
        // 背景を描画して、前のフレームを削除する
        background(0)

        //回転1
        pushMatrix()  // 座標を保存する
        // 回転軸となる原点(0, 0)を中心に移動
        translate(height/2F, width/2F)
        rotate(angle)
        // 四角形の基準点を中心にする
        rectMode(CENTER)
        rect(0F, 0F, 200F, 200F)
        popMatrix() // 座標を復元

        pushMatrix()
        translate(height/4F, width/4F)
        rotate(angle)
        rectMode(CENTER)
        rect(0F, 0F, 200F, 200F)
        popMatrix()

        angle += 0.01F
    }
}