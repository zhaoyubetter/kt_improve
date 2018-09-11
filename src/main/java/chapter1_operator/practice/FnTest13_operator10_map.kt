package chapter1__operator.practice

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println("======== map ========================")
    list.map { it * 2 }.forEach { print("$it" ) }

    println("\n======== map ========================")
    list.mapIndexedNotNull { index: Int, i: Int ->  }

}