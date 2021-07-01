package elections.common;

public class Result {
    private String constituency;
    private String name;
    private String party;
    private int votes;
    private double percentage;
    private String change;
    private double changeAmount;

    public Result(String constituency, String name, String party, int votes, double percentage, String change, double changeAmount) {
        this.constituency = constituency;
        this.name = name;
        this.party = party;
        this.votes = votes;
        this.percentage = percentage;
        this.change = change;
        this.changeAmount = changeAmount;
    }

    //Required by Jackson for marshalling from JSON
    public Result() {
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    @Override
    public String toString() {
        return String.format("%s standing in %s", name, constituency);
    }
}
