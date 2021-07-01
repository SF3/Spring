package elections.finish;

import elections.common.Result;
import elections.common.Utils;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Map;

import static elections.common.Utils.*;
import static java.util.Comparator.comparing;

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

        results.map(Utils::mapResult)
                .filter(r -> r.getParty().equals("APNI"))
                .map(Result::getName)
                .subscribe(Utils::printTabbed);
    }

    private static void totalVotesCastForOneParty(Flux<String> results) {
        printTitle("Total votes cast for one party");

        results.map(Utils::mapResult)
                .filter(r -> r.getParty().equals("APNI"))
                .map(Result::getVotes)
                .reduce(Integer::sum)
                .subscribe(Utils::printTabbed);
    }

    private static void nameAndPartyOfEveryBelfastCandidate(Flux<String> results) {
        printTitle("Name and party of every Belfast candidate");

        results.map(Utils::mapResult)
                .filter(r -> r.getConstituency().startsWith("Belfast"))
                .map(r -> String.format("%s stood for %s in %s", r.getName(), r.getParty(), r.getConstituency()))
                .subscribe(Utils::printTabbed);
    }

    private static void changeInVoteForEveryReturningCandidate(Flux<String> results) {
        printTitle("The change in vote for every returning candidate");

        results.map(Utils::mapResult)
                .filter(r -> r.getChange() != null)
                .sort(comparing(Result::getChange))
                .subscribe(result -> {
                    String word = result.getChange().equals("+") ? "up" : "down";
                    String msg = "%s went %s by %.2f percent";
                    printTabbed(String.format(msg, result.getName(), word, result.getChangeAmount()));
                });
    }

    private static void printTable(Map<String, Collection<Result>> input) {
        input.forEach((key, value) -> {
            printTabbed(String.format("Candidates for stood for %s are:", key));
            value.forEach(r -> printTabbed("\t" + r.getName()));
            System.out.println();
        });
    }

    private static void candidatesSortedByParty(Flux<String> results) {
        printTitle("Candidates sorted by party");
        results.map(Utils::mapResult)
                .collectMultimap(Result::getParty)
                .subscribe(Program::printTable);
    }

    private static void totalVotesForEachParty(Flux<String> results) {
        printTitle("Total votes for each party");

        results.map(Utils::mapResult)
                .groupBy(Result::getParty)
                .subscribe(groupedFlux -> {
                    String party = groupedFlux.key();
                    groupedFlux
                            .map(Result::getVotes)
                            .reduce(Integer::sum)
                            .subscribe(result ->
                                    printTabbed(String.format("Total of votes for %s is %d", party, result))
                            );
                });
    }
}
