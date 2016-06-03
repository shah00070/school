package com.schooltriangle.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schooltriangle.MyApplication;
import com.schooltriangle.adapters.CustomAdapter;
import com.schooltriangle.dialog.GooglePlayAlert;
import com.schooltriangle.healthvitals.AddVitalReading;
import com.schooltriangle.interfaces.AppConstants;
import com.schooltriangle.interfaces.RecyclerItemClickListener;
import com.schooltriangle.library.pojo.ApiResponse;
import com.schooltriangle.mylibrary.dialog.GlobalAlert;
import com.schooltriangle.mylibrary.dialog.ProgressDialogFragment;
import com.schooltriangle.mylibrary.pojo.MembershipAuthenticateResponse;
import com.schooltriangle.mylibrary.session.Home;
import com.schooltriangle.mylibrary.session.JeevomSession;
import com.schooltriangle.mylibrary.session.UserCurrentLocationManager;
import com.schooltriangle.mylibrary.session.ValuesManager;
import com.schooltriangle.mylibrary.util.CommonCode;
import com.schooltriangle.mylibrary.util.Connectivity;
import com.schooltriangle.mylibrary.util.JeevOMUtil;
import com.schooltriangle.mylibrary.util.MyUrlConnectionClient;
import com.schooltriangle.pojos.ConsumerDetailsResponse;
import com.schooltriangle.pojos.DocumentBody;
import com.schooltriangle.pojos.Element2;
import com.schooltriangle.pojos.HealthTipsResponse;
import com.schooltriangle.pojos.HealthUpdateMetaData;
import com.schooltriangle.pojos.Measurement;
import com.schooltriangle.pojos.MemberHealthCardsData;
import com.schooltriangle.pojos.TipsResponse;
import com.schooltriangle.pojos.UploadImageResponse;
import com.schooltriangle.service.interfaces.HealthVitalInterface;
import com.schooltriangle.service.interfaces.Member;
import com.schooltriangle.service.interfaces.UploadProfImageInterface;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.schooltriangle.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by chandan on 28/12/15.
 */
public class HomeFragment extends Fragment implements AppConstants {

    private RecyclerView recListView;
    private View rootView;
public static String sname;
    private JeevomSession session;
    private CircleImageView userImageView;
    private TextView headerTitleTextvw, todayDateTextView;
    private LinearLayout todayAlertLayout;
    private String consumerId;
    private String authToken = null;
    private ValuesManager valuesManager;
    private GlobalAlert globalAlert;
    private static ConsumerDetailsResponse consumerDetails;
    private Gson gson;
    private LinearLayout mInterceptorFrame;
    private GooglePlayAlert googleAlert;
    private Uri mCapturedImageURI;
    private ArrayList<TipsResponse> tipsList;
    private SharedPreferences preferences;
    public Home hsession;
   // private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    public static int s=1;
    private ImageButton fab;

    private boolean expanded = false;

    private View fabAction1;
    private View fabAction2;
    private View fabAction3;

    private float offset1;
    private float offset2;
    private float offset3;
    private List<FloatingActionMenu> menus = new ArrayList<>();

