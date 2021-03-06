fun hamming(x: List<Int>, y: List<Int>) = (0 until 20).count{x[it] != y[it]}

fun gray(p: Int): List<Int> {
    if (p == 1) return listOf(0,1)
    val code = gray(p-1)
    return ArrayList<Int>(code).apply{
        addAll(code.map{it+1.shl(p-1)}.asReversed())}
}

fun output(p: List<Int>, d: List<Int>, s: List<Int>) {
    // val out = ArrayList<Int>(s)
    // println(p)
    val index = ArrayList<Int>((0..19).toList())
    index[18] = d[0].also{index[d[0]] = 18} // first diff <--> 2**18
    if (d.size > 1 && 19 !in d) {
        if (d.last() != 18) {
            index[19] = d.last()
            index[d.last()] = 19
        }
        else {
            index[19] = d[0]
            index[18] = 19
        }
    }
    // println(index)
    println((0..19).map{s[it] xor p[index[it]]}.joinToString(""))
}

fun solve(src: List<Int>, dst: List<Int>, step: Int, ham: Int) {
    val s = step - ham
    val h = s / 2
    var diff = ArrayList<Int>((0..19).filter{src[it] != dst[it]})
    val cur = ArrayList<Int>(src)
    val code = gray(19)
    for (i in 1..h) {
        //println("code[$i] = ${code[i]}")
        val pattern = (0..19).map{code[i].ushr(it).and(1)}
        output(pattern, diff, src)
    }
    for (i in (1.shl(19)-h-1) until (1 shl 19)) {
        //println("code[$i] = ${code[i]}")
        val pattern = (0..19).map{code[i].ushr(it).and(1)}
        output(pattern, diff, src)
    }
    cur[diff[0]] = 1 - cur[diff[0]]
    for (i in diff.reversed()) {
        if (cur[i] != dst[i]) {
            cur[i] = 1 - cur[i]
            println(cur.joinToString(""))
        }
    }
}

fun main() {
    val src = readLine()!!.map{it - '0'}
    val dst = readLine()!!.map{it - '0'}
    var step = readLine()!!.toInt()
    val h = hamming(src, dst)
    when {
        h > step -> { println("-1") }
        (h - step) % 2 != 0 -> { println("-1") }
        h == 0 -> { return }
        else -> {solve(src, dst, step,h)}
    }
}
