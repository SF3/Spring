package elections.end

import elections.shared.*
import reactor.core.publisher.Flux
import java.util.Comparator.comparing

fun main() {
    namesOfCandidates(fetchResults())
    namesOfCandidatesFromOneParty(fetchResults())
    totalVotesCastForOneParty(fetchResults())
    nameAndPartyOfEveryBelfastCandidate(fetchResults())
    changeInVoteForEveryReturningCandidate(fetchResults())
    candidatesSortedByParty(fetchResults())
    totalVotesForEachParty(fetchResults())
}

fun namesOfCandidates(results: Flux<String>) {
    printTitle("Names of candidates")

    results.map(::mapResult)
        .map(Result::name)
        .subscribe(::printTabbed)
}

fun namesOfCandidatesFromOneParty(results: Flux<String>) {
    printTitle("Names of candidates from one party")

    results.map(::mapResult)
        .filter { it.party == "APNI" }
        .map(Result::name)
        .subscribe(::printTabbed)
}

fun totalVotesCastForOneParty(results: Flux<String>) {
    printTitle("Total votes cast for one party")

    results.map(::mapResult)
        .filter { it.party == "APNI" }
        .map(Result::votes)
        .reduce { a, b -> a + b }
        .subscribe(::printTabbed)
}

fun nameAndPartyOfEveryBelfastCandidate(results: Flux<String>) {
    printTitle("Name and party of every Belfast candidate")

    results.map(::mapResult)
        .filter { it.constituency.startsWith("Belfast") }
        .map { "${it.name} stood for ${it.party} in ${it.constituency}" }
        .subscribe(::printTabbed)
}

fun changeInVoteForEveryReturningCandidate(results: Flux<String>) {
    printTitle("The change in vote for every returning candidate")

    results.map(::mapResult)
        .filter { it.change.isNotEmpty() }
        .sort(comparing(Result::change))
        .subscribe { result ->
            val word = if (result.change == "+") "up" else "down"
            printTabbed("${result.name} went $word by ${result.changeAmount} percent")
        }
}

fun candidatesSortedByParty(results: Flux<String>) {
    fun printTable(input: Map<String, Collection<Result>>) {
        input.forEach { (key, value) ->
            printTabbed("Candidates for stood for $key are:")
            value.forEach { printTabbed("\t${it.name} ") }
            println()
        }
    }

    printTitle("Candidates sorted by party")
    results.map(::mapResult)
        .collectMultimap { it.party }
        .subscribe(::printTable)
}

fun totalVotesForEachParty(results: Flux<String>) {
    printTitle("Total votes for each party")

    results.map(::mapResult)
        .groupBy { it.party }
        .subscribe { groupedFlux ->
            val party = groupedFlux.key()
            groupedFlux
                .map(Result::votes)
                .reduce { a, b -> a + b }
                .subscribe { result ->
                    printTabbed("Total of votes for $party is $result")
                }
        }
}