    private static final String TAG = "Floating Action Button";
    private static final String TRANSLATION_Y = "translationY";
    List<MemberHealthCardsData> healthCardList;
    public com.github.clans.fab.FloatingActionButton addbp;
    UserCurrentLocationManager locationManager;
    private ProgressDialogFragment progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hsession= new Home(getActivity());
        session = new JeevomSession(getActivity());
        valuesManager = new ValuesManager(getActivity());
        consumerDetails = new ConsumerDetailsResponse();
        gson = new GsonBuilder().create();
        googleAlert = new GooglePlayAlert(getActivity());
        locationManager = new UserCurrentLocationManager(getActivity());
        globalAlert = new GlobalAlert(getActivity());
        tipsList = new ArrayList<>();
        preferences = getActivity().getSharedPreferences("JeevomPref", getActivity().MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container,
                false);
        setUiOnScreen();
        return rootView;
    }

    private void setUiOnScreen() {

       /* LinearLayout searchFromHome = (LinearLayout) rootView.findViewById(R.id.search_from_home);
        searchFromHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openFilterActivity();
            }
        });*/

        userImageView = (CircleImageView) rootView.findViewById(R.id.user_imageview);

        userImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (session.getLoggedInStatus())
                    selectImage();
            }
        });



        headerTitleTextvw = (TextView) rootView.findViewById(R.id.header_title_text);
        todayAlertLayout = (LinearLayout) rootView.findViewById(R.id.today_alerts_layout);
        todayDateTextView = (TextView) rootView.findViewById(R.id.today_date);

        recListView = (RecyclerView) rootView.findViewById(R.id.recycleListView);
        recListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recListView.setLayoutManager(llm);
        List<HealthUpdateMetaData> healthUpdateMetaDataList = getHealthUpdatesList();
        CustomAdapter adapter = new CustomAdapter(healthUpdateMetaDataList);
        recListView.setAdapter(adapter);

        recListView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position == 0) {
                            openDirectScreen(0);
                        } else if (position == 1) {
                            openDirectScreen(1);
                        } else if (position == 2) {
                            openDirectScreen(2);
                        } else if(position==3){
                            openDirectScreen(3);

                        }     else if(position==4){
                            openDirectScreen(4);
                        }
                        else if(position==5){
                            openDirectScreen(5);
                        }

                    else{
                            openDirectScreen(WHY_JEEVOM_PAGE);
                        }
                    }
                })
        );

        final FloatingActionMenu menuLabelsRight = (FloatingActionMenu) rootView.findViewById(R.id.menu_labels_right);

        addbp = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.add_bp);
        com.github.clans.fab.FloatingActionButton invfrn = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.inv_frn);
     //   com.github.clans.fab.FloatingActionButton reqquote = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.req_quote);
        com.github.clans.fab.FloatingActionButton attachdoc = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.attach_doc);

      /*  com.github.clans.fab.FloatingActionButton note_add = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.note_add);
        com.github.clans.fab.FloatingActionButton task_add = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.task_add);
      */  com.github.clans.fab.FloatingActionButton remminder_add = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.reminder_add);





