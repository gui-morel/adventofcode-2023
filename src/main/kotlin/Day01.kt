private const val current_day = 1;

fun main() {
    val dayInput = readDayInput(current_day)
    println(firstPart(dayInput))
    println(secondPart(dayInput))
}

private fun String.digitize(): String = when (this) {
    "one" -> "1"
    "two" -> "2"
    "three" -> "3"
    "four" -> "4"
    "five" -> "5"
    "six" -> "6"
    "seven" -> "7"
    "eight" -> "8"
    "nine" -> "9"
    else -> this
}

private fun firstPart(input: List<String>): Int {
    val matcher = """(?=(\d|one|two|three|four|five|six|seven|eight|nine))""".toRegex()

    val clibrationValues = input.map { row ->
        matcher.findAll(row)
            .map { it.groupValues.last() }.toList()
    }.map { rowDigits ->
        rowDigits.first().digitize() + rowDigits.last().digitize()
    }
        .map(String::toInt)
    return clibrationValues.sum()
}

private fun secondPart(input: List<String>): Int {
    val digits = input.map { row ->
        row.filter { char -> char.isDigit() }
    }
    val calibrationValues = digits.map { rowDigit -> """${rowDigit.first()}${rowDigit.last()}""".toInt() }
    return calibrationValues.sum()
}