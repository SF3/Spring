package demos.reactor.operators.model

data class Contact(val id: Int, val name: String) {
    constructor() : this(0, "") {}
}
