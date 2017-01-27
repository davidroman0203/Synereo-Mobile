package com.dr.sharingtest.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dr.sharingtest.R;
import com.dr.sharingtest.activities.LeftMenusMediaActivity;
import com.dr.sharingtest.activities.MainActivity;
import com.dr.sharingtest.adaptors.AmplificationListAdapter;
import com.dr.sharingtest.adaptors.GoogleCardsSocialAdapter;
import com.dr.sharingtest.model.Amplification;
import com.dr.sharingtest.model.DataAccess;
import com.dr.sharingtest.utils.DummyContent;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AmplificationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AmplificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmplificationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int INITIAL_DELAY_MILLIS = 300;

    public static final String TAG = AmplificationsFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    ListView lvAmplifications;
    private GoogleCardsSocialAdapter mGoogleCardsAdapter;

    public AmplificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AmplificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AmplificationsFragment newInstance(String param1, String param2) {
        AmplificationsFragment fragment = new AmplificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_amplifications, container, false);
        lvAmplifications = (ListView)view.findViewById(R.id.lvAmplifications);
        setListView();
        return view;
    }

    void setListView(){

        Realm realm = Realm.getInstance(getActivity());
        RealmResults<Amplification> results =
                realm.where(Amplification.class)
                        .findAll();

        DataAccess dataAccess = new DataAccess();
        for (Amplification amplification:results){
            DataAccess.amplifications.add(amplification);
        }

        mGoogleCardsAdapter = new GoogleCardsSocialAdapter(getActivity(),
                DataAccess.amplifications);//DummyContent.getDummyModelList());
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
                new SwipeDismissAdapter(mGoogleCardsAdapter, (OnDismissCallback) getActivity()));
        swingBottomInAnimationAdapter.setAbsListView(lvAmplifications);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
                INITIAL_DELAY_MILLIS);

        lvAmplifications.setClipToPadding(false);
        lvAmplifications.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        lvAmplifications.setDividerHeight(px);
        lvAmplifications.setFadingEdgeLength(0);
        lvAmplifications.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        lvAmplifications.setPadding(px, px, px, px);
        lvAmplifications.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        lvAmplifications.setAdapter(swingBottomInAnimationAdapter);

        //AmplificationListAdapter listAdapter = new AmplificationListAdapter(getActivity(), DataAccess.amplifications);
        //lvAmplifications.setAdapter(listAdapter);

        lvAmplifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                News news = arrNews.get(position);
//                Intent intent = new Intent(NewsActivity.this, DetailNewsActivity.class);
//                DetailNewsActivity.news = news;
//                startActivity(intent);
                String str = String.format(Locale.US, "Amplification %d selected", position);
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
