package elections.begin

import elections.shared.*

import reactor.core.publisher.Flux

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
}

fun totalVotesCastForOneParty(results: Flux<String>) {
    printTitle("Total votes cast for one party")
}

fun nameAndPartyOfEveryBelfastCandidate(results: Flux<String>) {
    printTitle("Name and party of every Belfast candidate")
}

fun changeInVoteForEveryReturningCandidate(results: Flux<String>) {
    printTitle("The change in vote for every returning candidate")
}

fun candidatesSortedByParty(results: Flux<String>) {
    printTitle("Candidates sorted by party")
}

fun totalVotesForEachParty(results: Flux<String>) {
    printTitle("Total votes for each party")
}
