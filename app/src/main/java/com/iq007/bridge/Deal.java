package com.iq007.bridge;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stefan on 22/07/14.
 */
public class Deal implements Serializable{
    List<Hand> remainingCards;
    ContractSuit trump;
    TablePosition first;
    Card[] currentTrick = new Card[3]; /* 0-2 for up to 3 cards in the order played */

    public Deal(List<Hand> remainingCards, ContractSuit trump, TablePosition first, Card[] currentTrick) {
        this.remainingCards = remainingCards;
        this.trump = trump;
        this.first = first;
        this.currentTrick = currentTrick;
    }

    public Deal(){
        remainingCards = null;
        trump = ContractSuit.C;
        first = TablePosition.N;
        currentTrick = null;
    }

    public List<Hand> getRemainingCards() {
        return remainingCards;
    }

    public String getRemainingCardsPBN(){
        StringBuffer remainingCardsPBN = new StringBuffer();
        String firstPBN;
        switch(first) {
            case N:
                firstPBN = "N:";
                break;
            case E:
                firstPBN = "E:";
                break;
            case S:
                firstPBN = "S:";
                break;
            case W:
                firstPBN = "W:";
                break;
            default:
                firstPBN = "N:";
        }
        remainingCardsPBN.append(firstPBN);

        for(Hand h: remainingCards){
            for(Suit s: Suit.values()) {
                for (Card c : h.getRemainingCards(s)) {
                    remainingCardsPBN.append(c.getSymbol());
                }
                remainingCardsPBN.append(".");
            }
            remainingCardsPBN.append(" ");
        }


        return remainingCardsPBN.toString();

    }

    public void setRemainingCards(List<Hand> remainingCards) {
        this.remainingCards = remainingCards;
    }

    public ContractSuit getTrump() {
        return trump;
    }

    public void setTrump(ContractSuit trump) {
        this.trump = trump;
    }

    public TablePosition getFirst() {
        return first;
    }

    public void setFirst(TablePosition first) {
        this.first = first;
    }

    public Card[] getCurrentTrick() {
        return currentTrick;
    }

    public void setCurrentTrick(Card[] currentTrick) {
        this.currentTrick = currentTrick;
    }

}
