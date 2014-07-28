package com.iq007.bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by stefan on 20/07/14.
 */
public class Deck {


    List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public Deck() {
        cards = new ArrayList<Card>(52);
        for(Suit s: Suit.values()){
            for(int i=1; i<13; i++){
                cards.add(i-1+(s.ordinal()*13), new Card(i+1, s));
            }
            cards.add(12+s.ordinal()*13, new Card(14,s));
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> orderDeck(){
        cards = new ArrayList<Card>(52);
        for(Suit s: Suit.values()){
            for(int i=0; i<13; i++){
                cards.add(i + (s.ordinal() * 13), new Card(i + 1, s));
            }
        }
        return cards;
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public void addCard(Card card){
        if(!cards.contains(card)){
            cards.add(card);
        }
    }

    public void reorderDeck(){

    }

    public List<Card> shuffle() throws BridgeException{
        //Fisher-Yates modern O(n)
        if(cards == null){
            throw new BridgeException("Deck not initialized");
        }
        for(int i = cards.size()-1; i > 0 ; i--){
            Card temp = new Card(cards.get(i));
            int j = randInt(0,i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }

        return cards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    private static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
