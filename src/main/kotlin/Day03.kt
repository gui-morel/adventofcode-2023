private const val current_day = 3;

private val input = readDayInput(current_day)
private val example = readDayExample(current_day)

/**
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 */

fun main() {
    println(firstPart(input))
    println(secondPart(example))
}

private data class Position(val x: Int, val y: Int)
private data class Schematic(val number: Int, val position: Set<Position>)

private class EngineSchematicParser {
    private var currentNumber = 0;
    private var currentPosition: Set<Position> = emptySet()

    private var schematics = emptyList<Schematic>()
    private var symbolPositions = emptyList<Position>()

    fun parse(char: Char, position: Position) {
        if (char == '.') {
            this.end()
        } else if (char.isDigit()) {
            currentNumber = currentNumber * 10 + char.digitToInt()
            currentPosition = currentPosition + position
        } else {
            symbolPositions = symbolPositions + position
            this.end()
        }
    }

    fun end() {
        if (currentNumber != 0) {
            schematics = schematics + Schematic(currentNumber, currentPosition)
            currentNumber = 0
            currentPosition = emptySet()
        }
    }

    fun schematicIncluded(): Set<Schematic> {
        return schematics.filter { schematic ->
            symbolPositions.any { symbolPosition ->
                (adjacent(symbolPosition) intersect schematic.position).isNotEmpty()
            }
        }.toSet()
    }

    private fun adjacent(position: Position): List<Position> {
        val (x, y) = position
        return listOf(
            Position(x - 1, y - 1),
            Position(x - 1, y),
            Position(x - 1, y + 1),
            Position(x, y - 1),
            Position(x, y),
            Position(x, y + 1),
            Position(x + 1, y - 1),
            Position(x + 1, y),
            Position(x + 1, y + 1),
        )
    }
}

private fun firstPart(input: List<String>): Int {
    val parser = EngineSchematicParser()
    input.mapIndexed { y, line ->
        line.mapIndexed { x, char -> parser.parse(char, Position(x, y)) }
        parser.end()
    }
    return parser.schematicIncluded().sumOf(Schematic::number)
}

private fun secondPart(input: List<String>): Int {
    return -1
}