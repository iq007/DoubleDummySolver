package com.iq007.bridge.doubledummysolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Layout;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.iq007.bridge.*;

import org.w3c.dom.Text;


public class DoubleDummySolver extends FragmentActivity implements ActionBar.TabListener {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private static final String TAG = "DoubleDummySolver_Activity";

    protected Contract contract;
    protected Deck deck;
    protected Deal deal;


    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccedit);

        /*if (savedInstanceState != null) {
            //Restore the fragment's instance
            mSectionsPagerAdapter. = getFragmentManager().getFragment(
                    savedInstanceState, "mDealFragment");

        }*/


        if(deal == null) {
            if(savedInstanceState != null && savedInstanceState.containsKey("deal")){
                deal = (Deal)savedInstanceState.get("deal");
            }
            else {
                deal = new Deal();
                deal.setCurrentTrick(null);
                deal.setFirst(TablePosition.N);
                deal.setTrump(null);
                Hand northHand = new Hand(null, TablePosition.N);
                Hand eastHand = new Hand(null, TablePosition.E);
                Hand southHand = new Hand(null, TablePosition.S);
                Hand westHand = new Hand(null, TablePosition.W);

                List<Hand> hands = new ArrayList<Hand>();
                hands.add(northHand);
                hands.add(eastHand);
                hands.add(southHand);
                hands.add(westHand);
                deal.setRemainingCards(hands);
                Logger.getLogger(TAG).log(Level.INFO, "init new hand");
            }

        }

        if(deck == null) {
            if (savedInstanceState != null && savedInstanceState.containsKey("deck")) {
                deck = (Deck) savedInstanceState.get("deck");
            } else {
                deck = new Deck();
            }
        }


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
        /*if (id == R.id.action_settings) {
            return true;
        }*/
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

    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getFragmentManager().putFragment(outState, "mDealFragment", mSectionsPagerAdapter.getItem(0));


    }*/

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
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemSelectedListener {


        protected String[] mSuitLabels = {Suit.C.name(), Suit.D.name(), Suit.H.name(), Suit.S.name()};
        protected String[] mContractSuitLabels = {ContractSuit.C.name(), ContractSuit.D.name(), ContractSuit.H.name(), ContractSuit.S.name(), ContractSuit.NT.name()};
        protected ArrayList<CardButton> buttonBids = new ArrayList<CardButton>();



        private static final String TAG = "DoubleDummySolver_PlaceholderFragment";


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
            Logger.getLogger(TAG).log(Level.INFO, "PlaceHolderFragment constructor");
        }

        @Override
        public void onActivityCreated (Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            DoubleDummySolver activity = (DoubleDummySolver) getActivity();



        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            DoubleDummySolver activity = (DoubleDummySolver) getActivity();

            if(activity.deal == null) {
                if (savedInstanceState != null && savedInstanceState.containsKey("deal")) {
                    activity.deal = (Deal) savedInstanceState.get("deal");
                }
            }


            if(activity.deck == null) {
                if (savedInstanceState != null && savedInstanceState.containsKey("deck")) {
                    activity.deck = (Deck) savedInstanceState.get("deck");

                }
            }

            //Deal tab
            if (this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.fragment_deal_grid, container, false);

                BridgeHandLayoutEventListener handLayoutListener = new BridgeHandLayoutEventListener(activity.deal, activity.deck, buttonBids);
                LinearLayout[] layouts = new LinearLayout[4];
                layouts[0] = (LinearLayout) rootView.findViewById(R.id.deal_north_layout);
                layouts[1] = (LinearLayout) rootView.findViewById(R.id.deal_east_layout);
                layouts[2] = (LinearLayout) rootView.findViewById(R.id.deal_south_layout);
                layouts[3] = (LinearLayout) rootView.findViewById(R.id.deal_west_layout);

                for (LinearLayout layout : layouts) {
                    layout.setOnClickListener(handLayoutListener);
                    layout.setOnDragListener(handLayoutListener);
                }

                Deck intactDeck = new Deck();

                GridLayout dealGridView = (GridLayout) rootView.findViewById(R.id.deal_card_buttons_grid);

                /*Spinner dealSuitSpinner = (Spinner) rootView.findViewById(R.id.suit_spinner);
                // Create an ArrayAdapter using the string array and a default spinner layout
                mDealSuitAdapter = new ArrayAdapter(getActivity(), R.layout.contract_list_item,
                        R.id.listItemContract, mSuitLabels);
                // Specify the layout to use when the list of choices appears
                // mDealSuitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                dealSuitSpinner.setAdapter(mDealSuitAdapter);
                dealSuitSpinner.setOnItemSelectedListener(this);*/

                int gridRowCount = dealGridView.getRowCount();
                int row = 0;
                int column = 0;
                int index = 0;

                for (Card card : intactDeck.orderDeck()) {
                    CardButton buttonBid = new CardButton(getActivity(), card);

                    if(activity.deck.getCards().contains(card)){
                        buttonBid.setBackgroundColor(getResources().getColor(R.color.unassigned_deck_card));
                        buttonBid.setOnLongClickListener(handLayoutListener);
                    }
                    else {
                        buttonBid.setBackgroundColor(getResources().getColor(R.color.assigned_deck_card));
                        buttonBid.setOnLongClickListener(null);
                    }

                    buttonBid.setText(card.toString());
                    buttonBid.setTextSize(25);
                    buttonBid.setPadding(5, 5, 5, 5);
                    buttonBid.setMaxHeight(5);
                    buttonBid.setMaxWidth(5);
                    //buttonBid.setVisibility(Button.INVISIBLE);
                    //buttonBid.setOnDragListener(handLayoutListener);
                    buttonBid.setOnClickListener(handLayoutListener);
                    buttonBids.add(buttonBid);



                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(column));
                    dealGridView.addView(buttonBid, lp);

                    if (++row < gridRowCount/* && (++index)%9 > 0*/) {

                    }
                    else {
                        row = 0;
                        column++;
                    }


                }

                if(activity.deal != null){
                    redrawDealTab(activity.deal, rootView);
                }
            }


            //Contract tab
            if (this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.fragment_contract, container, false);

                //populate level list
                int[] mLevels = BridgeRules.instantiateLevels();
                String[] mLevelLabels = Arrays.toString(mLevels).split("[\\[\\]]")[1].split(", ");
                mContractLevelAdapter = new ArrayAdapter(getActivity(), R.layout.contract_list_item,
                        R.id.listItemContract, mLevelLabels);
                ListView mContractLevelListView = (ListView) rootView.findViewById(R.id.listContractLevel);
                mContractLevelListView.setAdapter(mContractLevelAdapter);

                //populate suit list
                mContractSuitAdapter = new ArrayAdapter(getActivity(), R.layout.contract_list_item,
                        R.id.listItemContract, mContractSuitLabels);
                ListView mContractSuitListView = (ListView) rootView.findViewById(R.id.listContractSuit);
                mContractSuitListView.setAdapter(mContractSuitAdapter);

                //populate declarer position at the table list
                String[] mDeclarerLabels = {TablePosition.N.name(), TablePosition.E.name(), TablePosition.S.name(), TablePosition.W.name()};
                mContractDeclarerAdapter = new ArrayAdapter(getActivity(), R.layout.contract_list_item,
                        R.id.listItemContract, mDeclarerLabels);
                ListView mContractDeclarerListView = (ListView) rootView.findViewById(R.id.listContractDeclarer);
                mContractDeclarerListView.setAdapter(mContractDeclarerAdapter);

                //initialize final contract with 1 C by N
                //activity.mContract = new Contract(new Bid(bb.getBids().get(0)),TablePosition.N);
            }

            //Par tab
            if (this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER) == 4) {

                rootView = inflater.inflate(R.layout.fragment_par, container, false);

                //Logger.getLogger(TAG).log(Level.INFO, activity.deal.getRemainingCardsPBN());

                new BridgeWorker(rootView).execute(activity.deal);


            }
            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putSerializable("deal", ((DoubleDummySolver)getActivity()).deal);
            outState.putSerializable("deck", ((DoubleDummySolver)getActivity()).deck);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);

            //redraw all hands
            redrawDealTab(((DoubleDummySolver)getActivity()).deal, this.getView());

            // Checks the orientation of the screen
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Logger.getLogger(TAG).log(Level.INFO, this.getView().getId() + ": Landscape");
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Logger.getLogger(TAG).log(Level.INFO, this.getView().getId() + ": Portrait");
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.v(TAG, "onItemSelected");
            Suit selectedSuit = Suit.C;
            if (position == 1) {
                selectedSuit = Suit.D;
            } else if (position == 2) {
                selectedSuit = Suit.H;
            } else if (position == 3) {
                selectedSuit = Suit.S;
            }
            makeSuitVisible(selectedSuit);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }


        private void makeSuitVisible(Suit suit) {
            for (CardButton bid : buttonBids) {
                if (bid.getCard().getSuit() == suit) {
                    bid.setVisibility(Button.VISIBLE);
                } else {
                    bid.setVisibility(Button.INVISIBLE);
                }
            }

        }

        private static void redrawDealTab(Deal d, View view) {
            for(Hand h: d.getRemainingCards()){
                for(Suit s : Suit.values()){
                    redrawDealTab(h,s,view);
                }
            }
            return;
        }

        private static void redrawDealTab(Hand h, Suit currentSuit, View view) {
            TextView textView = null;
            int handIndex = h.getPosition().ordinal();
            if (currentSuit == Suit.C) {
                switch (handIndex) {
                    case 0:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_north_club);
                        break;
                    case 1:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_east_club);
                        break;
                    case 2:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_south_club);
                        break;
                    case 3:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_west_club);
                        break;

                }

            } else if (currentSuit == Suit.D) {
                switch (handIndex) {
                    case 0:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_north_diamond);
                        break;
                    case 1:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_east_diamond);
                        break;
                    case 2:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_south_diamond);
                        break;
                    case 3:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_west_diamond);
                        break;

                }
            } else if (currentSuit == Suit.H) {
                switch (handIndex) {
                    case 0:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_north_heart);
                        break;
                    case 1:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_east_heart);
                        break;
                    case 2:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_south_heart);
                        break;
                    case 3:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_west_heart);
                        break;

                }
            } else if (currentSuit == Suit.S) {
                switch (handIndex) {
                    case 0:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_north_spade);
                        break;
                    case 1:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_east_spade);
                        break;
                    case 2:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_south_spade);
                        break;
                    case 3:
                        textView = (TextView) view.getRootView().findViewById(R.id.deal_west_spade);
                        break;
                }
            }
            if (textView != null) {
                textView.setText(BridgeRules.getSymbol(currentSuit));
                for (Card c : h.getRemainingCards(currentSuit)) {
                    textView.setText(textView.getText() + " " + c.getSymbol());
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
            shadow = new ColorDrawable(v.getResources().getColor(R.color.card_drag_shadow));
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
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


    private static class BridgeHandLayoutEventListener implements View.OnDragListener, View.OnClickListener, View.OnLongClickListener {

        String TAG = "BridgeHandLayoutEventListener";
        Deal deal = null;
        Deck deck = null;
        protected ArrayList<CardButton> buttonBids = null;

        private BridgeHandLayoutEventListener(Deal d, Deck deck, ArrayList<CardButton> buttonBids) {
            this.deal = d;
            this.deck = deck;
            this.buttonBids = buttonBids;
        }

        @Override
        public boolean onLongClick(View view) {
            Log.v(TAG + " onLongClick ", view.toString());

            if (!(view instanceof CardButton)) {
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


        @Override
        public void onClick(View view) {

            Resources res = view.getRootView().getResources();

            switch (view.getId()) {
                case R.id.deal_north_layout:
                    if(((ColorDrawable) view.getBackground()).getColor() == res.getColor((R.color.table_position_bk))) {
                        view.setBackgroundColor(res.getColor(R.color.table_position_sel));
                    }
                    else{
                        view.setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    }
                    view.getRootView().findViewById(R.id.deal_east_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_south_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_west_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    return;
                case R.id.deal_east_layout:
                    if(((ColorDrawable) view.getBackground()).getColor() == res.getColor((R.color.table_position_bk))) {
                        view.setBackgroundColor(res.getColor(R.color.table_position_sel));
                        //view.setBackground(view.getResources().getDrawable(R.drawable.hand_border));
                    }
                    else{
                        view.setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    }
                    view.getRootView().findViewById(R.id.deal_north_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_south_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_west_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    return;
                case R.id.deal_south_layout:
                    if(((ColorDrawable) view.getBackground()).getColor() == res.getColor((R.color.table_position_bk))) {
                        view.setBackgroundColor(res.getColor(R.color.table_position_sel));
                        //view.setBackground(view.getResources().getDrawable(R.drawable.hand_border));
                    }
                    else{
                        view.setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    }
                    view.getRootView().findViewById(R.id.deal_east_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_north_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_west_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    return;
                case R.id.deal_west_layout:
                    if(((ColorDrawable) view.getBackground()).getColor() == res.getColor((R.color.table_position_bk))) {
                        view.setBackgroundColor(res.getColor(R.color.table_position_sel));
                        // view.setBackground(view.getResources().getDrawable(R.drawable.hand_border));
                    }
                    else{
                        view.setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    }
                    view.getRootView().findViewById(R.id.deal_east_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_south_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    view.getRootView().findViewById(R.id.deal_north_layout).setBackgroundColor(res.getColor((R.color.table_position_bk)));
                    return;
            }

            int buttonIndex = buttonBids.indexOf(view);
            if (buttonIndex >= 0) {
                CardButton currentCardButton = buttonBids.get(buttonIndex);
                int assignedCardColour = view.getResources().getColor(R.color.assigned_deck_card);
                int unassignedCardColour = view.getResources().getColor(R.color.unassigned_deck_card);
                if (((ColorDrawable) currentCardButton.getBackground()).getColor() == assignedCardColour) {
                    buttonBids.get(buttonIndex).setBackgroundColor(unassignedCardColour);
                    currentCardButton.setOnLongClickListener(this);

                    List<Hand> hands = deal.getRemainingCards();
                    out:
                    for (Hand h : hands) {
                        for (Card c : h.getRemainingCards()) {
                            if (c.compareTo(currentCardButton.card) == 0) {
                                h.playCard(c); //removing the card from this hand
                                deck.addCard(c); //and add it back to the deck
                                //TODO: remove card from deal deal.setRemainingCards(?);
                                PlaceholderFragment.redrawDealTab(h, c.getSuit(), view);
                                break out;
                            }
                        }
                    }
                }
                else
                {
                    //TODO: assign the card to the active layout (N,E,S or W)
                    List<Hand> hands = deal.getRemainingCards();

                    View northView = view.getRootView().findViewById(R.id.deal_north_layout);
                    View eastView = view.getRootView().findViewById(R.id.deal_east_layout);
                    View southView = view.getRootView().findViewById(R.id.deal_south_layout);
                    View westView = view.getRootView().findViewById(R.id.deal_west_layout);

                    if(((ColorDrawable) northView.getBackground()).getColor() == res.getColor(R.color.table_position_sel)
                            && hands.get(0).canAcceptCard())
                    {
                        hands.get(0).dealCard(currentCardButton.card);
                        Collections.sort(hands.get(0).getRemainingCards(), Card.CardReverseComparator);
                        deck.removeCard(currentCardButton.card);
                        deal.setRemainingCards(hands);
                        int assignedCardcolor = view.getResources().getColor(R.color.assigned_deck_card);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setBackgroundColor(assignedCardcolor);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setOnLongClickListener(null);

                        PlaceholderFragment.redrawDealTab(hands.get(0), currentCardButton.card.getSuit(), northView);

                    }
                    else if(((ColorDrawable) eastView.getBackground()).getColor() == res.getColor(R.color.table_position_sel)
                            && hands.get(1).canAcceptCard())
                    {
                        hands.get(1).dealCard(currentCardButton.card);
                        Collections.sort(hands.get(1).getRemainingCards(), Card.CardReverseComparator);
                        deck.removeCard(currentCardButton.card);
                        deal.setRemainingCards(hands);
                        int assignedCardcolor = view.getResources().getColor(R.color.assigned_deck_card);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setBackgroundColor(assignedCardcolor);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setOnLongClickListener(null);
                        PlaceholderFragment.redrawDealTab(hands.get(1), currentCardButton.card.getSuit(), eastView);
                    }
                    else if(((ColorDrawable) southView.getBackground()).getColor() == res.getColor(R.color.table_position_sel)
                            && hands.get(2).canAcceptCard())
                    {
                        hands.get(2).dealCard(currentCardButton.card);
                        Collections.sort(hands.get(2).getRemainingCards(), Card.CardReverseComparator);
                        deck.removeCard(currentCardButton.card);
                        deal.setRemainingCards(hands);
                        int assignedCardcolor = view.getResources().getColor(R.color.assigned_deck_card);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setBackgroundColor(assignedCardcolor);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setOnLongClickListener(null);
                        PlaceholderFragment.redrawDealTab(hands.get(2), currentCardButton.card.getSuit(), southView);
                    }
                    else if(((ColorDrawable) westView.getBackground()).getColor() == res.getColor(R.color.table_position_sel)
                        && hands.get(3).canAcceptCard())
                    {
                        hands.get(3).dealCard(currentCardButton.card);
                        Collections.sort(hands.get(3).getRemainingCards(), Card.CardReverseComparator);
                        deck.removeCard(currentCardButton.card);
                        deal.setRemainingCards(hands);
                        int assignedCardcolor = view.getResources().getColor(R.color.assigned_deck_card);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setBackgroundColor(assignedCardcolor);
                        buttonBids.get((Suit.values().length - currentCardButton.card.getSuit().ordinal() - 1) * 13 + (14-currentCardButton.card.getvalue())).setOnLongClickListener(null);
                        PlaceholderFragment.redrawDealTab(hands.get(3), currentCardButton.card.getSuit(), westView);
                    }
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
                    switch (dragData.charAt(0)) {
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
                    if (dragData.length() == 3) {
                        sCardValue = sCardValue.concat(Character.toString(dragData.charAt(2)));
                    }
                    Card card = new Card(Integer.parseInt(sCardValue), currentSuit);
                    List<Hand> remainingCards = deal.getRemainingCards();

                    Hand currentHand = null;

                    int handIndex = 0; //init with 0 - North
                    int viewId = 0;
                    switch (view.getId()) {
                        case R.id.deal_north_layout:
                            handIndex = 0;
                            break;
                        case R.id.deal_east_layout:
                            handIndex = 1;
                            break;
                        case R.id.deal_south_layout:
                            handIndex = 2;
                            break;
                        case R.id.deal_west_layout:
                            handIndex = 3;
                            break;

                    }

                    if(!remainingCards.get(handIndex).canAcceptCard())
                        return false;

                    remainingCards.get(handIndex).dealCard(card);
                    deck.removeCard(card);
                    currentHand = (Hand) remainingCards.get(handIndex);

                    deal.setRemainingCards(remainingCards);

                    Collections.sort(currentHand.getRemainingCards(), Card.CardReverseComparator);

                    //Logger.getLogger(TAG).log(Level.INFO, view.getId() + " " + card.toString());

                    int assignedCardcolor = view.getResources().getColor(R.color.assigned_deck_card);
                    buttonBids.get(currentSuit.ordinal() * 13 + card.getvalue() - 2).setBackgroundColor(assignedCardcolor);
                    buttonBids.get(currentSuit.ordinal() * 13 + card.getvalue() - 2).setOnLongClickListener(null);

                    PlaceholderFragment.redrawDealTab(currentHand, currentSuit, view);


                    // Invalidates the view to force a redraw
                    view.invalidate();

                    // Returns true. DragEvent.getResult() will return true.
                    return true;
            }
            return false;
        }
    }


    private static class BridgeWorker extends AsyncTask<Deal, Integer, Object[]> {

        private View rootView;

        private BridgeWorker(View rootView) {
            this.rootView = rootView;
        }

        static {
            System.loadLibrary("ddsLib");
        }

        public native int SolveBoard(Deal d);
        public native int[][] CalcDDtablePBN(String dealPBN);
        public native char[][] CalcParPBN(String dealPBN, int vulnerability);


        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Object[] res) {

            int[][] results = new int[5][4];

            int k=0;
            for (Object obj: res){
                results[k++] = (int[]) obj;
            }

            TableLayout parTableLayout = (TableLayout) rootView.findViewById(R.id.par_table_layout);

            TableRow[] parRows = new TableRow[4];

            parRows[0] = (TableRow) parTableLayout.getChildAt(1);
            parRows[1] = (TableRow) parTableLayout.getChildAt(2);
            parRows[2] = (TableRow) parTableLayout.getChildAt(3);
            parRows[3] = (TableRow) parTableLayout.getChildAt(4);


            for(int i=0;i<results.length;i++){ //denomination
                for(int j=0;j<results[i].length;j++) { //declarer
                    //Logger.getLogger(TAG).log(Level.INFO, "[" + i + ":" + j + "] =" + results[i][j]);
                    TextView par = new TextView(rootView.getContext());
                    par.setText(Integer.toString(results[i][j]));
                    switch(j) { //because results are in order: NESW and table is NSEW
                        case 0:
                        case 3:
                            parRows[j].addView(par);
                            break;
                        case 1:
                            parRows[j+1].addView(par);
                            break;
                        case 2:
                            parRows[j-1].addView(par);
                            break;
                    }
                    par.invalidate();

                }
            }

            parTableLayout.invalidate();

        }

        @Override
        protected int[][] doInBackground(Deal... objects) {
            Deal d = objects[0];
            int[][] results = CalcDDtablePBN(d.getRemainingCardsPBN());
            //CalcParPBN(d.getRemainingCardsPBN(),0);
            return results;
        }
    }

}




