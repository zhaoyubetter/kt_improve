package chapter1__operator.practice

fun main(args: Array<String>) {
    val array = arrayOf("aaa", "bbb", "ccc", "ddd", "1111", "5555", "9999")
    // === 1. map 转大写
    println(array.map { it -> it.toUpperCase() })

    // === 2. filter
    println(array.filter {
        try {
            it.toInt()
            true
        } catch (e: Exception) {
            false
        }
    })

    // === filterTo



    // === 3.reduce
    println(array.reduce { init, next ->
        println("$init $next")
        "$init, $next"
    })


    // === 4. reduceRight
    println(array.reduceRight { init, next ->
        println("$init $next")
        "$init, $next"
    })


}