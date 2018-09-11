package chapter1__operator.practice

/**
 * 元素操作符
 */
fun main(args: Array<String>) {
    val list = listOf(-3, -2, -1, 0, 1, 2, 3, 4)

    // 1.elementAtOrElse 如果越界会根据方法返回默认值
    val atOrElse = list.elementAtOrElse(20) { "index $it not found" }
    println(atOrElse)

    // 2.elementAtOrNull 查找元素，找不到，返回null
    println(list.elementAtOrNull(20))

    // 3.first 返回符合条件的第一个元素，没有 抛NoSuchElementException
    println(list.first { it == 0 })
    println(list.firstOrNull { it == 10 })

}