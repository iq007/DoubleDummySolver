package com.iq007.bridge.doubledummysolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.iq007.bridge.*;


public class DoubleDummySolver extends Activity implements ActionBar.TabListener {



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private static final String TAG = "DoubleDummySolver_Activity";

    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccedit);



        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // Create the adapter that will return a fragment for each of the four
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));

        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ccedit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final String TAG = "DoubleDummySolver_SectionsPagerAdapter";

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.main_tab1).toUpperCase(l);
                case 1:
                    return getString(R.string.main_tab2).toUpperCase(l);
                case 2:
                    return getString(R.string.main_tab3).toUpperCase(l);
                case 3:
                    return "Par".toUpperCase();
            }
            return null;
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemSelectedListener{

        static {
            System.loadLibrary("ddsLib");
        }

        public native int SolveBoard(Deal d);

        private static final String TAG = "DoubleDummySolver_PlaceholderFragment";

        protected Contract contract;
        protected Deck deck;
        protected Deal deal;
        protected String[] mSuitLabels = {Suit.C.name(), Suit.D.name(),Suit.H.name(),Suit.S.name()};
        protected String[] mContractSuitLabels = {ContractSuit.C.name(), ContractSuit.D.name(),ContractSuit.H.name(),ContractSuit.S.name(), ContractSuit.NT.name()};
        protected ArrayList<CardButton> buttonBids = new ArrayList<CardButton>();

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        ArrayAdapter mContractLevelAdapter;
        ArrayAdapter mContractSuitAdapter;
        ArrayAdapter mContractDeclarerAdapter;
        ArrayAdapter mDealSuitAdapter;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = null;
            DoubleDummySolver activity = (DoubleDummySolver) getActivity();
            deal = new Deal();
            deal = new Deal();

           /* //init random deal
            try {
                if(deck == null){
                    deck = new Deck();
                }
                deck.shuffle();
            }
            catch(BridgeException e){
                Log.e(TAG, e.toString());
                return rootView;
            }*/


            deal.setCurrentTrick(null);
            deal.setFirst(null);
            deal.setTrump(null);

            Hand northHand = new Hand(null, TablePosition.N);
            Hand eastHand = new Hand(null, TablePosition.E);
            Hand southHand = new Hand(null, TablePosition.S);
            Hand westHand = new Hand(null, TablePosition.W);

           /* int i = 0;
            for(Card card : deck.getCards()) {
                switch(i%4){
                    case 0:
                        northHand.dealCard(card);
                        break;
                    case 1:
                        eastHand.dealCard(card);
                        break;
                    case 2:
                        southHand.dealCard(card);
                        break;
                    case 3:
                        westHand.dealCard(card);
                        break;

                }
                //deck.removeCard(card);
                i++;

            }*/
            List<Hand> hands = new ArrayList<Hand>();
            hands.add(northHand);
            hands.add(eastHand);
            hands.add(southHand);
            hands.add(westHand);
            deal.setRemainingCards(hands);


            //Deal tab
            if(this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER)==1) {
                rootView = inflater.inflate(R.layout.fragment_deal_grid, container, false);

                Log.d(TAG, Integer.toString(SolveBoard(null)));


                BridgeHandLayoutEventListener handLayoutListener = new BridgeHandLayoutEventListener(deal, buttonBids);
                LinearLayout[] layouts = new LinearLayout[4];
                layouts[0] = (LinearLayout) rootView.findViewById(R.id.deal_north_layout);
                layouts[1] = (LinearLayout) rootView.findViewById(R.id.deal_east_layout);
                layouts[2] = (LinearLayout) rootView.findViewById(R.id.deal_south_layout);
                layouts[3] = (LinearLayout) rootView.findViewById(R.id.deal_west_layout);

                for(LinearLayout layout :  layouts) {
                    layout.setOnClickListener(handLayoutListener);
                    layout.setOnDragListener(handLayoutListener);
                }

                deck = new Deck();

                GridLayout dealGridView = (GridLayout) rootView.findViewById(R.id.deal_grid);

                Spinner dealSuitSpinner = (Spinner) rootView.findViewById(R.id.suit_spinner);
                // Create an ArrayAdapter using the string array and a default spinner layout
                mDealSuitAdapter = new ArrayAdapter(getActivity(),R.layout.contract_list_item,
                        R.id.listItemContract,mSuitLabels);
                // Specify the layout to use when the list of choices appears
                // mDealSuitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                dealSuitSpinner.setAdapter(mDealSuitAdapter);
                dealSuitSpinner.setOnItemSelectedListener(this);

                int gridRowCount = dealGridView.getRowCount();
                int gridColCount = dealGridView.getColumnCount();

                int row = 0;
                int column = 0;
                Suit currentSuit = Suit.C;
                for (Card card : deck.getCards()) {
                    CardButton buttonBid = new CardButton(getActivity(),card);
                    buttonBid.setBackgroundColor(Color.CYAN);
                    buttonBid.setText(card.toString());
                    buttonBid.setTextSize(25);
                    buttonBid.setPadding(0, 0, 0, 0);
                    buttonBid.setMaxHeight(5);
                    buttonBid.setMaxWidth(5);
                    buttonBid.setVisibility(Button.INVISIBLE);
                    buttonBid.setOnLongClickListener(handLayoutListener);
                    //buttonBid.setOnDragListener(handLayoutListener);
                    buttonBid.setOnClickListener(handLayoutListener);
                    buttonBids.add(buttonBid);
                    if(card.getSuit() != currentSuit){
                        row = 0; column = 0;
                        currentSuit = card.getSuit();
                    }
                    if(column >= gridColCount){
                        column=0;
                        row++;
                    }
                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(column++));
                    dealGridView.addView(buttonBid, lp);
                }
                makeSuitVisible(Suit.C);
            }


            //Contract tab
            if(this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER)==2) {
                rootView = inflater.inflate(R.layout.fragment_contract, container, false);

                //populate level list
                int[] mLevels = BridgeRules.instantiateLevels();
                String[] mLevelLabels =  Arrays.toString(mLevels).split("[\\[\\]]")[1].split(", ");
                mContractLevelAdapter = new ArrayAdapter(getActivity(),R.layout.contract_list_item,
                        R.id.listItemContract,mLevelLabels);
                ListView mContractLevelListView = (ListView) rootView.findViewById(R.id.listContractLevel);
                mContractLevelListView.setAdapter(mContractLevelAdapter);

                //populate suit list
                mContractSuitAdapter = new ArrayAdapter(getActivity(),R.layout.contract_list_item,
                        R.id.listItemContract,mContractSuitLabels);
                ListView mContractSuitListView = (ListView) rootView.findViewById(R.id.listContractSuit);
                mContractSuitListView.setAdapter(mContractSuitAdapter);

                //populate declarer position at the table list
                String[] mDeclarerLabels = {TablePosition.N.name(), TablePosition.E.name(), TablePosition.S.name(),TablePosition.W.name()};
                mContractDeclarerAdapter = new ArrayAdapter(getActivity(),R.layout.contract_list_item,
                        R.id.listItemContract,mDeclarerLabels);
                ListView mContractDeclarerListView = (ListView) rootView.findViewById(R.id.listContractDeclarer);
                mContractDeclarerListView.setAdapter(mContractDeclarerAdapter);

                //initialize final contract with 1 C by N
                //activity.mContract = new Contract(new Bid(bb.getBids().get(0)),TablePosition.N);
            }

            //Par tab
            if(this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER)==4) {


                //call native function

            }
            return rootView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.v(TAG, "onItemSelected");
            Suit selectedSuit = Suit.C;
            if(position == 1){
                selectedSuit = Suit.D;
            }
            else if(position == 2){
                selectedSuit = Suit.H;
            }
            else if(position == 3){
                selectedSuit = Suit.S;
            }
            makeSuitVisible(selectedSuit);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


        private void makeSuitVisible(Suit suit){
            for (CardButton bid : buttonBids) {
                if (bid.getCard().getSuit() == suit){
                    bid.setVisibility(Button.VISIBLE);
                }
                else {
                    bid.setVisibility(Button.INVISIBLE);
                }
            }

        }




    }



    private static class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);

            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics (Point size, Point touch){
            // Defines local variables
            int width, height;

            // Sets the width of the shadow to half the width of the original View
            width = getView().getWidth();

            // Sets the height of the shadow to half the height of the original View
            height = getView().getHeight();

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width, height);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {

            // Draws the ColorDrawable in the Canvas passed in from the system.
            shadow.draw(canvas);
        }
    }

    private static class BridgeHandLayoutEventListener implements View.OnDragListener, View.OnClickListener, View.OnLongClickListener{


        @Override
        public boolean onLongClick(View view) {
            Log.v(TAG + " onLongClick ", view.toString());

            if(!(view instanceof CardButton)){
                return false;
            }

            CardButton bidButton = (CardButton) view;
            Log.v(TAG, bidButton.getText().toString());


            Card card = new Card(bidButton.getCard());

            ClipData clipData = ClipData.newPlainText(card.toString(), card.getSuit().name() + card.getvalue());

            // Instantiates the drag shadow builder.
            View.DragShadowBuilder myShadow = new MyDragShadowBuilder(bidButton);

            // Starts the drag

            view.startDrag(clipData,  // the data to be dragged
                    myShadow,  // the drag shadow builder
                    null,      // no need to use local data
                    0          // flags (not currently used, set to 0)
            );


            return false;
        }

        String TAG = "BridgeHandLayoutEventListener";
        Deal deal = null;
        protected ArrayList<CardButton> buttonBids = null;

        private BridgeHandLayoutEventListener(Deal d,  ArrayList<CardButton> buttonBids) {
            this.deal = d;
            this.buttonBids = buttonBids;
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.deal_north_layout:
                    view.setBackground(view.getResources().getDrawable(R.drawable.hand_border));
                    return;



            }

            //TODO: check if view is CardButton and if yes and is greyed implement "get back to deck" function
            int buttonIndex = buttonBids.indexOf(view);
            if(buttonIndex >= 0){
                CardButton currentCardButton = buttonBids.get(buttonIndex);
                if(((ColorDrawable)currentCardButton.getBackground()).getColor() == Color.GRAY) {
                    buttonBids.get(buttonIndex).setBackgroundColor(Color.CYAN);
                    //TODO: remove this card from dealt cards and from layout view

                    currentCardButton.setOnLongClickListener(this);

                }

            }
        }

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    view.setBackgroundColor(Color.BLUE);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    view.setBackgroundColor(Color.WHITE);
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    view.setBackgroundColor(Color.RED);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    view.setBackgroundColor(Color.BLUE);
                    return true;
                case DragEvent.ACTION_DROP:
                    // Gets the item containing the dragged data
                    ClipData.Item item = dragEvent.getClipData().getItemAt(0);

                    // Gets the text data from the item.
                    CharSequence dragData = item.getText();

                    Suit currentSuit = Suit.C;
                    switch(dragData.charAt(0)){
                        case 'C':
                            currentSuit = Suit.C;
                            break;
                        case 'D':
                            currentSuit = Suit.D;
                            break;
                        case 'H':
                            currentSuit = Suit.H;
                            break;
                        case 'S':
                            currentSuit = Suit.S;
                            break;
                    }

                    String sCardValue = Character.toString(dragData.charAt(1));
                    if(dragData.length() == 3){
                        sCardValue = sCardValue.concat(Character.toString(dragData.charAt(2)));
                    }
                    Card card = new Card(Integer.parseInt(sCardValue), currentSuit);
                    List<Hand> remainingCards = deal.getRemainingCards();

                    int index = 0;
                    switch(view.getId()){
                        case R.id.deal_north_layout:
                            remainingCards.get(0).dealCard(card);
                            //TODO: make the card visible in the grid
                            TextView textView = null;
                            if(currentSuit == Suit.C){
                                textView = (TextView)view.getRootView().findViewById(R.id.deal_north_club);

                            }
                            else if(currentSuit == Suit.D){
                                textView = (TextView)view.getRootView().findViewById(R.id.deal_north_diamond);

                            }
                            else if(currentSuit == Suit.H){
                                textView = (TextView)view.getRootView().findViewById(R.id.deal_north_heart);

                            }
                            else if(currentSuit == Suit.S){
                                textView = (TextView)view.getRootView().findViewById(R.id.deal_north_spade);

                            }

                            if(textView != null){
                                textView.setText(textView.getText() + " " + card.getSymbol());
                                //TODO: add BridgeRules.sortDesc() and call it;
                            }

                            //TODO: grey out card in the deck and
                            Logger.getLogger(TAG).log(Level.INFO, view.getId() + " " + card.toString());
                            break;
                        case R.id.deal_east_layout:
                            remainingCards.get(1).dealCard(card);
                            Logger.getLogger(TAG).log(Level.INFO, view.getId() + " " + card.toString());
                            break;
                        case R.id.deal_south_layout:
                            remainingCards.get(2).dealCard(card);
                            break;
                        case R.id.deal_west_layout:
                            remainingCards.get(3).dealCard(card);
                            break;

                    }

                    buttonBids.get(currentSuit.ordinal()*13+card.getvalue()-2).setBackgroundColor(Color.GRAY);
                    buttonBids.get(currentSuit.ordinal()*13+card.getvalue()-2).setOnLongClickListener(null);


                    // Invalidates the view to force a redraw
                    view.invalidate();

                    // Returns true. DragEvent.getResult() will return true.
                    return true;
            }
            return false;
        }
    }

}

