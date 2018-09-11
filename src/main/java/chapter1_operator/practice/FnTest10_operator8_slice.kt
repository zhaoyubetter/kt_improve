package chapter1__operator.practice

/**
 * slice https://zhuanlan.zhihu.com/p/36707197
 */
fun main(args: Array<String>) {
    val list = listOf(0, 1, 2, 3, 4, 5, 6, 7, 111, 10)
    println(list.slice(IntRange(1, 4)))         // [1, 2, 3, 4]
    println(list.slice(listOf(0, 2, 4, 8)))     // [0, 2, 4, 111]

    val array = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 111, 10)
    array.sliceArray(0..2).forEach {
        print("$it ")
    }
    array.sliceArray(listOf(2,5)).forEach { print("$it ") }

}