package chapter1__operator.practice

/**
 * 生产操作符
 */
fun main(args: Array<String>) {
    val list1 = listOf(1, 2, 3, 4)
    val list2 = listOf(5, 6, 7, 8, 9)

    // 1.zip 按照下标组合成Pair
    println(list1.zip(list2))       // 丢弃 9
    // 通过转换函数zip
    val zip = list1.zip(list2) { first, second ->
        first * second
    }
    println(zip)

    // 2.partition，拆分成2个list
    val list3 = listOf(-3, -2, -1, 0, 1, 2, 3, 4)
    println(list3.partition { it > 0 })

    // 3.plus
    println(list1.plus(list2))

    // 4.unzip 返回 pair，里面2个list
    val list4 = listOf("one" to 1, "two" to 2, "three" to 3)
    println(list4.unzip())

}