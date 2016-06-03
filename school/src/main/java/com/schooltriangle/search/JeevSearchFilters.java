package com.schooltriangle.search;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.mylibrary.session.JeevomSession;
import com.schooltriangle.mylibrary.session.UserCurrentLocationManager;
import com.schooltriangle.mylibrary.util.CommonCode;
import com.schooltriangle.mylibrary.util.InputStreamToString;
import com.schooltriangle.mylibrary.util.JeevOMUtil;
import com.schooltriangle.mylibrary.util.MyUrlConnectionClient;
import com.schooltriangle.pojos.JeevCriteria;
import com.schooltriangle.pojos.JeevSearchAvailability;
import com.schooltriangle.pojos.JeevSearchCategory;
import com.schooltriangle.pojos.JeevSearchCriteria;
import com.schooltriangle.pojos.JeevSearchFilter;
import com.schooltriangle.pojos.JeevSearchGender;
import com.schooltriangle.pojos.JeevSearchRequisition;
import com.schooltriangle.pojos.JeevSearchTiming;
import com.schooltriangle.pojos.LocationHints;
import com.schooltriangle.pojos.SaveSearchRequest;
import com.schooltriangle.pojos.Suggestions;
import com.schooltriangle.pojos.SuggestionsResponse;
import com.schooltriangle.service.interfaces.Search;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.schooltriangle.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

