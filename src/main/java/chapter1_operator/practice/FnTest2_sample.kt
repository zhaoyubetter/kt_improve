package chapter1__operator.practice

import java.util.*

/**
 * 比如，我们有 3 辆车比赛，简单起见，我们分别给这 3 辆车有 70% 的概率可以往前走一步，
 *  一共有 5 次机会，然后打出每一次这 3 辆车的前行状态。
 *  来自左耳听风的例子
 */

fun main(args: Array<String>) {
//    by1()
//    by2()
    by3(5, intArrayOf(1, 1, 1))
}

// === 1. 传统的写法
fun by1() {
    var times = 5
    val cars = intArrayOf(0, 0, 0)
    while (times > 0) {
        times--
        for (i in (0 until cars.size)) {
            if (Random().nextInt(10) >= 3)
                cars[i] = cars[i] + 1       // 往前一步
        }
    }

    // println result
    cars.forEachIndexed { index, i ->
        println("car name: $index, now step is $i")
    }
}

// === 2.优化一下 --> 拆方法，每个方法需要关注共享变量，这些方法是有状态的；
var by2_times = 5
val by2_cars = intArrayOf(0, 0, 0)

fun by2() {
    while (by2_times > 0) {
        by2_times--
        moveCar()
    }
    drawCar()
}

fun moveCar() {
    for (i in (0 until by2_cars.size)) {
        if (Random().nextInt(10) >= 3)
            by2_cars[i] = by2_cars[i] + 1       // 往前一步
    }
}

fun drawCar() {
    by2_cars.forEachIndexed { index, i ->
        println("car name: $index, now step is $i")
    }
}

// === 3.函数式，消除全局变量
fun by3(times: Int, cars: IntArray) {
    (0 until times).forEach {
        by3_moveCar(cars)
    }
    by3_println(cars)
}

fun by3_moveCar(cars: IntArray) {
    cars.forEachIndexed { index, i ->
        if (Random().nextInt(10) >= 3)
            cars[index] = cars[index] + 1
    }
}

fun by3_println(cars: IntArray) {
    cars.forEachIndexed { index, i ->
        println("car name: $index, now step is $i")
    }
}


