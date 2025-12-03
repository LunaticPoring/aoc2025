import java.io.File

enum class InputType {
    SAMPLE,
    ACTUAL
}

fun readAllLines(inputType: InputType, day: String) = File(filePath(inputType, day)).readLines()
fun readAllFile(inputType: InputType, day: String) = File(filePath(inputType, day)).readText()

private fun filePath(inputType: InputType, day : String) = "inputs/${day}/${inputType.name.lowercase()}.txt"
