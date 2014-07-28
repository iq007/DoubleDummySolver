package com.iq007.bridge;

/**
 * Created by stefan on 20/07/14.
 */
public class Card {
    int value;
    String symbol;
    String suitSymbol;
    Suit suit;

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

        switch(suit){
            case C:
            {
                suitSymbol = "♣";
                break;
            }
            case D:
            {
                suitSymbol = "♦";
                break;
            }
            case H:
            {
                suitSymbol = "♥";
                break;
            }
            case S:
            {
                suitSymbol = "♠";
                break;
            }
        }
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
}
