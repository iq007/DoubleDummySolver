package com.iq007.bridge;

import java.util.Iterator;
import java.util.List;

/**
 * Created by stefan on 18/07/14.
 */
public class BiddingBox implements Iterable<Bid>{

    private List<Bid> bids;


    public BiddingBox(List<Bid> bids) {
        //maybe copy array so it does not get modified outside
        this.bids = bids;
    }

    public List<Bid> getBids() {
        return bids;
    }

    @Override
    public Iterator<Bid> iterator() {
        return new Iterator<Bid>() {
            private int currentBid = 0;
            @Override
            public boolean hasNext() {
                return currentBid < bids.size();

            }

            @Override
            public Bid next() {
                return bids.get(currentBid++);
            }

            @Override
            public void remove() {

            }
        };
    }
}
