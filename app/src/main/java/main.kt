import java.util.*

data class Person(val name: String, val age: Int)

fun main() {
    println(Arrays.toString(gap(6, 100, 110)))
}

fun gap(g: Int, m: Long, n: Long): LongArray {
    (m until n-g).map { i ->
        if (isPrime(i) and (isPrime(i + g)) and(!hasPrimeBetween(i , i + g)) )
            return longArrayOf(i, i + g)
    }.toList()
    return longArrayOf()
}

fun hasPrimeBetween(i: Long, l: Long): Boolean {
    (i..l).forEach{
        if(isPrime(it))
            return true
    }
    return false
}

fun isPrime(n: Long): Boolean {
    for (i in 2..Math.sqrt(n.toDouble()).toInt())
        if (n % i.toLong() == 0L)
            return false
    return true
}

//
//fun gap(g: Int, m: Long, n: Long): LongArray {
//    return LongArray((n - m).toInt(), fun(i: Int): Long {
//        return (i + m)
//    }).filter { isPrime(it) }.toLongArray()
//}
//
//fun filterGap(prev: Long, current: Long, gap: Int): Boolean {
//    return (current - prev).toInt() == gap
//}
//

//
//inline fun LongArray.filterTwo(predicate: (Long, Long) -> Boolean): List<Long> {
//    return filterTwo(ArrayList<Long>(), predicate)
//}
//
//inline fun <C : MutableCollection<in Long>> LongArray.filterTwo(
//    destination: C,
//    predicate: (Long, Long) -> Boolean
//): C {
//    for (i in 1 until this.size) {
//        if (predicate(this[i - 1], this[i])) {
//            destination.add(this[i])
//        }
//    }
//    return destination
//}
//
