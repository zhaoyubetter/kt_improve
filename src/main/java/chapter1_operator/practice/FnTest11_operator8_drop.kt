package chapter1__operator.practice

/**
 * drop
 */
fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    println("======== drop ========================")
    list.drop(3).forEach { print("$it ") }        // 丢弃前面3个

    println("\n======== dropLast ========================")
    list.dropLast(3).forEach { print("$it ") }

    println("\n======== dropWhile ========================")
    // 去掉满足条件的项目，直到不满足条件位置（后续的可能满足条件）
    val dropWhileList = listOf(-1, -2, -5, -2, 5, 6, -20, 7, 8)
    dropWhileList.dropWhile { it < 0 }.forEach { print("$it ") }

    println("\n======== dropLastWhile ========================")
    dropWhileList.dropLastWhile { it > 0 }.forEach { print("$it ") }        // -1 -2 -5 -2 5 6 -20

}