package chapter1_operator.sample.words

import java.io.File
import java.util.regex.Pattern

/**
 * 最近在读英语短文小说，发现了不少生词，顺便记录了一下，然后就有了一下需求；
 *
 * 统计单词数据
 * 需求：
 * 1. 将所有生词按照字母排序
 * 2. 按首字母分组统计生词个数
 * 3. 找出所有没有音标的生词
 * 4. 找出所有词组
 */

private const val filePath = "../../../chapter1/words.txt"

private data class Word(val spell: String, val soundmark: String?, val summary: String)

fun main(args: Array<String>) {
    val listWords = getWords()
//    resolve_one(listWords)
    resolve_two(listWords)
}

private fun getWords(): List<Word> {
    val filePath = Word::class.java.getResource(filePath).path
    val words = mutableListOf<Word>()
    val file = File(filePath)
    if (!file.exists()) {
        return words
    }

    // 过滤掉 空行 与 #开头的
    file.readLines(charset = Charsets.UTF_8).filterNot {
        it.trim().isEmpty() || it.startsWith("#")
    }.forEach {
        words += lineToWord(it)
    }

    return words
}

private inline fun lineToWord(line: String): Word {
    /*
文件每一行表示一个单词，有单词（可能是词组），有音标，有中文意思，其中，音标可能没有，求获取的正则表达式
可以分别求出，单词，音标，中文意思；

示例：
cambridge  [ˈkembrɪdʒ]	剑桥（英国城市）
cigarette  [sɪgə'ret]	香烟；纸烟
run across	跑着穿过
tablet	['tæblɪt]	 碑；药片；写字板；小块；平板电脑
Albert ['ælbət] 艾伯特（男子名）
at once	马上，立刻
     */
    // 利用rg来切割字符串  prison	['prɪz(ə)n]	监狱
    val regex = """(?<g1>([\s\w$])+)\s+(?<g2>\[.+?\])?(?<g3>.+)"""
    val p = Pattern.compile(regex)
    val matcher = p.matcher(line)
    if (matcher.find()) {
        return Word(matcher.group("g1").trim(), matcher.group("g2"), matcher.group("g3"))
    } else {
        throw RuntimeException("line ${line} is error!")
    }
}

/**
 * === 1. 将所有生词按照字母排序  sortedWith ==============
 */
private fun resolve_one(words: List<Word?>) {
    println("1. 将所有生词按照字母排序")
    words.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it?.spell ?: "" }).forEach {
        println(it?.spell)
    }
}

/**
 * === 2. 按首字母分组统计生词个数 groupBy ==============
 */
private fun resolve_two(words: List<Word>) {
    println("2. 按首字母分组统计生词个数")
    words.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.spell })
            .groupBy { it.spell[0].toLowerCase() }
            .forEach { key, value ->
                println("$key: [${value.joinToString { it.spell }}]")
            }
}
