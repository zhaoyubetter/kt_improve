package chapter1__operator.practice

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println("\n======== fold ========================")
    println(list.fold(100, { init, it -> init + it }))

    println("\n======== foldRight ========================")
    // right to left
    println(list.foldRight(0) { init, it ->
        print("$it ");init + it
    })

//    list.foldIndexed(100) { index, init, it ->
//
//    }

}