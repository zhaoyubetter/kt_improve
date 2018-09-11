package chapter1__operator.practice

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println("======== take ========================")
    list.take(3).forEach { print("$it ") }

    println("\n======== takeLast ========================")
    list.takeLast(3).forEach { print("$it ") }

    println("\n======== takeLastWhile ========================")
    // 从最后一个元素，一直到不满足条件为止
    val last = listOf(-1, -2, -5, -2, 5, 6, -20, 4, 8)
    last.takeLastWhile { it % 2 == 0 }.forEach { print("$it ") }   // 6 -20 4 8

    println("\n======== takeWhile ========================")
    last.takeWhile { it < 0 }.forEach { print("$it ") }

    println("\n======== takeIf ========================")
    // 是否全部满足
    list.takeIf { it.all { it > 0 } }?.forEach { print("$it ") }
    last.takeIf { it.all { it > 0 } }?.forEach { print("$it ") }

    println("\n======== takeUnless ========================")
    // 是否全部不满足
    list.takeUnless { it.all { it < 0 } }?.forEach { print("$it ") }


}
