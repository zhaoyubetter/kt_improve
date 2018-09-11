package chapter1__operator.practice

fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val list2 = listOf("a", "b", "c", "d")

    println("======== flatMap 合并集合 ========================")
    listOf(list,list2).flatMap { it -> it }.forEach {
        print("$it ")
    }

    println("\n======== flatMap ========================")
    list.flatMap { a -> list2.map { b -> "$a$b" } }.forEach {
        print("$it ")
    }

    println("\n======== flatMap ========================")
    list.flatMap { a -> list2.filterIndexed { index, _ -> index % 2 == 0 }.map { b -> "$a$b" } }.forEach {
        print("$it ")
    }


    println("\n======== flatMap ========================")
    val kotin = Course(1, "Kotlin")
    val java = Course(2, "Java")
    val py = Course(3, "Python")
    val js = Course(4, "Javascript")
    val go = Course(5, "Golang")
    val c = Course(6, "C/C++")

    val stud1 = Student("better", listOf(kotin, java))
    val stud2 = Student("cz", listOf(kotin, java, py))
    val stud3 = Student("cc", listOf(go, c, js))

    // 打印所有学生的课程
    listOf(stud1, stud2, stud3).map { it.courseList }.forEach {
        it.forEach {
            print("${it.name} ")
        }
    }

    println("\n======== use flatMap 打印每个学生的课程 ========================")
    listOf(stud1, stud2, stud3).flatMap { it.courseList }.forEach { print("${it.name} ") }

}

internal data class Course(val id: Int, val name: String)
internal data class Student(val name: String, val courseList: List<Course>)


//data class City(val id: Int, val name: String)
//
//data class Country(val name: String, val citys: List<City>)