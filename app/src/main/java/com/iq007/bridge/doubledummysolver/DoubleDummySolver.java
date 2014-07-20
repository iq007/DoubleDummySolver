package com.iq007.bridge.doubledummysolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Spinner;

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

        // Create the adapter that will return a fragment for each of the three
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
            // Show 3 total pages.
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
                    return "Test";
            }
            return null;
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemSelectedListener {

        private static final String TAG = "DoubleDummySolver_PlaceholderFragment";

        protected Contract contract;
        protected Deck deck;
        protected String[] mSuitLabels = {Suit.C.name(), Suit.D.name(),Suit.H.name(),Suit.S.name()};
        protected ArrayList<Button> buttonBids = new ArrayList<Button>();

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


            //Deal tab
            if(this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER)==1) {
                rootView = inflater.inflate(R.layout.fragment_deal_grid, container, false);

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

                int row = 1;
                int column = 0;
                Suit currentSuit = Suit.C;
                for (Card card : deck.getCards()) {
                    Button buttonBid = new Button(getActivity());
                    buttonBid.setText(card.toString());
                    buttonBid.setVisibility(Button.INVISIBLE);
                    buttonBids.add(buttonBid);
                    if(card.getSuit() != currentSuit){
                        column = 0;
                        currentSuit = card.getSuit();
                    }
                    row = column / 3 + 1;
                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(column++%3));
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
                        R.id.listItemContract,mSuitLabels);
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
            for (Button bid : buttonBids) {
                if (bid.getText().toString().contains("!"+suit.name())){
                    bid.setVisibility(Button.VISIBLE);
                }
                else {
                    bid.setVisibility(Button.INVISIBLE);
                }
            }

        }
    }



}
