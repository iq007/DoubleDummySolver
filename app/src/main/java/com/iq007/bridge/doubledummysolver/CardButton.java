package com.iq007.bridge.doubledummysolver;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.iq007.bridge.Card;

/**
 * Created by stefan on 21/07/14.
 */
public class CardButton extends Button {


    Card card = null;
    public CardButton(Context context) {
        super(context);
    }

    public CardButton(Context context, Card card) {
        super(context);
        this.card= card;
    }

    public CardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardButton(Context context, AttributeSet attrs, Card card) {
        super(context, attrs);
        this.card = card;
    }

    public CardButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
