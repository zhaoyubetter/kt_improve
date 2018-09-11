package chapter1__operator.practice

fun main(args: Array<String>) {

    println("\n======== groupBy ========================")
    val kotin = Course(1, "Kotlin")
    val java = Course(2, "Java")
    val py = Course(3, "Python")
    val js = Course(4, "Javascript")
    val go = Course(5, "Golang")
    val c = Course(6, "C/C++")

    val stud1 = Student("better", listOf(kotin, java))
    val stud2 = Student("cz", listOf(kotin, java, py))
    val stud3 = Student("cc", listOf(go, c, js))

    listOf(stud1, stud2, stud3).groupBy { it.name }.forEach {
        println("${it.key} ${it.value.get(0).courseList}")
    }


    println("\n=========== groupingby ============================")
    // 用于按键（key）分组，同时折叠（fold）每个组
    val words = "one two three four five six seven eight nine ten".split(' ')
    words.groupingBy { it.first() }.eachCount().forEach { it ->
        print("(${it.key}, ${it.value}) ")
    }


    println("\n=========== groupingby ============================")
}