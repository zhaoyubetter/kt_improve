package chapter1__operator.practice

/**
 * 代码描述的是你在干什么，而不是你怎么干的
 */
fun main(args: Array<String>) {
    val array = intArrayOf(-1, 2, 9, -20, 5, 3, -9, 18, 9, -7, 4)

    // 1.通过正数个数
    println(array.filter { it > 0 }.size)

    // 2.计算正数的平均值
    println(array.filter { it > 0 }.average())

    // 3.正数求和 pipeline模式，若干命令串起来得到的记过，形成另一个命令的输入参数
    val sum = array.filter { it > 0 }.reduce { init, next -> init + next }
    println("sum:$sum")

    // 4.输出偶数，并拼接
    val str = array.filter { it % 2 == 0 }.map { "$it" }.reduce { init, next -> "$init$next" }
    println("$str class=${str::class.java}")
}