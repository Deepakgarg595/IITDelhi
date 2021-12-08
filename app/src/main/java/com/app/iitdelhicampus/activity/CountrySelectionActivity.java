package com.app.iitdelhicampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.CountryListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.ui.BaseActivity;


public class CountrySelectionActivity extends BaseActivity implements View.OnClickListener {

    private CountryListAdapter adapter;
    private ImageView imgBack;
    private ImageView imgSearch;
    private EditText etSearchCountry;
    private ImageView imgCross;
    private ImageView imgBackSearch;
    private RelativeLayout rlMain;
    private RelativeLayout rlSearchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_selection);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);

        etSearchCountry = (EditText) findViewById(R.id.etSearchCountry);
        imgCross = (ImageView) findViewById(R.id.imgCross);
        imgBackSearch = (ImageView) findViewById(R.id.imgBackSearch);
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        rlSearchItem = (RelativeLayout) findViewById(R.id.rlSearchItem);
        imgBack = (ImageView) findViewById(R.id.imgBack);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.mipmap.back_arrow_icon);
//            actionBar.setTitle(getString(R.string.select_country));
//
//        }

        ListView lsvCountries = (ListView) findViewById(R.id.lsvCountries);
        adapter = new CountryListAdapter(this);
        lsvCountries.setAdapter(adapter);
        lsvCountries.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = adapter.getItem(position).toString();
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_SELECTED_COUNTRY, selectedCountry);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        etSearchCountry.setCursorVisible(true);
        imgSearch.setOnClickListener(this);
        imgBackSearch.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        etSearchCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    adapter.filter(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
////        /*Expanding the search view */
////        searchView.setIconified(false);
////        searchView.setIconifiedByDefault(false);
////        /* Code for changing the textcolor and hint color for the search view */
////
////        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
////        searchAutoComplete.setHintTextColor(Color.WHITE);
////        searchAutoComplete.setTextColor(Color.WHITE);
////
////          /*Code for changing the search icon */
////        ImageView searchIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
////        searchIcon.setImageResource(R.drawable.search_black_icon);
////
////        ImageView searchCloseIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
////        searchCloseIcon.setImageResource(R.mipmap.cross_icon_white);
//////
//////        ImageView searchbackIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.);
//////        searchbackIcon.setImageResource(R.drawable.back_arrow_white);
//
////        searchView.setOnQueryTextListener(new OnQueryTextListener() {
////
////            @Override
////            public boolean onQueryTextSubmit(String query) {
////                return false;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String searchText) {
////                adapter.filter(searchText);
////                return false;
////            }
////        });
//        changeSearchViewTextColor(searchView);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        return true;
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgSearch:
                rlSearchItem.setVisibility(View.VISIBLE);
                rlMain.setVisibility(View.GONE);
                etSearchCountry.requestFocus();
                 openKeyBoard(etSearchCountry);
            break;
            case R.id.imgBackSearch:
                rlSearchItem.setVisibility(View.GONE);
                rlMain.setVisibility(View.VISIBLE);
                break;
            case R.id.imgCross:
                etSearchCountry.setText("");
                break;
            case R.id.imgBack:
                onBackPressed();
                hideSoftKeyBoard();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideSoftKeyBoard();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
