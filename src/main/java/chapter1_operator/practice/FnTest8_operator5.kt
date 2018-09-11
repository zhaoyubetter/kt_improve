package chapter1__operator.practice

/**
 * 顺序操作符
 */
fun main(args: Array<String>) {
    val array = listOf(-3, -2, -1, 0, 1, 2, 3, 4)

    //1.reverse 反转
    val copy = ArrayList(array)
    println(copy.reversed())

    println()
    //2.sortedBy 根据绝对值来
    println(copy.sortedBy { Math.abs(it) })

    //3.sortedDescending
    println(copy.sortedDescending())

    // 4.根据绝对值来
    println(copy.sortedByDescending { Math.abs(it) })

    // 5.自然排序
    val list = listOf(2,1,0,-9,5,2,-8,-2)
    println(list.sorted())
}