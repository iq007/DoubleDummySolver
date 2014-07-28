package com.iq007.bridge;

import java.util.List;

/**
 * Created by stefan on 22/07/14.
 */
public class Hand {
    List<Card> cards;
    TablePosition position;

    public Hand(List<Card> cards, TablePosition position) {
        cards.addAll(cards);
        this.position = position;
    }

    public Card playCard(Card card){
        cards.remove(card);
        return card;
    }

    public Card dealCard(Card card){
        cards.add(card);
        return card;
    }
}
