private const val current_day = 2

private val input = readDayInput(current_day)
private val example = readDayExample(current_day)

/**
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 */

fun main() {
    println(firstPart(input))
    println(secondPart(input))
}

private fun firstPart(input: List<String>): Int {
    return analyseInput(input)
        .filter { (_, analysedRecords) -> predicate(analysedRecords) }
        .sumOf { (gameId, _) -> gameId }
}

private fun analyseInput(input: List<String>) =
    input.mapIndexed { index, s -> index + 1 to s.substringAfter(':') }
        .map { (gameId, bagRecords) -> gameId to bagRecords.split(";").map { record -> analyseRecords(record) } }
        .map { (gameId, analysedRecord) -> gameId to merge(analysedRecord) }

private fun predicate(analysedRecords: Map<String, Int>) =
    (!analysedRecords.containsKey("red") || analysedRecords["red"]!! <= 12)
            && (!analysedRecords.containsKey("green") || analysedRecords["green"]!! <= 13)
            && (!analysedRecords.containsKey("blue") || analysedRecords["blue"]!! <= 14)

private fun merge(analysedRecord: List<Map<String, Int>>): Map<String, Int> {
    return analysedRecord.flatMap { it.entries }
        .groupBy({ it.key }, { it.value })
        .mapValues { (_, values) -> values.max() }
}


private val recordsRegex = """(\d+) (\w+)""".toRegex()

private fun analyseRecords(record: String): Map<String, Int> {
    return recordsRegex.findAll(record)
        .map(MatchResult::destructured)
        .map { (n, color) -> color to n.toInt() }
        .toMap()
}


private fun secondPart(input: List<String>): Int {
    return analyseInput(input)
        .map { (_, analysedInputs) -> analysedInputs.values.reduce { acc, n -> acc * n } }
        .sum()
}