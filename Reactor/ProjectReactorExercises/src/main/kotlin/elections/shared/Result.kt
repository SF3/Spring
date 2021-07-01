package elections.shared

data class Result(
    val constituency: String,
    val name: String,
    val party: String,
    val votes: Int,
    val percentage: Double,
    val change: String,
    val changeAmount: Double
) {

    //Required by Jackson for marshalling from JSON
    constructor() : this("", "", "", 0, 0.0, "", 0.0)

    override fun toString() = "$name standing in $constituency"
}
