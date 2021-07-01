package simpsons.begin

import simpsons.shared.fetchSimpsons
import simpsons.shared.subscribeWithTitle

val haircutRegex = "^ *\\(#+\\) *$".toRegex()

fun main() {
    simpsonsFamily()
    margeHaircut()
    homerAtMoes()
    homerIsUpstairs()
    simpsonsCloned()
    homerIsOnRHS()
    simpsonsStacked()
}

private fun simpsonsFamily() {
    fetchSimpsons().subscribeWithTitle("The Simpsons Family")
}

private fun margeHaircut() {
    //Remove any lines that only contain Marge's hair
}

private fun homerAtMoes() {
    //Remove Homer from the picture
}

private fun homerIsUpstairs() {
    //Put Homer above the other characters
    //Hint - you can subscribe multiple times
}

private fun simpsonsCloned() {
    //Print the Simpsons beside one another
    //Hint - you can assume no line is longer than 80 chars
    //Hint - you might want to use 'zipWith'
}

private fun homerIsOnRHS() {
    //Move Homer to the right hand side of the picture
    //NB you cannot assume the maximum line length
    //Hint - you might want to use 'collect'
}

private fun simpsonsStacked() {
    //Print the characters on top of one another
    //Hint - you might want to use 'reduce'
}


