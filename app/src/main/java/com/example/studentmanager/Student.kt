data class Student(val name: String, val studentId: String) {
    override fun toString(): String {
        return "Student: $name\nID: $studentId"
    }
}
