fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val first = line.first { it.isDigit() }
            val last = line.last { it.isDigit() }
            "$first$last".toInt()
        }
    }

    val digits = listOf(
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    ) + (1..9).map(Int::toString)

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val firstS = line.findAnyOf(digits)!!.second
            val first = digits.indexOf(firstS).mod(9) + 1
            val lastS = line.findLastAnyOf(digits)!!.second
            val last = digits.indexOf(lastS).mod(9) + 1

            "$first$last".toInt()
        }
    }

    check(part1(readInput("D01T01")) == 142)
    check(part2(readInput("D01T02")) == 281)

    val input = readInput("D01")
    part1(input).println()
    part2(input).println()
}
