package demos.reactor.operators.model

data class Name(val first: String, val last: String) {
    constructor() : this("", "")

    override fun toString() = "$first $last"
}
