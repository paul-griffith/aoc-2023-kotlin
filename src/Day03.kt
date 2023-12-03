fun main() {
    val numeric = """\d+""".toRegex()

    data class CharPosition(
        val position: Pair<Int, Int>,
        val value: Char,
    )

    fun List<String>.neighborsFor(row: Int, col: Int): List<CharPosition> {
        fun get(row: Int, col: Int): CharPosition? {
            return getOrNull(row)?.getOrNull(col)?.let {
                CharPosition(row to col, it)
            }
        }

        return listOfNotNull(
            get(row, col - 1), // left
            get(row, col + 1), // right
            get(row - 1, col), // up
            get(row - 1, col - 1), // up left
            get(row - 1, col + 1), // up right
            get(row + 1, col), // down
            get(row + 1, col - 1), // down left
            get(row + 1, col + 1), // down right
        )
    }

    fun part1(input: List<String>): Int {
        fun Char.isSymbol(): Boolean {
            return !isDigit() && this != '.'
        }

        var sum = 0
        for ((row, line) in input.withIndex()) {
            for (matchResult in numeric.findAll(line)) {
                for (col in matchResult.range) {
                    if (input.neighborsFor(row, col).any { it.value.isSymbol() }) {
                        sum += matchResult.value.toInt()
                        break
                    }
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val maybeGears = mutableMapOf<Pair<Int, Int>, Int>()
        for ((row, line) in input.withIndex()) {
            for (matchResult in numeric.findAll(line)) {
                for (col in matchResult.range) {
                    val neighbor = input.neighborsFor(row, col).find { it.value == '*' }
                    if (neighbor?.value == '*') {
                        val existingGear = maybeGears[neighbor.position]
                        if (existingGear != null) {
                            sum += existingGear * matchResult.value.toInt()
                        } else {
                            maybeGears[neighbor.position] = matchResult.value.toInt()
                        }
                        break
                    }
                }
            }
        }
        return sum
    }

    val testInput = readInput("D03T01")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("D03")
    part1(input).println()
    part2(input).println()
}