/*
        note_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDirectScreen(1);
            }
        });
        task_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDirectScreen(2);
            }
        });*/
        remminder_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDirectScreen(5);
            }
        });











        addbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*  if(session.getLoggedInStatus()==true){
                   //call_healthrecord();
               }else{
                   Intent i = new Intent(getActivity(), SignUpLoginActivity.class);
                   startActivity(i);
                   getActivity().overridePendingTransition(R.anim.trans_left_in,
                           R.anim.trans_left_exit);
               }*/


            }
        });

        invfrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent inv = new Intent(getActivity(), Invitefriend.class);
                startActivity(inv);
                menuLabelsRight.toggle(true);*/

            }
        });


      /*  reqquote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        attachdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        menus.add(menuLabelsRight);

        menuLabelsRight.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {

                if (opened) {


                } else {

                }
            }
        });
      /*  menuLabelsRight.hideMenuButton(false);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }*/


        mInterceptorFrame = (LinearLayout) rootView.findViewById(R.id.content_layout);
        mInterceptorFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (menuLabelsRight.isOpened())

                {
                    menuLabelsRight.toggle(true);
                    return true;

                }

                return false;
            }

        });





    }

    public void call_healthrecord() {


            RestAdapter healthIndicatorsAdapter = new RestAdapter.Builder()
                    .setLog(new AndroidLog("healthcards")).setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(JeevOMUtil.baseUrl).build();
            HealthVitalInterface healthVitals = healthIndicatorsAdapter
                    .create(HealthVitalInterface.class);
            progressDialog = ProgressDialogFragment.newInstance();
            progressDialog.show(getFragmentManager(), "dialog");
            progressDialog.setCancelable(false);
          //  Toast.makeText(getActivity(), ""+session.getMemberId(), Toast.LENGTH_SHORT).show();

            healthVitals.getMemberAvailableHealthCards(gson.toJson(locationManager.getUserLocation()).toString(), session.getMemberId(), new Callback<ApiResponse<List<MemberHealthCardsData>>>() {
                @Override
                public void success(ApiResponse<List<MemberHealthCardsData>> listApiResponse, Response response) {
                    progressDialog.dismissAllowingStateLoss();
                    healthCardList = listApiResponse.getData();
                    List<Element2> elements = null;


                    for (MemberHealthCardsData object :
                            healthCardList) {

                        ArrayList<String> listOfMeasurements = new ArrayList<String>();

                        List<Measurement> measurementList = object.getMeasurement();

                        for (Measurement measurementObject :
                                measurementList) {
                            elements = measurementObject.getElements();


                            for (Element2 elementObject : elements) {
                                listOfMeasurements.add(elementObject.getMeasuredValue());
                            }
                        }


                        if (object.getHealthIndicator().getName().trim().equalsIgnoreCase("Blood pressure")) {
                            addbp.setTag(object.getHealthIndicator());
                            Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                            Intent view_health_vital_intent = new Intent(getActivity(), AddVitalReading.class);
                            view_health_vital_intent.putExtra("elements", (Serializable) (addbp.getTag()));
                            startActivity(view_health_vital_intent);
                        }


                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismissAllowingStateLoss();
                    Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
                }
            });

















        }





    private Animator createCollapseAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

 /*   public void fabAction1(View view) {
        Log.d(TAG, "Action 1");
    }

    public void fabAction2(View view) {
        Log.d(TAG, "Action 2");
    }

    public void fabAction3(View view) {
        Log.d(TAG, "Action 3");
    }

*/




    public void openDirectScreen(int i) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

        Fragment fragment = null;

        if (i == 0) {
            fragment = new HealthTipsListFragment();
        } else if (i == 1) {
            fragment = new HealthBuddyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("stote_view", 0);
            fragment.setArguments(bundle);
            //((HomeActivity) getActivity()).resetSelectedItems(DEAL_AND_OFFERS);
        } else if (i == 2) {

            fragment = new HealthBuddyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("stote_view", 1);
            fragment.setArguments(bundle);
           // Fragment fg = new Inbox();

        }else if(i==3){
            fragment= new Inbox();
        }
        else if(i==4){


            fragment = new HealthCloudFragment();

        }
        else if(i==5){
                fragment = new HealthBuddyFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("stote_view", 2);
                fragment.setArguments(bundle);


            }

        else{
            showAlert("Comming Soonll");

        }

        if (fragment != null) {
            fragTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragTransaction.replace(R.id.container_body, fragment);
            fragTransaction.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Home Screen");

        if (session.getLoggedInStatus()) {

            consumerId = session.getConsumerIds().get(
                    JeevomSession.JEEVOM_CONSUMER_ID);

            if (!CommonCode.isNullOrEmpty(session.getAuthToken())) {
                authToken = "Basic " + session.getAuthToken();
            }



            if(hsession.getKey_HomeId().equals("true")){
              //  Toast.makeText(getActivity(), "hit", Toast.LENGTH_SHORT).show();
                //getConsumerDetails(consumerId);
                hsession.setKey_HomeId("false");
            } else if(hsession.getKey_HomeId().equals("false")){
                setUserInfo(new ConsumerDetailsResponse());
            }

        } else {
            setUserInfo(new ConsumerDetailsResponse());
        }
    }


    public ArrayList<HealthUpdateMetaData> getHealthUpdatesList() {

        ArrayList<HealthUpdateMetaData> dataArrayList = new ArrayList<>();

        HealthUpdateMetaData healthTipdata = new HealthUpdateMetaData();
        healthTipdata.setTitle("Learning tips");

        Date date = new Date();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String todayHealthTip = preferences.getString(today, null);

        if (todayHealthTip == null) {
            //hopgetAllHealthTips();
            healthTipdata.setDescription("Turning lessons into stories and relating name and events to a story will help you memorize better");
        } else {
            todayHealthTip = preferences.getString(today, null);
            healthTipdata.setDescription(todayHealthTip);
        }

        HealthUpdateMetaData dealsOfferData = new HealthUpdateMetaData();
        dealsOfferData.setTitle("Important Notes");
        dealsOfferData.setDescription("Hi Class! We will be having a class test of Mathematics on 20th May,2016. Syllabus covered will be complete Unit 5 of Trigonometry. All the best!");

        HealthUpdateMetaData overviewData = new HealthUpdateMetaData();
        overviewData.setTitle("Pending Task");
        overviewData.setDescription("Submit Biology Lab file by Saturday \n Submit Hindi class assignment due on Thursday (26th May).");

        HealthUpdateMetaData productOverview = new HealthUpdateMetaData();
        productOverview.setTitle("Recent Messages");
        productOverview.setDescription("Please deposit your pending fee of Rs 10,000 for this quarter.");




        HealthUpdateMetaData dealsOfferData12 = new HealthUpdateMetaData();
        dealsOfferData12.setTitle("Today's Timetable");
        dealsOfferData12.setDescription(" Check your today's schedule here.");

        HealthUpdateMetaData dealsOfferData13 = new HealthUpdateMetaData();
        dealsOfferData13.setTitle("Upcoming Events");
        dealsOfferData13.setDescription("Stay up to date with all events.");


        dataArrayList.add(healthTipdata);
        dataArrayList.add(dealsOfferData);
        dataArrayList.add(overviewData);
        dataArrayList.add(productOverview);

        dataArrayList.add(dealsOfferData12);
        dataArrayList.add(dealsOfferData13);
        return dataArrayList;
    }


    public void getConsumerDetails(String consumerId) {
        RestAdapter consumerAdapter = new RestAdapter.Builder()
                .setLog(new AndroidLog("consumer")).setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new MyUrlConnectionClient())
                .setEndpoint(JeevOMUtil.baseUrl).build();

        final DialogFragment dialogFragment = ProgressDialogFragment.newInstance();
        dialogFragment.show(getFragmentManager(), "dialog");
        dialogFragment.setCancelable(false);
        Member getConsumerService = consumerAdapter
                .create(Member.class);
        getConsumerService.getConsumer(authToken, consumerId,
                String.valueOf(valuesManager.getVersion()),
                new Callback<ConsumerDetailsResponse>() {

                    @Override
                    public void success(ConsumerDetailsResponse arg0,
                                        Response arg1) {
                        dialogFragment.dismissAllowingStateLoss();

                        if (arg0.getData() != null) {
                            consumerDetails = arg0;
                            saveuser(consumerDetails);
                            setUserInfo(consumerDetails);
                        }
                    }

                    @Override
                    public void failure(RetrofitError arg0) {
                        dialogFragment.dismissAllowingStateLoss();

                        if (arg0.isNetworkError()) {
                            if (!(Connectivity
                                    .checkConnectivity(getActivity()))) {
                                showAlert(JeevOMUtil.INTERNET_CONNECTION);
                            } else if (arg0.getCause() instanceof SocketTimeoutException) {
                                showAlert(JeevOMUtil.INTERNET_CONNECTION_SLOW);
                            } else if (arg0.getResponse() == null) {
                                showAlert(JeevOMUtil.SOMETHING_WRONG);
                            }
                        } else if (arg0.getResponse().getStatus() == 426) {
                            showGooglePlayAlert(getResources().getString(
                                    R.string.google_update));
                        } else if (arg0.getResponse().getStatus() > 400) {
                            showAlert(JeevOMUtil.SOMETHING_WRONG);
                        } else {
                            String json = new String(((TypedByteArray) arg0
                                    .getResponse().getBody()).getBytes());

                            MembershipAuthenticateResponse MembershipAuthenticateErrors = gson
                                    .fromJson(
                                            json,
                                            MembershipAuthenticateResponse.class);
                            String code = MembershipAuthenticateErrors
                                    .getStatus().getCode();
                            String message = MembershipAuthenticateErrors
                                    .getStatus().getMessage();
                            if (code.equals("BE-1001")) {
                                showAlert(message);
                            } else if (code.equals("BE-1000")) {
                                showAlert(message);
                            } else if (code.equals("DE-1001")) {
                                showAlert(message);
                            } else if (code.equals("BE-1002")) {
                                showAlert(message);
                            } else if (code.equals("DE-1000")) {
                                showAlert(message);
                            } else if (code.equals("BE-1004")) {
                                showAlert(message);
                            }
                        }
                    }
                });

    }
    public void saveuser(ConsumerDetailsResponse data){
       /* System.out.println("1::getDateOfBirth" + "-->" + data.getData().getDateOfBirth());
        System.out.println("1::getPhotoURL" + "-->" + data.getData().getPhotoURL());
        System.out.println("1::getAllergies" + "-->" + data.getData().getAllergies());
        System.out.println("1::getCellNumber" + "-->" + data.getData().getCellNumber());
        System.out.println("1::getConsultationFees" + "-->" + data.getData().getConsultationFees());
        System.out.println("1::getDegrees" + "-->" + data.getData().getDegrees());
        System.out.println("1::getEmail" + "-->" + data.getData().getEmail());
        System.out.println("1::getExpertises" + "-->" + data.getData().getExpertises());
        System.out.println("1::getFirstName" + "-->" + data.getData().getFirstName());
        System.out.println("1::getFeatures" + "-->" + data.getData().getFeatures());
        System.out.println("1::getFullName" + "-->" + data.getData().getFullName());
        System.out.println("1::getMemberId" + "-->" + data.getData().getMemberId());
        System.out.println("1::getTitle" + "-->" + data.getData().getTitle());
        System.out.println("1::getGender" + "-->" + data.getData().getGender());


        System.out.println("1::getHeight" + "-->" + data.getData().getHeight());
        System.out.println("1::getHeightUnit" + "-->" + data.getData().getHeightUnit());
        System.out.println("1::getIdentityMarks" + "-->" + data.getData().getIdentityMarks());
        System.out.println("1::getLastName" + "-->" + data.getData().getLastName());
        System.out.println("1::getMedicalConditions" + "-->" + data.getData().getMedicalConditions());


        System.out.println("1::getSpecialities" + "-->" + data.getData().getSpecialities());
        System.out.println("1::getWeight" + "-->" + data.getData().getWeight());
        System.out.println("1::getBloodGroupType" + "-->" + data.getData().getBloodGroupType());
        System.out.println("1::getConsultationDetails" + "-->" + data.getData().getConsultationDetails());
        System.out.println("1::getFacilityProfile" + "-->" + data.getData().getFacilityProfile());

        System.out.println("1::getStartYear" + "-->" + data.getData().getStartYear());
        System.out.println("1::getMemberContactInformation" + "-->" + data.getData().getMemberContactInformation());
        System.out.println("1::getMedicalConditions" + "-->" + data.getData().getMedicalConditions());
        System.out.println("1::getProfileSettingDetails" + "-->" + data.getData().getProfileSettingDetails());
        System.out.println("1::getProfileId" + "-->" + data.getData().getProfileId());

        System.out.println("1::getServicesOffered" + "-->" + data.getData().getServicesOffered());
        System.out.println("1::getConsultationDetails" + "-->" + data.getData().getConsultationDetails());
        System.out.println("1::getProfileSettingDetails" + "-->" + data.getData().getProfileSettingDetails());
        System.out.println("1::getMemberContactInformation" + "-->" + data.getData().getMemberContactInformation());
*/
        hsession.setKEY_FirstName(data.getData().getFirstName());
        // save age
        String dateOfBirth = data.getData().getDateOfBirth();

        // get Age of User
        long age;
        if (!CommonCode.isNullOrEmpty(dateOfBirth)) {
            age = CommonCode.getYearDifferenceBetweenDates(CommonCode
                    .getCurrentTimeStamp(), data.getData().getDateOfBirth());
        } else {
            age = 0;
        }
        hsession.setKey_age(String.valueOf(age));
        hsession.setKEY_PhotoURL(data.getData().getPhotoURL());
        hsession.setKEY_DateOfBirth(data.getData().getDateOfBirth());



        System.out.println("2::getFirstName" + "-->" + hsession.getKEY_FirstName());
        System.out.println("2::Key_age" + "-->" + hsession.getKey_age());
        System.out.println("2::PhotoURL" + "-->" + hsession.getKEY_PhotoURL());
        System.out.println("2::DateOfBirth" + "-->" + hsession.getKEY_DateOfBirth());


    }
    // Show Global Message
    private void showAlert(String message) {
        globalAlert.show();
        globalAlert.setMessage(message);
    }

    private void showGooglePlayAlert(String message) {
        googleAlert.show();
        googleAlert.setMessage(message);
    }


    private void setUserInfo(ConsumerDetailsResponse details) {
        String stringHeader = getResources().getString(R.string.header_hello_text);
        System.out.print("check" + hsession.getKEY_FirstName());
        if ( hsession.getKEY_FirstName()!=null && !hsession.getKEY_FirstName().equals("-")) {
            String firstName = hsession.getKEY_FirstName();

            if (firstName != null) {
                stringHeader = stringHeader.replace("user", firstName);
            }
        }


        headerTitleTextvw.setText(stringHeader);
sname=stringHeader;
        SimpleDateFormat parseFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        Date date = new Date();
        String todayDateTime = parseFormat.format(date);
        todayDateTextView.setText(todayDateTime);

        todayAlertLayout.removeAllViews();

        if (!session.getLoggedInStatus()) {

            SpannableString spannableString = new SpannableString(getResources().getString(R.string.go_for_signup_msg));

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                 /*   startActivity(new Intent(getActivity(),
                            SignUpLoginActivity.class));*/
                }
            };

          //  spannableString.setSpan(clickableSpan, 67, 79, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            TextView textView = new TextView(getActivity());
            textView.setTextColor(Color.parseColor("#e7e7e7"));
            textView.setTextSize(15.0f);
            textView.setText(spannableString);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            todayAlertLayout.addView(textView);
        } else {

            if (hsession.getKEY_DateOfBirth() != null) {

                TextView dobTxtVw = new TextView(getActivity());
                dobTxtVw.setTextColor(Color.parseColor("#000000"));
                dobTxtVw.setTextSize(14.0f);

                long dayDiff = 0;
                try {
                    dayDiff = CommonCode.getDayDifferenceBetweenDates(CommonCode
                            .getCurrentTimeStamp(), hsession.getKEY_DateOfBirth());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                int year = (int) dayDiff / 365;
                int month = (int) (dayDiff % 365) / 30;
                int days = (int) (dayDiff % 365) % 30;

                String dobString = getResources().getString(R.string.user_age_msg);

                String replaceStr = year + " Yrs " + month + " Months " + days + " days";
                dobString = dobString.replace("YYYYMMDD", replaceStr);

                dobTxtVw.setText(dobString);
                todayAlertLayout.addView(dobTxtVw);
            } else {

                SpannableString spannableStr = new SpannableString(getResources().getString(R.string.update_profile_msg));

                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        ((HomeActivity) getActivity()).callHealthProfileScreen(null);
                    }
                };

                spannableStr.setSpan(clickableSpan, 70, 91, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                TextView textView = new TextView(getActivity());
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setTextSize(14.0f);
                textView.setText(spannableStr);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                todayAlertLayout.addView(textView);
            }

        }

        if (hsession.getKEY_PhotoURL() != null) {

            String imgUrl = hsession.getKEY_PhotoURL().replace(" ", "%20");

            Picasso.with(getActivity()).load(imgUrl)
                    .placeholder(R.drawable.jeevom_back)
                    .error(R.drawable.jeevom_back)
                    .into(userImageView, new com.squareup.picasso.Callback() {

                        @Override
                        public void onSuccess() {
                            System.out.println("success");
                        }

                        @Override
                        public void onError() {
                            System.out.println("error");
                        }
                    });
        }

    }


    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "temp.jpg");
                    mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                    intentPicture.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    intentPicture.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
                    startActivityForResult(intentPicture, JeevOMUtil.CAMERA_REQUEST_CODE);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,
                            JeevOMUtil.GALLERY_REQUEST_CODE);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == JeevOMUtil.CAMERA_REQUEST_CODE) {

                String selectedImagePath = getRealPathFromURI(mCapturedImageURI);
                performCrop(selectedImagePath);

            } else if (requestCode == JeevOMUtil.GALLERY_REQUEST_CODE) {

                if (data != null && data.getData() != null) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        performCrop(picturePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else if (requestCode == JeevOMUtil.IMAGE_CROP_REQUEST_CODE) {

                Bundle extras = data.getExtras();

                Bitmap selectedBitmap = extras.getParcelable("data");
                // Set The Bitmap Data To ImageView
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                double width = selectedBitmap.getWidth();
                double height = selectedBitmap.getHeight();
                double ratio = 400 / width;
                int newheight = (int) (ratio * height);
                selectedBitmap = Bitmap.createScaledBitmap(selectedBitmap, 400,
                        newheight, true);
                selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 95, bao);
                byte[] ba = bao.toByteArray();

                String encodeToString = Base64.encodeToString(ba, 0);

                if (encodeToString == null) {
                    showAlert("image not accessible");
                } else {
                    //postCallForUploadImage(encodeToString);
                }
            }
        }
    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, JeevOMUtil.IMAGE_CROP_REQUEST_CODE);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Convert the image URI to the direct file system path of the image file
    public String getRealPathFromURI(Uri contentUri) {
        String result = null;
        Cursor cursor = getActivity().getContentResolver().query(contentUri,
                null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            if (idx != -1) {
                result = cursor.getString(idx);
            }
            cursor.close();
        }
        return result;
    }


    private void postCallForUploadImage(String documentContent) {
        RestAdapter uploadRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("upload_image"))
                .setClient(new MyUrlConnectionClient())
                .setEndpoint(JeevOMUtil.baseUrl).build();

        final DialogFragment dialogFragment = ProgressDialogFragment.newInstance();
        dialogFragment.show(getFragmentManager(), "dialog");
        dialogFragment.setCancelable(false);

        DocumentBody documentBody = new DocumentBody();
        documentBody.setBinaryContent(documentContent);
        documentBody.setDocumentFileName("profile_pic.jpg");
        // documentBody.setUserId(Integer.valueOf(jeevom_session.getConsumerIds().get(JeevomLocalSession.JEEVOM_CONSUMER_ID)));
        documentBody.setUserId(session.getMemberId());

        UploadProfImageInterface uploadProfImageInterface = uploadRestAdapter
                .create(UploadProfImageInterface.class);
        uploadProfImageInterface.upload(
                authToken, documentBody,
                new Callback<UploadImageResponse>() {

                    @Override
                    public void failure(RetrofitError arg0) {

                        dialogFragment.dismissAllowingStateLoss();

                        if (arg0.isNetworkError()) {
                            if (!(Connectivity.checkConnectivity(getActivity()))) {
                                showAlert(JeevOMUtil.INTERNET_CONNECTION);
                            } else if (arg0.getCause() instanceof SocketTimeoutException) {
                                showAlert(JeevOMUtil.INTERNET_CONNECTION_SLOW);
                            } else if (arg0.getResponse() == null) {
                                showAlert(JeevOMUtil.SOMETHING_WRONG);
                            }
                        } else if (arg0.getResponse().getStatus() > 400) {
                            showAlert(JeevOMUtil.SOMETHING_WRONG);
                        } else {
                            String json = new String(((TypedByteArray) arg0
                                    .getResponse().getBody()).getBytes());
                            Gson gson = new GsonBuilder().setPrettyPrinting()
                                    .create();
                            UploadImageResponse uploadImageErrors = gson
                                    .fromJson(json, UploadImageResponse.class);
                            String code = uploadImageErrors.getStatus()
                                    .getCode();
                            String message = uploadImageErrors.getStatus()
                                    .getMessage();
                            if (code.equals("BE-1001")) {
                                showAlert(message);
                            } else if (code.equals("BE-1000")) {
                                showAlert(message);
                            } else if (code.equals("DE-1001")) {
                                showAlert(message);
                            } else if (code.equals("BE-1002")) {
                                showAlert(message);
                            } else if (code.equals("DE-1000")) {
                                showAlert(message);
                            } else if (code.equals("BE-1003")) {
                                showAlert(message);
                            }
                        }

                    }

                    @Override
                    public void success(UploadImageResponse arg0, Response arg1) {

                        dialogFragment.dismissAllowingStateLoss();

                        if (!CommonCode.isNullOrEmpty(arg0.getData()
                                .getFileUrl())) {
                            Picasso.with(getActivity())
                                    .load(arg0.getData().getFileUrl())
                                    .placeholder(R.drawable.jeevom_back)
                                    .error(R.drawable.jeevom_back)
                                    .into(userImageView);

                            hsession.setKEY_PhotoURL(arg0.getData().getFileUrl());

                        }
                    }
                });
    }

    public void getAllHealthTips() {

        RestAdapter consumerAdapter = new RestAdapter.Builder()
                .setLog(new AndroidLog("consumer")).setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new MyUrlConnectionClient())
                .setEndpoint(JeevOMUtil.baseUrl).build();

        final DialogFragment dialogFragment = ProgressDialogFragment.newInstance();
        dialogFragment.show(getFragmentManager(), "dialog");
        dialogFragment.setCancelable(false);
        Member getConsumerService = consumerAdapter
                .create(Member.class);

        getConsumerService.getHealthTips(
                new Callback<HealthTipsResponse>() {

                    @Override
                    public void success(HealthTipsResponse arg0,
                                        Response arg1) {
                        dialogFragment.dismissAllowingStateLoss();

                        tipsList = arg0.getData();
                        Date date = new Date();
                        String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        SharedPreferences.Editor editor = preferences.edit();

                        if (tipsList.size() > 0) {
                            editor.putString(today, tipsList.get(0).getText());
                            editor.commit();
                        }
                    }

                    @Override
                    public void failure(RetrofitError arg0) {
                        dialogFragment.dismissAllowingStateLoss();

                        if (arg0.isNetworkError()) {
                            if (!(Connectivity
                                    .checkConnectivity(getActivity()))) {
                                showAlert(JeevOMUtil.INTERNET_CONNECTION);
                            } else if (arg0.getCause() instanceof SocketTimeoutException) {
                                showAlert(JeevOMUtil.INTERNET_CONNECTION_SLOW);
                            } else if (arg0.getResponse() == null) {
                                showAlert(JeevOMUtil.SOMETHING_WRONG);
                            }
                        } else {
                            showAlert(JeevOMUtil.SOMETHING_WRONG);
                        }
                    }
                });

    }

}