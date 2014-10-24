package com.iq007.bridge;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by stefan on 20/07/14.
 */
public class Card implements Comparable<Card>, Serializable{
    int value;
    String symbol;
    String suitSymbol;
    Suit suit;

    public Card(){

    }

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;

        if(value >= 2 && value <= 9){
            symbol = String.valueOf(value);
        }
        else if (value == 14){
            symbol = "A";
        }
        else if(value == 10){
            symbol = "T";
        }
        else if (value == 11){
            symbol = "J";
        }
        else if (value == 12){
            symbol = "Q";
        }
        else if (value == 13){
            symbol = "K";
        }

        suitSymbol = BridgeRules.getSymbol(suit);

    }

    public Card(Card card) {
        this.value = card.value;
        this.suit = card.suit;
        this.symbol = card.symbol;
        this.suitSymbol = card.suitSymbol;
    }

    public int getvalue() {
        return value;
    }

    public void setvalue(int value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return suitSymbol + symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (value != card.value) return false;
        if (suit != card.suit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + suit.hashCode();
        return result;
    }

    @Override
    public int compareTo(Card card) {
        if(this.suit.ordinal() > card.suit.ordinal()){
            return 1;
        }
        else if(this.suit.ordinal() < card.suit.ordinal()){
            return -1;
        }
        else
            return this.value - card.value;
    }

    public static Comparator<Card> CardReverseComparator
            = new Comparator<Card>() {
        public int compare(Card card1, Card card2) {
            //descending order
            return card2.compareTo(card1);

        }

    };
}
