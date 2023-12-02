fun main() {
    data class Game(
        val id: Int,
        var r: Int = 0,
        var g: Int = 0,
        var b: Int = 0,
    )

    val regex = """(\d+) ([rgb])""".toRegex()

    fun toGame(line: String): Game {
        val (gameId, gameString) = line.split(':')
        val plays = gameString.split(',')

        val game = Game(
            id = gameId.filter { it.isDigit() }.toInt(),
        )

        for (play in plays) {
            for (result in regex.findAll(play)) {
                val (count, color) = result.destructured
                val prop = when (color) {
                    "r" -> game::r
                    "g" -> game::g
                    "b" -> game::b
                    else -> throw IllegalStateException()
                }
                prop.set(prop.get().coerceAtLeast(count.toInt()))
            }
        }

        return game
    }

    fun part1(input: List<String>): Int {
        val red = 12
        val green = 13
        val blue = 14

        return input.map { line ->
            toGame(line)
        }.filter { game ->
            game.r <= red && game.g <= green && game.b <= blue
        }.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            toGame(line)
        }.sumOf { game ->
            game.r * game.g * game.b
        }
    }

    val testInput = readInput("D02T01")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("D02")
    part1(input).println()
    part2(input).println()
}
