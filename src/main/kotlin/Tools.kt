fun readDayInput(day: Int) =
    object {}.javaClass.getResourceAsStream("""day${day}/input""")?.bufferedReader()?.readLines()!!

fun readDayExample(day: Int) =
    object {}.javaClass.getResourceAsStream("""day${day}/example""")?.bufferedReader()?.readLines()!!