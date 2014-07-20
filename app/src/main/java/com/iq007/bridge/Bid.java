package com.iq007.bridge;

/**
 * Created by stefan on 18/07/14.
 */
public class Bid implements Comparable<Bid>{

    int level;
    ContractSuit suit;
    String displayName;

    public Bid(int level, ContractSuit suit, String displayName){
        this.level = level;
        this.suit = suit;
        this.displayName = displayName;
    }

    public Bid(Bid bid){
        this.level = bid.getLevel();
        this.suit = bid.getSuit();
        this.displayName = bid.getDisplayName();
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ContractSuit getSuit() {
        return suit;
    }

    public void setSuit(ContractSuit suit) {
        this.suit = suit;
    }

    @Override
    public int compareTo(Bid another) {
        return this.level - another.level;

    }
}