public class JeevSearchFilters extends ActionBarActivity implements
        OnClickListener {
    UserCurrentLocationManager locationManager;


    private final int REQ_CODE_SEARCH_WHAT = 100;
    int distance;
    boolean isSearchButtonEnabled = false;
    SeekBar distance_seek_bar;
    CheckBox near_me;
    String search_what_final;
    AutoCompleteTextView searchEditText, search_area;
    List<Suggestions> completeSuggestions;
    List<String> locationHints;
    TextView any_gender, male, female;
    TextView distance_count;
    TextView any_timing, morning, afternoon, evening;
    TextView any_day, mon, tue, wed, thru, fri, sat, sun;
    TextView valued, discount, verified;
    boolean isNearMeChecked;
    String nameOfSavedSearch;

    private enum stateValues {
        UNSELECTED, SELECTED
    }

    TextView service_mode_count, service_category_count;

    ImageView finish_view;
    TextView search_view;
    JeevSearchFilters context;

    // service categories
    ImageView any_category, doctor_clinic_category, hospital_nursing_category,
            lab_diagnostic_category, pharmacy_category, gym_fitness_category,
            spa_yoga_category, medicine_category, support_category,
            healing_category;
    LinearLayout any_service_cat_layout, doctor_clinic_cat_layout,
            hospital_nursing_cat_layout, lab_diagnostic_cat_layout,
            pharmacy_cat_layout, gym_fitness_cat_layout, spa_yoga_cat_layout,
            medicine_cat_layout, support_cat_layout, healing_cat_layout;

    // Service Modes
    ImageView any_service_mode, email_service_mode, video_service_mode,
            chat_service_mode, phone_service_mode, home_service_mode,
            clinic_service_mode;

    LinearLayout any_service, email_service, video_service, chat_service,
            phone_service, home_service, clinic_service;

    JeevCriteria jeevCriteria;
    JeevSearchFilter filter;
    JeevSearchRequisition requisition;
    JeevSearchCategory category;
    JeevSearchTiming timing;
    JeevSearchAvailability availability;
    JeevSearchGender gender;
    JeevSearchCriteria jeevSearchCriteria;
    TextView top_bar_title;
    // 0 for advance search and 1 for
    // fiter
    int typeOfActivity;
    boolean isModifySearch;
    private JeevomSession jeevomSession;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeev_search_filter_layout);
        context = this;
        locationManager = new UserCurrentLocationManager(getApplicationContext());
        hideKeyboard();

        jeevomSession = new JeevomSession(getApplicationContext());
        if (!CommonCode.isNullOrEmpty(jeevomSession.getAuthToken())) {
            authToken = "Basic " + jeevomSession.getAuthToken();
        }

        Intent intent = getIntent();
        jeevSearchCriteria = new JeevSearchCriteria();
        jeevCriteria = (JeevCriteria) intent.getSerializableExtra("criteria");
        filter = (JeevSearchFilter) intent.getSerializableExtra("filter");
        typeOfActivity = intent.getIntExtra("activity_type", 0);
        isNearMeChecked = intent.getBooleanExtra("isNearMeChecked", false);

        if (intent.hasExtra("isModifySearch")) {
            isModifySearch = intent.getBooleanExtra("isModifySearch", false);
            nameOfSavedSearch = intent.getStringExtra("nameOfSavedSearch");
        }
        top_bar_title = (TextView) findViewById(R.id.top_bar_title);

        if (typeOfActivity == 0) {
            top_bar_title.setText(getResources().getString(
                    R.string.advance_search));
        } else {
            top_bar_title.setText("Filter");
        }


        // modes object
        requisition = jeevCriteria.getRequisition();

        // category object
        category = jeevCriteria.getCategory();
        // timing object
        timing = jeevCriteria.getTiming();

        // day object
        availability = jeevCriteria.getAvailability();

        // gender
        gender = jeevCriteria.getGender();

        // auto complete text view
        searchEditText = (AutoCompleteTextView) findViewById(R.id.searchEditText);
        // on touch listner on what edit text
        searchEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (searchEditText.getRight() - searchEditText
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
                            .width())) {
                        promptSpeechInput(searchEditText);

                        return true;
                    }
                }
                return false;
            }
        });
        if (!CommonCode.isNullOrEmpty(jeevCriteria.getSearchString())) {

            searchEditText.setText(jeevCriteria.getSearchString());
        }
        SuggestionAdapter adapter = new SuggestionAdapter(context);
        adapter.notifyDataSetChanged();

        searchEditText.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                search_what_final = null;

                checkSearchButtonState();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        searchEditText.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                search_what_final = completeSuggestions.get(position)
                        .getKeyWord();

                InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
            }
        });

        search_area = (AutoCompleteTextView) findViewById(R.id.search_area);
        AreaSuggestionAdapter locationAdapter = new AreaSuggestionAdapter(
                context);
        locationAdapter.notifyDataSetChanged();
        search_area.setAdapter(locationAdapter);
        search_area.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {

                search_area.setText(locationHints.get(position));
                InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(search_area.getWindowToken(), 0);

            }
        });

        service_mode_count = (TextView) findViewById(R.id.service_mode_count);
        service_category_count = (TextView) findViewById(R.id.service_category_count);
        // cancel button
        finish_view = (ImageView) findViewById(R.id.finish_view);
        finish_view.setOnClickListener(context);

        // search button
        search_view = (TextView) findViewById(R.id.search_view);
        search_view.setOnClickListener(context);

        if (isModifySearch) {
            search_view.setText("Save");
        }

        // service modes references
        any_service_mode = (ImageView) findViewById(R.id.any_service_mode);
        email_service_mode = (ImageView) findViewById(R.id.email_service_mode);
        video_service_mode = (ImageView) findViewById(R.id.video_service_mode);
        chat_service_mode = (ImageView) findViewById(R.id.chat_service_mode);
        phone_service_mode = (ImageView) findViewById(R.id.phone_service_mode);
        home_service_mode = (ImageView) findViewById(R.id.home_service_mode);
        clinic_service_mode = (ImageView) findViewById(R.id.clinic_service_mode);

        any_service = (LinearLayout) findViewById(R.id.any_service);
        email_service = (LinearLayout) findViewById(R.id.email_service);
        video_service = (LinearLayout) findViewById(R.id.video_service);
        chat_service = (LinearLayout) findViewById(R.id.chat_service);
        phone_service = (LinearLayout) findViewById(R.id.phone_service);
        home_service = (LinearLayout) findViewById(R.id.home_service);
        clinic_service = (LinearLayout) findViewById(R.id.clinic_service);

        // Set service modes based on intent values from home screen
        setServiceModes();

        // service category references
        any_category = (ImageView) findViewById(R.id.any_category);
        doctor_clinic_category = (ImageView) findViewById(R.id.doctor_clinic_category);
        hospital_nursing_category = (ImageView) findViewById(R.id.hospital_nursing_category);
        lab_diagnostic_category = (ImageView) findViewById(R.id.lab_diagnostic_category);
        pharmacy_category = (ImageView) findViewById(R.id.pharmacy_category);
        gym_fitness_category = (ImageView) findViewById(R.id.gym_fitness_category);
        spa_yoga_category = (ImageView) findViewById(R.id.spa_yoga_category);
        medicine_category = (ImageView) findViewById(R.id.medicine_category);
        support_category = (ImageView) findViewById(R.id.support_category);
        healing_category = (ImageView) findViewById(R.id.healing_category);

        any_service_cat_layout = (LinearLayout) findViewById(R.id.any_service_cat_layout);
        doctor_clinic_cat_layout = (LinearLayout) findViewById(R.id.doctor_clinic_cat_layout);
        hospital_nursing_cat_layout = (LinearLayout) findViewById(R.id.hospital_nursing_cat_layout);
        lab_diagnostic_cat_layout = (LinearLayout) findViewById(R.id.lab_diagnostic_cat_layout);
        pharmacy_cat_layout = (LinearLayout) findViewById(R.id.pharmacy_cat_layout);
        gym_fitness_cat_layout = (LinearLayout) findViewById(R.id.gym_fitness_cat_layout);
        spa_yoga_cat_layout = (LinearLayout) findViewById(R.id.spa_yoga_cat_layout);
        medicine_cat_layout = (LinearLayout) findViewById(R.id.medicine_cat_layout);
        support_cat_layout = (LinearLayout) findViewById(R.id.support_cat_layout);
        healing_cat_layout = (LinearLayout) findViewById(R.id.healing_cat_layout);

        setServiceCategories();

        // timing textview references
        any_timing = (TextView) findViewById(R.id.any_timing);
        morning = (TextView) findViewById(R.id.morning);
        afternoon = (TextView) findViewById(R.id.afternoon);
        evening = (TextView) findViewById(R.id.evening);

        setUpTiming();

        any_day = (TextView) findViewById(R.id.any_day);
        mon = (TextView) findViewById(R.id.monday);
        tue = (TextView) findViewById(R.id.tuesday);
        wed = (TextView) findViewById(R.id.wednesday);
        thru = (TextView) findViewById(R.id.thrusday);
        fri = (TextView) findViewById(R.id.friday);
        sat = (TextView) findViewById(R.id.saturday);
        sun = (TextView) findViewById(R.id.sunday);

        setUpDay();

        any_gender = (TextView) findViewById(R.id.any_gender);
        male = (TextView) findViewById(R.id.male_gender);
        female = (TextView) findViewById(R.id.female_gender);

        setUpGender();

        distance_count = (TextView) findViewById(R.id.distance_count);
        distance_seek_bar = (SeekBar) findViewById(R.id.distance_seek_bar);
        distance_seek_bar
                .setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {

                        distance = progress;
                        if (progress < 2) {
                            distance_count.setText(String.valueOf(2) + " Km.");
                        } else {

                            if (progress == 100) {
                                distance_count.setText("Any");
                                distance = 0;
                            } else {
                                distance_count.setText(String.valueOf(progress)
                                        + " Km.");
                            }
                        }

                    }
                });

        // near me

        near_me = (CheckBox) findViewById(R.id.near_me);
        near_me.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    search_area.setEnabled(true);
                    distance_seek_bar.setEnabled(true);
                } else {
                    search_area.setEnabled(false);
                    distance_seek_bar.setEnabled(false);
                    search_area.setText("");
                }
            }
        });

        // set up location
        setUpLocation();

        valued = (TextView) findViewById(R.id.valued);
        discount = (TextView) findViewById(R.id.discount);
        verified = (TextView) findViewById(R.id.verified);

        valued.setOnClickListener(context);
        discount.setOnClickListener(context);
        verified.setOnClickListener(context);

        setUpAttributes();

        if (isModifySearch) {
            searchEditText.setText(jeevCriteria.getSearchString());
        }
    }

    private void setUpAttributes() {
        valued.setTag(stateValues.UNSELECTED);
        discount.setTag(stateValues.UNSELECTED);
        verified.setTag(stateValues.UNSELECTED);

        if (jeevCriteria.isDiscount()) {
            discount.setTextColor(getResources().getColor(R.color.green_dark));
            discount.setTag(stateValues.SELECTED);
        }
        if (jeevCriteria.isValued()) {
            valued.setTextColor(getResources().getColor(R.color.green_dark));
            valued.setTag(stateValues.SELECTED);
        }
        if (jeevCriteria.isVerified()) {
            verified.setTextColor(getResources().getColor(R.color.green_dark));
            verified.setTag(stateValues.SELECTED);
        }

    }

    private void updateAttributes(String value) {

        if (value.equals("discount")) {
            if (discount.getTag() == stateValues.UNSELECTED) {
                discount.setTextColor(getResources().getColor(
                        R.color.green_dark));
                discount.setTag(stateValues.SELECTED);
                jeevCriteria.setDiscount(true);
            } else {
                discount.setTextColor(getResources().getColor(R.color.Black));
                discount.setTag(stateValues.UNSELECTED);
                jeevCriteria.setDiscount(false);
            }
        }

        if (value.equals("valued")) {
            if (valued.getTag() == stateValues.UNSELECTED) {
                valued.setTextColor(getResources().getColor(R.color.green_dark));
                valued.setTag(stateValues.SELECTED);
                jeevCriteria.setValued(true);
            } else {
                valued.setTextColor(getResources().getColor(R.color.Black));
                valued.setTag(stateValues.UNSELECTED);
                jeevCriteria.setValued(false);
            }
        }
        if (value.equals("verified")) {
            if (verified.getTag() == stateValues.UNSELECTED) {
                verified.setTextColor(getResources().getColor(
                        R.color.green_dark));
                verified.setTag(stateValues.SELECTED);
                jeevCriteria.setVerified(true);
            } else {
                verified.setTextColor(getResources().getColor(R.color.Black));
                verified.setTag(stateValues.UNSELECTED);
                jeevCriteria.setVerified(false);
            }
        }

    }

    private void setUpLocation() {

        if (!CommonCode.isNullOrEmpty(filter.getLocation())) {
            search_area.setText(filter.getLocation());
        }

        distance = filter.getDistance();
        distance_seek_bar.setProgress(distance);

        if (distance < 0) {
            distance = 2;
            distance_count.setText(String.valueOf(distance) + " Km.");
            distance_seek_bar.setProgress(distance);
        } else if (distance == 0) {
            distance_count.setText("Any");
            distance_seek_bar.setProgress(100);
        } else {
            distance_count.setText(String.valueOf(distance) + " Km.");
            distance_seek_bar.setProgress(distance);
        }

        if (isNearMeChecked) {
            near_me.setChecked(true);
            search_area.setEnabled(true);
            distance_seek_bar.setEnabled(true);
        } else {
            near_me.setChecked(false);
            search_area.setEnabled(false);
            distance_seek_bar.setEnabled(false);
        }

        if (isNearMeChecked) {
            if ((CommonCode.isNullOrEmpty(search_area.getText().toString().trim()))) {

                near_me.setChecked(false);
                search_area.setEnabled(false);
                distance_seek_bar.setEnabled(false);
            }

        }
    }

    private void promptSpeechInput(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            if (v.getId() == R.id.searchEditText) {
                startActivityForResult(intent, REQ_CODE_SEARCH_WHAT);
            }

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SEARCH_WHAT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchEditText.setText(result.get(0));
                }
                break;
            }

        }
    }

    private void updateServiceModes(String value) {
        if (value.equals("any")) {
            requisition.setChat(false);
            requisition.setClinic(false);
            requisition.setEmail(false);
            requisition.setHome(false);
            requisition.setPhone(false);
            requisition.setVideo(false);

            email_service_mode.setImageResource(R.drawable.email_filter);
            email_service_mode.setTag(stateValues.UNSELECTED);
            requisition.setEmail(false);

            chat_service_mode.setImageResource(R.drawable.chat_filter);
            chat_service_mode.setTag(stateValues.UNSELECTED);
            requisition.setChat(false);

            clinic_service_mode.setImageResource(R.drawable.in_clinic_filter);
            clinic_service_mode.setTag(stateValues.UNSELECTED);
            requisition.setClinic(false);

            home_service_mode.setImageResource(R.drawable.home_filter);
            home_service_mode.setTag(stateValues.UNSELECTED);
            requisition.setHome(false);

            phone_service_mode.setImageResource(R.drawable.phone_filter);
            phone_service_mode.setTag(stateValues.UNSELECTED);
            requisition.setPhone(false);

            video_service_mode.setImageResource(R.drawable.video_filter);
            video_service_mode.setTag(stateValues.UNSELECTED);
            requisition.setVideo(false);

        } else {
            if (value.equals("email")) {

                if (email_service_mode.getTag() == stateValues.UNSELECTED) {
                    email_service_mode
                            .setImageResource(R.drawable.email_filter_selected);
                    email_service_mode.setTag(stateValues.SELECTED);
                    requisition.setEmail(true);
                } else {
                    email_service_mode
                            .setImageResource(R.drawable.email_filter);
                    email_service_mode.setTag(stateValues.UNSELECTED);
                    requisition.setEmail(false);
                }

            }
            if (value.equals("video")) {
                if (video_service_mode.getTag() == stateValues.UNSELECTED) {
                    video_service_mode
                            .setImageResource(R.drawable.video_filter_selected);
                    video_service_mode.setTag(stateValues.SELECTED);
                    requisition.setVideo(true);
                } else {
                    video_service_mode
                            .setImageResource(R.drawable.video_filter);
                    video_service_mode.setTag(stateValues.UNSELECTED);
                    requisition.setVideo(false);
                }

            }
            if (value.equals("chat")) {
                if (chat_service_mode.getTag() == stateValues.UNSELECTED) {
                    chat_service_mode
                            .setImageResource(R.drawable.chat_filter_selected);
                    chat_service_mode.setTag(stateValues.SELECTED);
                    requisition.setChat(true);
                } else {
                    chat_service_mode.setImageResource(R.drawable.chat_filter);
                    chat_service_mode.setTag(stateValues.UNSELECTED);
                    requisition.setChat(false);
                }
            }
            if (value.equals("phone")) {
                if (phone_service_mode.getTag() == stateValues.UNSELECTED) {
                    phone_service_mode
                            .setImageResource(R.drawable.phone_filter_selected);
                    phone_service_mode.setTag(stateValues.SELECTED);
                    requisition.setPhone(true);
                } else {
                    phone_service_mode
                            .setImageResource(R.drawable.phone_filter);
                    phone_service_mode.setTag(stateValues.UNSELECTED);
                    requisition.setPhone(false);
                }
            }
            if (value.equals("home")) {
                if (home_service_mode.getTag() == stateValues.UNSELECTED) {
                    home_service_mode
                            .setImageResource(R.drawable.home_filter_selected);
                    home_service_mode.setTag(stateValues.SELECTED);
                    requisition.setHome(true);
                } else {
                    home_service_mode.setImageResource(R.drawable.home_filter);
                    home_service_mode.setTag(stateValues.UNSELECTED);
                    requisition.setHome(false);
                }
            }
            if (value.equals("clinic")) {
                if (clinic_service_mode.getTag() == stateValues.UNSELECTED) {
                    clinic_service_mode
                            .setImageResource(R.drawable.inclinic_filter_selected);
                    clinic_service_mode.setTag(stateValues.SELECTED);
                    requisition.setClinic(true);
                } else {
                    clinic_service_mode
                            .setImageResource(R.drawable.in_clinic_filter);
                    clinic_service_mode.setTag(stateValues.UNSELECTED);
                    requisition.setClinic(false);
                }
            }

            any_service_mode.setImageResource(R.drawable.any_filter);
            any_service_mode.setTag(stateValues.UNSELECTED);
        }
        updateServiceModeCountValues();
        checkSearchButtonState();
    }

    private void updateServiceCategoryCountValues() {
        int count = 0;
        if (category.isAlternateMedicine())
            count++;
        if (category.isDoctorClinic())
            count++;
        if (category.isGymFitness())
            count++;
        if (category.isHealing())
            count++;
        if (category.isHealthcareSupport())
            count++;
        if (category.isHospitalNursing())
            count++;
        if (category.isLabDiagnostic())
            count++;
        if (category.isPharmacies())
            count++;
        if (category.isSpaWellness())
            count++;

        if (count != 0) {
            service_category_count.setVisibility(View.VISIBLE);
            service_category_count.setText(count + " Selected");
        } else {
            any_category.setImageResource(R.drawable.any_filter_selected);
            any_service_mode.setTag(stateValues.SELECTED);
            service_category_count.setVisibility(View.INVISIBLE);
        }
    }

    private void setServiceCategories() {

        any_service_cat_layout.setOnClickListener(context);
        doctor_clinic_cat_layout.setOnClickListener(context);
        hospital_nursing_cat_layout.setOnClickListener(context);
        lab_diagnostic_cat_layout.setOnClickListener(context);
        pharmacy_cat_layout.setOnClickListener(context);
        gym_fitness_cat_layout.setOnClickListener(context);
        spa_yoga_cat_layout.setOnClickListener(context);
        medicine_cat_layout.setOnClickListener(context);
        support_cat_layout.setOnClickListener(context);
        healing_cat_layout.setOnClickListener(context);

        any_category.setTag(stateValues.UNSELECTED);
        medicine_category.setTag(stateValues.UNSELECTED);
        doctor_clinic_category.setTag(stateValues.UNSELECTED);
        gym_fitness_category.setTag(stateValues.UNSELECTED);
        healing_category.setTag(stateValues.UNSELECTED);
        support_category.setTag(stateValues.UNSELECTED);
        hospital_nursing_category.setTag(stateValues.UNSELECTED);
        lab_diagnostic_category.setTag(stateValues.UNSELECTED);
        pharmacy_category.setTag(stateValues.UNSELECTED);
        spa_yoga_category.setTag(stateValues.UNSELECTED);

        if (category.isAlternateMedicine()) {
            medicine_category
                    .setImageResource(R.drawable.medicine_filter_selected);
            medicine_category.setTag(stateValues.SELECTED);
        }

        if (category.isDoctorClinic()) {
            doctor_clinic_category
                    .setImageResource(R.drawable.doctor_filter_selected);
            doctor_clinic_category.setTag(stateValues.SELECTED);
        }
        if (category.isGymFitness()) {
            gym_fitness_category
                    .setImageResource(R.drawable.fitness_filter_selected);
            gym_fitness_category.setTag(stateValues.SELECTED);
        }
        if (category.isHealing()) {
            healing_category
                    .setImageResource(R.drawable.ic_jeev_healing_selected);
            healing_category.setTag(stateValues.SELECTED);
        }
        if (category.isHealthcareSupport()) {
            support_category
                    .setImageResource(R.drawable.support_filter_selected);
            support_category.setTag(stateValues.SELECTED);
        }
        if (category.isHospitalNursing()) {
            hospital_nursing_category
                    .setImageResource(R.drawable.hospita_filter_selected);
            hospital_nursing_category.setTag(stateValues.SELECTED);
        }

        if (category.isLabDiagnostic()) {
            lab_diagnostic_category
                    .setImageResource(R.drawable.lab_filter_selected);
            lab_diagnostic_category.setTag(stateValues.SELECTED);
        }

        if (category.isPharmacies()) {
            pharmacy_category
                    .setImageResource(R.drawable.pharmacy_filter_selected);
            pharmacy_category.setTag(stateValues.SELECTED);
        }

        if (category.isSpaWellness()) {
            spa_yoga_category
                    .setImageResource(R.drawable.wellness_filter_selected);
            spa_yoga_category.setTag(stateValues.SELECTED);
        }

        if (!category.isAlternateMedicine() && !category.isDoctorClinic()
                && !category.isGymFitness() && !category.isHealing()
                && !category.isHealthcareSupport()
                && !category.isHospitalNursing() && !category.isPharmacies()
                && !category.isLabDiagnostic() && !category.isSpaWellness()) {
            any_category.setImageResource(R.drawable.any_filter_selected);
            any_category.setTag(stateValues.SELECTED);

        }
        updateServiceCategoryCountValues();

        checkSearchButtonState();
    }

    private void updateServiceCategories(String value) {
        if (value.equals("any")) {
            doctor_clinic_category.setImageResource(R.drawable.doctor_filter);
            doctor_clinic_category.setTag(stateValues.UNSELECTED);
            category.setDoctorClinic(false);

            hospital_nursing_category
                    .setImageResource(R.drawable.hospital_filter);
            hospital_nursing_category.setTag(stateValues.UNSELECTED);
            category.setHospitalNursing(false);

            lab_diagnostic_category.setImageResource(R.drawable.lab_filter);
            lab_diagnostic_category.setTag(stateValues.UNSELECTED);
            category.setLabDiagnostic(false);

            pharmacy_category.setImageResource(R.drawable.pharmacy_filter);
            pharmacy_category.setTag(stateValues.UNSELECTED);
            category.setPharmacies(false);

            gym_fitness_category.setImageResource(R.drawable.fitness_filter);
            gym_fitness_category.setTag(stateValues.UNSELECTED);
            category.setGymFitness(false);

            spa_yoga_category.setImageResource(R.drawable.wellness_filter);
            spa_yoga_category.setTag(stateValues.UNSELECTED);
            category.setSpaWellness(false);

            medicine_category.setImageResource(R.drawable.medicine_filter);
            medicine_category.setTag(stateValues.UNSELECTED);
            category.setAlternateMedicine(false);

            support_category.setImageResource(R.drawable.support_filter);
            support_category.setTag(stateValues.UNSELECTED);
            category.setHealthcareSupport(false);

            healing_category.setImageResource(R.drawable.ic_jeev_healing);
            healing_category.setTag(stateValues.UNSELECTED);
            category.setHealing(false);
        } else {
            if (value.equals("doctor_clinic")) {

                if (doctor_clinic_category.getTag() == stateValues.UNSELECTED) {
                    doctor_clinic_category
                            .setImageResource(R.drawable.doctor_filter_selected);
                    doctor_clinic_category.setTag(stateValues.SELECTED);
                    category.setDoctorClinic(true);
                } else {
                    doctor_clinic_category
                            .setImageResource(R.drawable.doctor_filter);
                    doctor_clinic_category.setTag(stateValues.UNSELECTED);
                    category.setDoctorClinic(false);
                }

            }
            if (value.equals("hospital_nursing")) {
                if (hospital_nursing_category.getTag() == stateValues.UNSELECTED) {
                    hospital_nursing_category
                            .setImageResource(R.drawable.hospita_filter_selected);
                    hospital_nursing_category.setTag(stateValues.SELECTED);
                    category.setHospitalNursing(true);
                } else {
                    hospital_nursing_category
                            .setImageResource(R.drawable.hospital_filter);
                    hospital_nursing_category.setTag(stateValues.UNSELECTED);
                    category.setHospitalNursing(false);
                }

            }
            if (value.equals("lab_diagnostic")) {
                if (lab_diagnostic_category.getTag() == stateValues.UNSELECTED) {
                    lab_diagnostic_category
                            .setImageResource(R.drawable.lab_filter_selected);
                    lab_diagnostic_category.setTag(stateValues.SELECTED);
                    category.setLabDiagnostic(true);
                } else {
                    lab_diagnostic_category
                            .setImageResource(R.drawable.lab_filter);
                    lab_diagnostic_category.setTag(stateValues.UNSELECTED);
                    category.setLabDiagnostic(false);
                }
            }
            if (value.equals("pharmacy")) {
                if (pharmacy_category.getTag() == stateValues.UNSELECTED) {
                    pharmacy_category
                            .setImageResource(R.drawable.pharmacy_filter_selected);
                    pharmacy_category.setTag(stateValues.SELECTED);
                    category.setPharmacies(true);
                } else {
                    pharmacy_category
                            .setImageResource(R.drawable.pharmacy_filter);
                    pharmacy_category.setTag(stateValues.UNSELECTED);
                    category.setPharmacies(false);
                }
            }
            if (value.equals("gym_fitness")) {
                if (gym_fitness_category.getTag() == stateValues.UNSELECTED) {
                    gym_fitness_category
                            .setImageResource(R.drawable.fitness_filter_selected);
                    gym_fitness_category.setTag(stateValues.SELECTED);
                    category.setGymFitness(true);
                } else {
                    gym_fitness_category
                            .setImageResource(R.drawable.fitness_filter);
                    gym_fitness_category.setTag(stateValues.UNSELECTED);
                    category.setGymFitness(false);
                }
            }
            if (value.equals("spa_yoga")) {
                if (spa_yoga_category.getTag() == stateValues.UNSELECTED) {
                    spa_yoga_category
                            .setImageResource(R.drawable.wellness_filter_selected);
                    spa_yoga_category.setTag(stateValues.SELECTED);
                    category.setSpaWellness(true);
                } else {
                    spa_yoga_category
                            .setImageResource(R.drawable.wellness_filter);
                    spa_yoga_category.setTag(stateValues.UNSELECTED);
                    category.setSpaWellness(false);
                }
            }

            if (value.equals("medicine")) {
                if (medicine_category.getTag() == stateValues.UNSELECTED) {
                    medicine_category
                            .setImageResource(R.drawable.medicine_filter_selected);
                    medicine_category.setTag(stateValues.SELECTED);
                    category.setAlternateMedicine(true);
                } else {
                    medicine_category
                            .setImageResource(R.drawable.medicine_filter);
                    medicine_category.setTag(stateValues.UNSELECTED);
                    category.setAlternateMedicine(false);
                }
            }
            if (value.equals("support")) {
                if (support_category.getTag() == stateValues.UNSELECTED) {
                    support_category
                            .setImageResource(R.drawable.support_filter_selected);
                    support_category.setTag(stateValues.SELECTED);
                    category.setHealthcareSupport(true);
                } else {
                    support_category
                            .setImageResource(R.drawable.support_filter);
                    support_category.setTag(stateValues.UNSELECTED);
                    category.setHealthcareSupport(false);
                }
            }
            if (value.equals("healing")) {
                if (healing_category.getTag() == stateValues.UNSELECTED) {
                    healing_category
                            .setImageResource(R.drawable.ic_jeev_healing_selected);
                    healing_category.setTag(stateValues.SELECTED);
                    category.setHealing(true);
                } else {
                    healing_category
                            .setImageResource(R.drawable.ic_jeev_healing);
                    healing_category.setTag(stateValues.UNSELECTED);
                    category.setHealing(false);
                }
            }

            any_category.setImageResource(R.drawable.any_filter);
            any_category.setTag(stateValues.UNSELECTED);
        }
        updateServiceCategoryCountValues();
        checkSearchButtonState();
    }

    private void setServiceModes()

    {
        any_service.setOnClickListener(this);
        email_service.setOnClickListener(this);
        video_service.setOnClickListener(this);
        chat_service.setOnClickListener(this);
        phone_service.setOnClickListener(this);
        home_service.setOnClickListener(this);
        clinic_service.setOnClickListener(this);

        chat_service_mode.setTag(stateValues.UNSELECTED);
        clinic_service_mode.setTag(stateValues.UNSELECTED);
        email_service_mode.setTag(stateValues.UNSELECTED);
        home_service_mode.setTag(stateValues.UNSELECTED);
        phone_service_mode.setTag(stateValues.UNSELECTED);
        video_service_mode.setTag(stateValues.UNSELECTED);

        if (requisition.isChat()) {
            chat_service_mode.setImageResource(R.drawable.chat_filter_selected);
            chat_service_mode.setTag(stateValues.SELECTED);
        }
        if (requisition.isClinic()) {
            clinic_service_mode
                    .setImageResource(R.drawable.inclinic_filter_selected);
            clinic_service_mode.setTag(stateValues.SELECTED);
        }
        if (requisition.isEmail()) {
            email_service_mode
                    .setImageResource(R.drawable.email_filter_selected);
            email_service_mode.setTag(stateValues.SELECTED);
        }
        if (requisition.isHome()) {
            home_service_mode.setImageResource(R.drawable.home_filter_selected);
            home_service_mode.setTag(stateValues.SELECTED);
        }
        if (requisition.isPhone()) {
            phone_service_mode
                    .setImageResource(R.drawable.phone_filter_selected);
            phone_service_mode.setTag(stateValues.SELECTED);
        }
        if (requisition.isVideo()) {
            video_service_mode
                    .setImageResource(R.drawable.video_filter_selected);
            video_service_mode.setTag(stateValues.SELECTED);
        }

        if (!requisition.isChat() && !requisition.isClinic()
                && !requisition.isEmail() && !requisition.isHome()
                && !requisition.isPhone() && !requisition.isVideo()) {
            any_service_mode.setImageResource(R.drawable.any_filter_selected);
            any_service_mode.setTag(stateValues.SELECTED);
        }

        updateServiceModeCountValues();
        checkSearchButtonState();
    }

    private void updateServiceModeCountValues() {
        int count = 0;
        if (requisition.isChat())
            count++;

        if (requisition.isClinic())
            count++;
        if (requisition.isEmail())
            count++;
        if (requisition.isHome())
            count++;
        if (requisition.isPhone())
            count++;
        if (requisition.isVideo())
            count++;

        if (count != 0) {
            service_mode_count.setVisibility(View.VISIBLE);
            service_mode_count.setText(count + " Selected");
        } else {
            any_service_mode.setImageResource(R.drawable.any_filter_selected);
            any_service_mode.setTag(stateValues.SELECTED);
            service_mode_count.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.valued:
                updateAttributes("valued");
                break;
            case R.id.discount:
                updateAttributes("discount");
                break;
            case R.id.verified:
                updateAttributes("verified");
                break;
            // finish button
            case R.id.finish_view:
                context.finish();
                overridePendingTransition(R.anim.trans_static, R.anim.trans_up_exit);
                break;

            // search button
            case R.id.search_view:
                if (search_view.getText().toString().equals("Save")) {
                    modifySearch();
                } else {
                    performSearch();
                }
                break;

            // service layout clicks
            case R.id.any_service:
                updateServiceModes("any");
                break;
            case R.id.email_service:
                updateServiceModes("email");
                break;
            case R.id.video_service:
                updateServiceModes("video");
                break;
            case R.id.chat_service:
                updateServiceModes("chat");
                break;
            case R.id.phone_service:
                updateServiceModes("phone");
                break;
            case R.id.home_service:
                updateServiceModes("home");
                break;
            case R.id.clinic_service:
                updateServiceModes("clinic");
                break;

            // categories layout clicks
            case R.id.any_service_cat_layout:
                updateServiceCategories("any");
                break;

            case R.id.doctor_clinic_cat_layout:
                updateServiceCategories("doctor_clinic");
                break;

            case R.id.hospital_nursing_cat_layout:
                updateServiceCategories("hospital_nursing");
                break;

            case R.id.lab_diagnostic_cat_layout:
                updateServiceCategories("lab_diagnostic");
                break;

            case R.id.pharmacy_cat_layout:
                updateServiceCategories("pharmacy");
                break;

            case R.id.gym_fitness_cat_layout:
                updateServiceCategories("gym_fitness");
                break;

            case R.id.spa_yoga_cat_layout:
                updateServiceCategories("spa_yoga");
                break;

            case R.id.medicine_cat_layout:
                updateServiceCategories("medicine");
                break;

            case R.id.support_cat_layout:
                updateServiceCategories("support");
                break;

            case R.id.healing_cat_layout:
                updateServiceCategories("healing");
                break;

            // gender click listners
            case R.id.any_gender:
                gender.setBoth(true);
                gender.setMale(false);
                gender.setFemale(false);
                UpdateGender();
                break;
            case R.id.male_gender:
                gender.setBoth(false);
                gender.setMale(true);
                gender.setFemale(false);
                UpdateGender();
                break;
            case R.id.female_gender:
                gender.setBoth(false);
                gender.setMale(false);
                gender.setFemale(true);
                UpdateGender();
                break;

            // timing click listners
            case R.id.any_timing:
                timing.setAfternoon(false);
                timing.setEvening(false);
                timing.setMorning(false);
                UpdateTiming();
                break;
            case R.id.morning:
                timing.setAfternoon(false);
                timing.setEvening(false);
                timing.setMorning(true);
                UpdateTiming();
                break;
            case R.id.evening:
                timing.setAfternoon(false);
                timing.setEvening(true);
                timing.setMorning(false);
                UpdateTiming();
                break;
            case R.id.afternoon:
                timing.setAfternoon(true);
                timing.setEvening(false);
                timing.setMorning(false);
                UpdateTiming();
                break;

            // day click listners

            case R.id.any_day:
                UpdateDay("any");

                break;
            case R.id.monday:
                UpdateDay("monday");

                break;
            case R.id.tuesday:
                UpdateDay("tuesday");

                break;
            case R.id.wednesday:
                UpdateDay("wednesday");

                break;
            case R.id.thrusday:
                UpdateDay("thrusday");

                break;
            case R.id.friday:
                UpdateDay("friday");

                break;
            case R.id.saturday:
                UpdateDay("saturday");
                break;
            case R.id.sunday:
                UpdateDay("sunday");
                break;
        }

    }

    private void setUpTiming() {

        any_timing.setOnClickListener(context);
        morning.setOnClickListener(context);
        afternoon.setOnClickListener(context);
        evening.setOnClickListener(context);

        any_timing.setTag(stateValues.UNSELECTED);
        morning.setTag(stateValues.UNSELECTED);
        afternoon.setTag(stateValues.UNSELECTED);
        evening.setTag(stateValues.UNSELECTED);

        if (!timing.isAfternoon() && !timing.isEvening() && !timing.isMorning()) {
            any_timing
                    .setTextColor(getResources().getColor(R.color.green_dark));
            any_timing.setTag(stateValues.SELECTED);

            morning.setTextColor(getResources().getColor(R.color.Black));
            morning.setTag(stateValues.UNSELECTED);

            afternoon.setTextColor(getResources().getColor(R.color.Black));
            afternoon.setTag(stateValues.UNSELECTED);

            evening.setTextColor(getResources().getColor(R.color.Black));
            evening.setTag(stateValues.UNSELECTED);

        } else {
            if (timing.isMorning()) {
                morning.setTextColor(getResources()
                        .getColor(R.color.green_dark));
                morning.setTag(stateValues.SELECTED);
            } else if (timing.isAfternoon()) {
                afternoon.setTextColor(getResources().getColor(
                        R.color.green_dark));
                afternoon.setTag(stateValues.SELECTED);
            } else {
                evening.setTextColor(getResources()
                        .getColor(R.color.green_dark));
                evening.setTag(stateValues.SELECTED);
            }
        }
    }

    private void UpdateTiming() {

        if (!timing.isAfternoon() && !timing.isEvening() && !timing.isMorning()) {
            any_timing
                    .setTextColor(getResources().getColor(R.color.green_dark));
            any_timing.setTag(stateValues.SELECTED);

            morning.setTextColor(getResources().getColor(R.color.Black));
            morning.setTag(stateValues.UNSELECTED);

            afternoon.setTextColor(getResources().getColor(R.color.Black));
            afternoon.setTag(stateValues.UNSELECTED);

            evening.setTextColor(getResources().getColor(R.color.Black));
            evening.setTag(stateValues.UNSELECTED);

        } else {

            any_timing.setTextColor(getResources().getColor(R.color.Black));
            any_timing.setTag(stateValues.UNSELECTED);
            if (timing.isMorning()) {
                morning.setTextColor(getResources()
                        .getColor(R.color.green_dark));
                morning.setTag(stateValues.SELECTED);

                any_timing.setTextColor(getResources().getColor(R.color.Black));
                any_timing.setTag(stateValues.UNSELECTED);

                afternoon.setTextColor(getResources().getColor(R.color.Black));
                afternoon.setTag(stateValues.UNSELECTED);

                evening.setTextColor(getResources().getColor(R.color.Black));
                evening.setTag(stateValues.UNSELECTED);

            } else if (timing.isAfternoon()) {

                morning.setTextColor(getResources().getColor(R.color.Black));
                morning.setTag(stateValues.UNSELECTED);

                any_timing.setTextColor(getResources().getColor(R.color.Black));
                any_timing.setTag(stateValues.UNSELECTED);

                afternoon.setTextColor(getResources().getColor(
                        R.color.green_dark));
                afternoon.setTag(stateValues.SELECTED);

                evening.setTextColor(getResources().getColor(R.color.Black));
                evening.setTag(stateValues.UNSELECTED);

            } else {

                morning.setTextColor(getResources().getColor(R.color.Black));
                morning.setTag(stateValues.UNSELECTED);

                any_timing.setTextColor(getResources().getColor(R.color.Black));
                any_timing.setTag(stateValues.UNSELECTED);

                afternoon.setTextColor(getResources().getColor(R.color.Black));
                afternoon.setTag(stateValues.UNSELECTED);

                evening.setTextColor(getResources()
                        .getColor(R.color.green_dark));
                evening.setTag(stateValues.SELECTED);
            }
        }
    }

    private void setUpDay() {

        any_day.setOnClickListener(context);
        mon.setOnClickListener(context);
        tue.setOnClickListener(context);
        wed.setOnClickListener(context);
        thru.setOnClickListener(context);
        fri.setOnClickListener(context);
        sat.setOnClickListener(context);
        sun.setOnClickListener(context);

        any_day.setTag(stateValues.UNSELECTED);
        mon.setTag(stateValues.UNSELECTED);
        tue.setTag(stateValues.UNSELECTED);
        wed.setTag(stateValues.UNSELECTED);
        thru.setTag(stateValues.UNSELECTED);
        fri.setTag(stateValues.UNSELECTED);
        sat.setTag(stateValues.UNSELECTED);
        sun.setTag(stateValues.UNSELECTED);

        if (!availability.isMonday() && !availability.isTuesday()
                && !availability.isWednesday() && !availability.isThrusday()
                && !availability.isFriday() && !availability.isSaturday()
                && !availability.isSunday()) {
            any_day.setTextColor(getResources().getColor(R.color.green_dark));
            any_timing.setTag(stateValues.SELECTED);

            mon.setTextColor(getResources().getColor(R.color.Black));
            mon.setTag(stateValues.UNSELECTED);

            tue.setTextColor(getResources().getColor(R.color.Black));
            tue.setTag(stateValues.UNSELECTED);

            wed.setTextColor(getResources().getColor(R.color.Black));
            wed.setTag(stateValues.UNSELECTED);

            thru.setTextColor(getResources().getColor(R.color.Black));
            thru.setTag(stateValues.UNSELECTED);

            fri.setTextColor(getResources().getColor(R.color.Black));
            fri.setTag(stateValues.UNSELECTED);

            sat.setTextColor(getResources().getColor(R.color.Black));
            sat.setTag(stateValues.UNSELECTED);

            sun.setTextColor(getResources().getColor(R.color.Black));
            sun.setTag(stateValues.UNSELECTED);

        } else {
            if (availability.isMonday()) {
                mon.setTextColor(getResources().getColor(R.color.green_dark));
                mon.setTag(stateValues.SELECTED);
            } else if (availability.isTuesday()) {
                tue.setTextColor(getResources().getColor(R.color.green_dark));
                tue.setTag(stateValues.SELECTED);
            } else if (availability.isWednesday()) {
                wed.setTextColor(getResources().getColor(R.color.green_dark));
                wed.setTag(stateValues.SELECTED);
            } else if (availability.isThrusday()) {
                thru.setTextColor(getResources().getColor(R.color.green_dark));
                thru.setTag(stateValues.SELECTED);
            } else if (availability.isFriday()) {
                fri.setTextColor(getResources().getColor(R.color.green_dark));
                fri.setTag(stateValues.SELECTED);
            } else if (availability.isSaturday()) {
                sat.setTextColor(getResources().getColor(R.color.green_dark));
                sat.setTag(stateValues.SELECTED);
            } else if (availability.isSunday()) {
                sun.setTextColor(getResources().getColor(R.color.green_dark));
                sun.setTag(stateValues.SELECTED);
            }
        }
    }

    private void UpdateDay(String value) {

        if (value.equals("any")) {
            availability.setFriday(false);
            availability.setMonday(false);
            availability.setSaturday(false);
            availability.setSunday(false);
            availability.setThrusday(false);
            availability.setTuesday(false);
            availability.setWednesday(false);

            any_day.setTextColor(getResources().getColor(R.color.green_dark));
            any_day.setTag(stateValues.SELECTED);

            mon.setTextColor(getResources().getColor(R.color.Black));
            mon.setTag(stateValues.UNSELECTED);

            tue.setTextColor(getResources().getColor(R.color.Black));
            tue.setTag(stateValues.UNSELECTED);

            wed.setTextColor(getResources().getColor(R.color.Black));
            wed.setTag(stateValues.UNSELECTED);

            thru.setTextColor(getResources().getColor(R.color.Black));
            thru.setTag(stateValues.UNSELECTED);

            fri.setTextColor(getResources().getColor(R.color.Black));
            fri.setTag(stateValues.UNSELECTED);

            sat.setTextColor(getResources().getColor(R.color.Black));
            sat.setTag(stateValues.UNSELECTED);

            sun.setTextColor(getResources().getColor(R.color.Black));
            sun.setTag(stateValues.UNSELECTED);
        } else {

            any_day.setTextColor(getResources().getColor(R.color.Black));
            any_day.setTag(stateValues.UNSELECTED);

            if (value.equals("monday")) {

                if (mon.getTag() == stateValues.UNSELECTED) {
                    mon.setTextColor(getResources()
                            .getColor(R.color.green_dark));
                    mon.setTag(stateValues.SELECTED);
                    availability.setMonday(true);
                } else {
                    mon.setTextColor(getResources().getColor(R.color.Black));
                    mon.setTag(stateValues.UNSELECTED);
                    availability.setMonday(false);
                }

            }
            if (value.equals("tuesday")) {

                if (tue.getTag() == stateValues.UNSELECTED) {
                    tue.setTextColor(getResources()
                            .getColor(R.color.green_dark));
                    tue.setTag(stateValues.SELECTED);
                    availability.setTuesday(true);
                } else {
                    tue.setTextColor(getResources().getColor(R.color.Black));
                    tue.setTag(stateValues.UNSELECTED);
                    availability.setTuesday(false);
                }
            }
            if (value.equals("wednesday")) {
                if (wed.getTag() == stateValues.UNSELECTED) {
                    wed.setTextColor(getResources()
                            .getColor(R.color.green_dark));
                    wed.setTag(stateValues.SELECTED);
                    availability.setWednesday(true);
                } else {
                    wed.setTextColor(getResources().getColor(R.color.Black));
                    wed.setTag(stateValues.UNSELECTED);
                    availability.setWednesday(false);
                }
            }
            if (value.equals("thrusday")) {
                if (thru.getTag() == stateValues.UNSELECTED) {
                    thru.setTextColor(getResources().getColor(
                            R.color.green_dark));
                    thru.setTag(stateValues.SELECTED);
                    availability.setThrusday(true);
                } else {
                    thru.setTextColor(getResources().getColor(R.color.Black));
                    thru.setTag(stateValues.UNSELECTED);
                    availability.setThrusday(false);
                }
            }
            if (value.equals("friday")) {
                if (fri.getTag() == stateValues.UNSELECTED) {
                    fri.setTextColor(getResources()
                            .getColor(R.color.green_dark));
                    fri.setTag(stateValues.SELECTED);
                    availability.setFriday(true);

                } else {
                    fri.setTextColor(getResources().getColor(R.color.Black));
                    fri.setTag(stateValues.UNSELECTED);
                    availability.setFriday(false);
                }
            }
            if (value.equals("saturday")) {
                if (sat.getTag() == stateValues.UNSELECTED) {
                    sat.setTextColor(getResources()
                            .getColor(R.color.green_dark));
                    sat.setTag(stateValues.SELECTED);
                    availability.setSaturday(true);
                } else {
                    sat.setTextColor(getResources().getColor(R.color.Black));
                    sat.setTag(stateValues.UNSELECTED);
                    availability.setSaturday(false);
                }
            }
            if (value.equals("sunday")) {
                if (sun.getTag() == stateValues.UNSELECTED) {
                    sun.setTextColor(getResources()
                            .getColor(R.color.green_dark));
                    sun.setTag(stateValues.SELECTED);
                    availability.setSunday(true);
                } else {
                    sun.setTextColor(getResources().getColor(R.color.Black));
                    sun.setTag(stateValues.UNSELECTED);
                    availability.setSunday(false);
                }
            }

            if (!availability.isFriday() && !availability.isMonday()
                    && !availability.isSunday() && !availability.isSaturday()
                    && !availability.isThrusday() && !availability.isTuesday()
                    && !availability.isWednesday()) {

                any_day.setTextColor(getResources()
                        .getColor(R.color.green_dark));
                any_day.setTag(stateValues.SELECTED);

            }
        }
    }

    private void setUpGender() {

        any_gender.setOnClickListener(context);
        male.setOnClickListener(context);
        female.setOnClickListener(context);

        any_gender.setTag(stateValues.UNSELECTED);
        male.setTag(stateValues.UNSELECTED);
        female.setTag(stateValues.UNSELECTED);

        if (!gender.isMale() && !gender.isFemale()) {
            any_gender
                    .setTextColor(getResources().getColor(R.color.green_dark));
            any_gender.setTag(stateValues.SELECTED);

            male.setTextColor(getResources().getColor(R.color.Black));
            male.setTag(stateValues.UNSELECTED);

            female.setTextColor(getResources().getColor(R.color.Black));
            female.setTag(stateValues.UNSELECTED);
        } else {
            if (gender.isMale()) {
                male.setTextColor(getResources().getColor(R.color.green_dark));
                male.setTag(stateValues.SELECTED);
            } else if (gender.isFemale()) {
                female.setTextColor(getResources().getColor(R.color.green_dark));
                female.setTag(stateValues.SELECTED);
            }
        }
    }

    private void UpdateGender() {

        if (!gender.isFemale() && !gender.isMale()) {
            any_gender
                    .setTextColor(getResources().getColor(R.color.green_dark));
            any_gender.setTag(stateValues.SELECTED);

            male.setTextColor(getResources().getColor(R.color.Black));
            male.setTag(stateValues.UNSELECTED);

            female.setTextColor(getResources().getColor(R.color.Black));
            female.setTag(stateValues.UNSELECTED);
        } else {

            any_gender.setTextColor(getResources().getColor(R.color.Black));
            any_gender.setTag(stateValues.UNSELECTED);
            if (gender.isMale()) {
                male.setTextColor(getResources().getColor(R.color.green_dark));
                male.setTag(stateValues.SELECTED);

                female.setTextColor(getResources().getColor(R.color.Black));
                female.setTag(stateValues.UNSELECTED);
            } else if (gender.isFemale()) {

                male.setTextColor(getResources().getColor(R.color.Black));
                male.setTag(stateValues.UNSELECTED);

                female.setTextColor(getResources().getColor(R.color.green_dark));
                female.setTag(stateValues.SELECTED);
            }
        }
    }

    // Suggestions Adapter
    public class SuggestionAdapter extends ArrayAdapter<String> {

        String value;
        Context context;
        private List<String> suggestions;

        public SuggestionAdapter(Context context) {
            super(context, android.R.layout.simple_spinner_dropdown_item);
            this.context = context;
            completeSuggestions = new ArrayList<Suggestions>();
            suggestions = new ArrayList<String>();

        }

        @Override
        public int getCount() {
            return suggestions.size();
        }

        @Override
        public String getItem(int index) {
            return suggestions.get(index);
        }

        @Override
        public Filter getFilter() {

            Filter myFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(
                        final CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        List<String> new_suggestions;
                        new_suggestions = getSuggestion(constraint.toString()
                                .trim());
                        suggestions.clear();
                        for (int i = 0; i < new_suggestions.size(); i++) {
                            suggestions.add(new_suggestions.get(i));
                        }
                        // Now assign the values and count to the FilterResults
                        // object
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence contraint,
                                              FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {

                        notifyDataSetInvalidated();
                    }
                }
            };
            return myFilter;
        }

        public List<String> getSuggestion(String value) {

            final ArrayList<String> queryResults = new ArrayList<String>(); // new
            // String dictionaryString =
            // getDictionaryString("https://core-services-test.jv-lab.net/api/v1/SearchHints?query="
            // + value);
            String dictionaryString = getDictionaryString("https://core-services-stage.jv-lab.net/api/v1/SearchHints?query="
                    + value);

            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                SuggestionsResponse rootObject = gson.fromJson(
                        dictionaryString, SuggestionsResponse.class);
                completeSuggestions = rootObject.getData();
                Iterator<Suggestions> iterator = completeSuggestions.iterator();
                while (iterator.hasNext()) {
                    queryResults.add(iterator.next().getHintText());
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            return queryResults;

        }

    }

    public class AreaSuggestionAdapter extends ArrayAdapter<String> {

        String value;
        Context context;
        private List<String> suggestions;

        public AreaSuggestionAdapter(Context context) {
            super(context, android.R.layout.simple_spinner_dropdown_item);
            this.context = context;
            locationHints = new ArrayList<String>();
            suggestions = new ArrayList<String>();

        }

        @Override
        public int getCount() {
            return suggestions.size();
        }

        @Override
        public String getItem(int index) {
            return suggestions.get(index);
        }

        @Override
        public Filter getFilter() {

            Filter myFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(
                        final CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        List<String> new_suggestions;
                        new_suggestions = getSuggestion(constraint.toString()
                                .trim());
                        suggestions.clear();
                        for (int i = 0; i < new_suggestions.size(); i++) {
                            suggestions.add(new_suggestions.get(i));
                        }
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence contraint,
                                              FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {

                        notifyDataSetInvalidated();
                    }
                }
            };
            return myFilter;
        }

        public List<String> getSuggestion(String value) {

            final ArrayList<String> queryResults = new ArrayList<String>(); // new
            String dictionaryString = getDictionaryString("http://jeevomcoreservicetest.cloudapp.net/api/v1/LocationHints?query="
                    + value);
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                LocationHints rootObject = gson.fromJson(dictionaryString,
                        LocationHints.class);
                locationHints = rootObject.getData();
                Iterator<String> iterator = locationHints.iterator();
                while (iterator.hasNext()) {
                    queryResults.add(iterator.next());
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            return queryResults;

        }

    }

    public String getDictionaryString(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            if (inputStream != null && responseCode == 200) {
                result = InputStreamToString
                        .convertInputStreamToString(inputStream);
            }

        } catch (Exception e) {
        }
        return result;
    }

    private void hideKeyboard() {

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void modifySearch() {
        jeevCriteria.setAvailability(availability);
        jeevCriteria.setCategory(category);
        jeevCriteria.setGender(gender);

        jeevCriteria.setRequisition(requisition);
        jeevCriteria.setTiming(timing);

        if (!CommonCode.isNullOrEmpty(search_what_final)) {
            jeevCriteria.setSearchString(search_what_final);
        } else {
            if (!CommonCode.isNullOrEmpty(searchEditText.getText().toString()
                    .trim())) {
                jeevCriteria.setSearchString(searchEditText.getText()
                        .toString().trim());
            } else {
                jeevCriteria.setSearchString("");
            }
        }

        if ((!CommonCode.isNullOrEmpty(search_area.getText().toString().trim()))) {
            filter.setLocation(search_area.getText().toString().trim());
        } else {
            filter.setLocation("");
        }

        filter.setDistance(distance);
        isNearMeChecked = near_me.isChecked();

        makeSearchObject();

        saveModifiedSearch();

    }

    private void saveModifiedSearch() {
        Gson gson = new GsonBuilder().create();
        String criteria = gson.toJson(jeevSearchCriteria).toString();
        String filterObj = gson.toJson(filter).toString();

        SaveSearchRequest request = new SaveSearchRequest();
        request.setCriteriaJson(criteria);
        request.setFilterJson(filterObj);
        request.setName(nameOfSavedSearch);

        RestAdapter saveSearchAdapter = new RestAdapter.Builder()
                .setClient(new MyUrlConnectionClient())
                .setLogLevel(LogLevel.FULL)
                .setLog(new AndroidLog("savesearch"))
                .setEndpoint(JeevOMUtil.baseUrl).build();
        Search saveSearch = saveSearchAdapter
                .create(Search.class);
        saveSearch.modifySaveSearch(
                gson.toJson(locationManager.getUserLocation()).toString(), authToken, request, jeevomSession.getMemberId(),
                new Callback<ApiResponse<Boolean>>() {

                    @Override
                    public void success(ApiResponse<Boolean> arg0, Response arg1) {
                        Toast.makeText(JeevSearchFilters.this,
                                "Modified Existing Search", Toast.LENGTH_SHORT)
                                .show();

                        setResult(Activity.RESULT_OK);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError arg0) {
                        Toast.makeText(JeevSearchFilters.this, "Try Again..",
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void makeSearchObject() {
        makeFilterObject();
        makeCriteriaObject();
    }

    private void makeFilterObject() {

        filter.setDistance(filter.getDistance());
        filter.setIsPremium(jeevCriteria.isValued());
        filter.setIsRecommended(false);
        filter.setIsVerified(jeevCriteria.isVerified());
        filter.setLatitude(0.0);
        filter.setLocation(filter.getLocation());
        filter.setLongitude(0.0);
        filter.setSkip(0);
        filter.setTop(30);
        filter.setIsDiscountOffered(jeevCriteria.isDiscount());
    }

    private void makeCriteriaObject() {
        // set search string
        if (!CommonCode.isNullOrEmpty(jeevCriteria.getSearchString())) {
            jeevSearchCriteria.setSearchString(jeevCriteria.getSearchString());
        } else {
            jeevSearchCriteria.setSearchString(null);
        }

        // set gender type
        if (gender.isBoth()) {
            jeevSearchCriteria.setGenderType("All");
        } else if (gender.isMale()) {
            jeevSearchCriteria.setGenderType("Male");
        } else {
            jeevSearchCriteria.setGenderType("Female");
        }

        // set timing
        if (!timing.isAfternoon() && !timing.isEvening() && !timing.isMorning()) {
            jeevSearchCriteria.setTimings("All");
        } else if (timing.isAfternoon()) {
            jeevSearchCriteria.setTimings("After");
        } else if (timing.isEvening()) {
            jeevSearchCriteria.setTimings("Even");
        } else
            jeevSearchCriteria.setTimings("Morn");

        // set availability
        jeevSearchCriteria.setAvailability(availability);

        // set categories
        jeevSearchCriteria.setCategory(makeCategories());

        // set service requisition types
        jeevSearchCriteria.setServiceRequisitionTypes(serviceRequisition());

    }

    private List<String> serviceRequisition() {
        List<String> serviceRequistion = new LinkedList<>();

        if (requisition.isChat())
            serviceRequistion.add("SG5");

        if (requisition.isClinic())
            serviceRequistion.add("SG1");

        if (requisition.isEmail()) {
            serviceRequistion.add("SG6");
            serviceRequistion.add("SG7");
        }

        if (requisition.isHome())
            serviceRequistion.add("SG2");

        if (requisition.isPhone())
            serviceRequistion.add("SG4");

        if (requisition.isVideo())
            serviceRequistion.add("SG3");
        return serviceRequistion;
    }

    private List<String> makeCategories() {
        List<String> categories = new LinkedList<>();
        if (category.isAlternateMedicine())
            categories.add("Alternate Medicine");

        if (category.isDoctorClinic())
            categories.add("Doctors & Clinics");

        if (category.isGymFitness())
            categories.add("Gyms, Fitness, Aerobics services");

        if (category.isHealing())
            categories.add("Healing Services including Naturopathy");

        if (category.isHealthcareSupport())
            categories.add("Healthcare support services");

        if (category.isHospitalNursing())
            categories.add("Hospital & Nursing Home");

        if (category.isLabDiagnostic())
            categories.add("Labs, Diagnostics and Imaging centers ");

        if (category.isPharmacies())
            categories.add("Pharmacies and Healthcare suppliers");

        if (category.isSpaWellness())
            categories.add("Wellness, Spa, Yoga");

        return categories;
    }

    private void performSearch() {

        jeevCriteria.setAvailability(availability);
        jeevCriteria.setCategory(category);
        jeevCriteria.setGender(gender);

        jeevCriteria.setRequisition(requisition);
        jeevCriteria.setTiming(timing);

        if (!CommonCode.isNullOrEmpty(search_what_final)) {
            jeevCriteria.setSearchString(search_what_final);
        } else {
            if (!CommonCode.isNullOrEmpty(searchEditText.getText().toString()
                    .trim())) {
                jeevCriteria.setSearchString(searchEditText.getText()
                        .toString().trim());
            } else {
                jeevCriteria.setSearchString("");
            }
        }

        if ((!CommonCode.isNullOrEmpty(search_area.getText().toString().trim()))) {

            filter.setLocation(search_area.getText().toString().trim());
        } else {
            filter.setLocation("");
        }

        filter.setDistance(distance);
        isNearMeChecked = near_me.isChecked();

        if (typeOfActivity == 0) {
            Intent searchIntent = new Intent(JeevSearchFilters.this,
                    JeevSearchList.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object", jeevCriteria);
            bundle.putSerializable("filter", filter);
            bundle.putBoolean("isNearMeChecked", isNearMeChecked);
            searchIntent.putExtras(bundle);
            startActivity(searchIntent);
            finish();
        } else {
            Intent sending_intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("criteria", jeevCriteria);
            bundle.putSerializable("filter", filter);
            bundle.putBoolean("isNearMeChecked", isNearMeChecked);
            sending_intent.putExtras(bundle);
            setResult(RESULT_OK, sending_intent);
            finish();
        }
    }

    private void enableSearchButton() {
        search_view.setEnabled(true);
        search_view.setClickable(true);
        isSearchButtonEnabled = true;
    }

    private void disableSearchButton() {
        search_view.setEnabled(false);
        search_view.setClickable(false);
        isSearchButtonEnabled = false;
    }

    private void checkSearchButtonState() {
        if ((CommonCode.isNullOrEmpty(search_what_final) && CommonCode
                .isNullOrEmpty(searchEditText.getText().toString().trim()))
                && (!requisition.isChat() && !requisition.isClinic()
                && !requisition.isEmail() && !requisition.isHome()
                && !requisition.isPhone() && !requisition.isVideo())
                && (!category.isAlternateMedicine()
                && !category.isDoctorClinic()
                && !category.isGymFitness() && !category.isHealing()
                && !category.isHealthcareSupport()
                && !category.isHospitalNursing()
                && !category.isLabDiagnostic()
                && !category.isPharmacies() && !category
                .isSpaWellness())) {

            disableSearchButton();

        } else {
            enableSearchButton();
        }
    }
}
