package com.iq007.bridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 22/07/14.
 */
public class Hand {

    TablePosition position;
    List<Card> remainingCards;


    public Hand(List<Card> cards, TablePosition position) {
        remainingCards = new ArrayList<Card>();
        if(cards != null) {
            remainingCards.addAll(cards);
        }
        this.position = position;
    }

    public Card playCard(Card card){
        remainingCards.remove(card);
        return card;
    }

    public Card dealCard(Card card){
        remainingCards.add(card);
        return card;
    }

    public TablePosition getPosition() {
        return position;
    }

    public List<Card> getRemainingCards() {
        return remainingCards;
    }

    public List<Card> getRemainingCards(Suit s){
        List<Card> remainingCardsInSuit = new ArrayList<Card>();
        for(Card c: remainingCards){
            if(s.compareTo(c.getSuit()) == 0){
                remainingCardsInSuit.add(c);
            }
        }
        return remainingCardsInSuit;
    }

}
