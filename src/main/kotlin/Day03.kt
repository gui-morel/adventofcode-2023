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
    println(secondPart(input))
}

private data class Position(val x: Int, val y: Int)
private data class Schematic(val number: Int, val position: Set<Position>)
private data class Symbol(val symbol: Char, val position: Position)

private class EngineSchematicParser {
    private var currentNumber = 0;
    private var currentPosition: Set<Position> = emptySet()

    private var schematics = emptyList<Schematic>()
    private var symbols = emptyList<Symbol>()

    fun parse(char: Char, position: Position) {
        if (char == '.') {
            this.end()
        } else if (char.isDigit()) {
            currentNumber = currentNumber * 10 + char.digitToInt()
            currentPosition = currentPosition + position
        } else {
            symbols = symbols + Symbol(char, position)
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
            symbols.any { symbol ->
                (adjacent(symbol.position) intersect schematic.position).isNotEmpty()
            }
        }.toSet()
    }


    fun gears(): List<Pair<Schematic, Schematic>> {
        return symbols.filter { symbol -> symbol.symbol == '*' }
            .map { symbol ->
                schematics.filter { schematic -> (adjacent(symbol.position) intersect schematic.position).isNotEmpty() }
            }
            .filter { adjacentSchematics -> adjacentSchematics.size == 2 }
            .map { schematics -> schematics.first() to schematics.last() }
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
    val parser = EngineSchematicParser()
    input.mapIndexed { y, line ->
        line.mapIndexed { x, char -> parser.parse(char, Position(x, y)) }
        parser.end()
    }
    return parser.gears()
        .map { (first, second) -> first.number * second.number }
        .sum()
}