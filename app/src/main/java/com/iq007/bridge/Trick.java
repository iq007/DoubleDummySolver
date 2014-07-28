package com.iq007.bridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 23/07/14.
 */
public class Trick {
    int number;
    List<Card> cards;
    TablePosition winner;
    TablePosition starter;

    public Trick(int number, List<Card> cards, TablePosition winner, TablePosition starter) {
        this.number = number;
        this.cards = cards;
        this.winner = winner;
        this.starter = starter;
    }

    public Trick(int number) {
        this.number = number;
        this.cards = new ArrayList<Card>();
    }

    public Trick(TablePosition starter, TablePosition winner, int number) {
        this.starter = starter;
        this.winner = winner;
        this.number = number;
        this.cards = new ArrayList<Card>();
    }

    public Card playCard(Card card){
        cards.add(card);
        return card;
    }

    //return the winner of the trick
    public TablePosition playTrick(int number, List<Card> cards, TablePosition starter){




         return winner;
    }


}
