package com.iq007.bridge;

/**
 * Created by stefan on 18/07/14.
 */
public class Contract {

    Bid finalBid;
    TablePosition declarer;

    public Contract(Bid finalBid, TablePosition declarer) {
        this.finalBid = finalBid;
        this.declarer = declarer;
    }

    public Bid getFinalBid() {
        return finalBid;
    }

    public void setFinalBid(Bid finalBid) {
        this.finalBid = finalBid;
    }

    public TablePosition getDeclarer() {
        return declarer;
    }

    public void setDeclarer(TablePosition declarer) {
        this.declarer = declarer;
    }


}
