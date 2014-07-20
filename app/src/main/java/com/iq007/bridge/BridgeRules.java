package com.iq007.bridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 18/07/14.
 */
public class  BridgeRules {
    private static BiddingBox mBiddingBox = null;

    public static BiddingBox instantiateBiddingBox(){

        if(mBiddingBox != null)
           return mBiddingBox;

        final List<Bid> bids = new ArrayList<Bid>();
        for(int level = 1; level<=7; level++){
            for (ContractSuit suit : ContractSuit.values()){
                bids.add(new Bid(level, suit, level + " !" + suit.toString()));
            }
        }
        mBiddingBox = new BiddingBox(bids);
        return mBiddingBox;
    }

    public static int[] instantiateLevels(){
        int[] levels = {1,2,3,4,5,6,7};
        return levels;
    }

    public static Bid firstBid() throws BridgeException
    {
        if (mBiddingBox == null)
            throw new BridgeException("BiddingBox not initialized");
        Bid firstBid = new Bid((Bid)mBiddingBox.getBids().get(0));
        return firstBid;
    }
}
