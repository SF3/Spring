package elections.start;

import elections.common.Result;
import elections.common.Utils;
import reactor.core.publisher.Flux;

import static elections.common.Utils.*;

public class Program {
    public static void main(String[] args) {
        namesOfCandidates(fetchResults());
        namesOfCandidatesFromOneParty(fetchResults());
        totalVotesCastForOneParty(fetchResults());
        nameAndPartyOfEveryBelfastCandidate(fetchResults());
        changeInVoteForEveryReturningCandidate(fetchResults());
        candidatesSortedByParty(fetchResults());
        totalVotesForEachParty(fetchResults());
    }

    private static void namesOfCandidates(Flux<String> results) {
        printTitle("Names of candidates");

        results.map(Utils::mapResult)
                .map(Result::getName)
                .subscribe(Utils::printTabbed);
    }

    private static void namesOfCandidatesFromOneParty(Flux<String> results) {
        printTitle("Names of candidates from one party");
    }

    private static void totalVotesCastForOneParty(Flux<String> results) {
        printTitle("Total votes cast for one party");
    }

    private static void nameAndPartyOfEveryBelfastCandidate(Flux<String> results) {
        printTitle("Name and party of every Belfast candidate");
    }

    private static void changeInVoteForEveryReturningCandidate(Flux<String> results) {
        printTitle("The change in vote for every returning candidate");
    }

    private static void candidatesSortedByParty(Flux<String> results) {
        printTitle("Candidates sorted by party");
    }

    private static void totalVotesForEachParty(Flux<String> results) {
        printTitle("Total votes for each party");
    }
}
