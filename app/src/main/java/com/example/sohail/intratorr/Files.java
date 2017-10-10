package com.example.sohail.intratorr;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Files.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Files#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Files extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<FilesRow> filesList = new ArrayList<>();
    private RecyclerView filesRecyclerView;
    private FilesViewAdapter fAdapter;
    private SearchView searchView;
    private ImageView downloadView;

    public Files() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Files.
     */
    // TODO: Rename and change types and number of parameters
    public static Files newInstance(String param1, String param2) {
        Files fragment = new Files();
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
        final View view = inflater.inflate(R.layout.fragment_files,container,false);
        FloatingActionButton add_files = view.findViewById(R.id.add_files);
        searchView = view.findViewById(R.id.searchFiles);
        searchView.setIconified(false);
        searchView.clearFocus();

        filesRecyclerView = view.findViewById(R.id.filesList);
        fAdapter = new FilesViewAdapter(filesList,getActivity().findViewById(R.id.viewPager));
        RecyclerView.LayoutManager fLayoutManager = new LinearLayoutManager(view.getContext());
        filesRecyclerView.setLayoutManager(fLayoutManager);
        filesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        filesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        filesRecyclerView.setHasFixedSize(true);
        filesRecyclerView.setAdapter(fAdapter);
        final DecimalFormat df = new DecimalFormat("#.##");
        final FileListerDialog fileListerDialog = FileListerDialog.createFileListerDialog(this.getContext());
        fileListerDialog.setFileFilter(FileListerDialog.FILE_FILTER.ALL_FILES);

        fileListerDialog.setOnFileSelectedListener(new OnFileSelectedListener() {
            @Override
            public void onFileSelected(File file, String path) {
                FilesRow filesRow = new FilesRow(file.getName(),file.getPath(),
                        String.valueOf(df.format(file.length()/(1024.0*1024.0)))+"MB");
                if(contains_file(filesRow)){
                    Snackbar.make(view,"File already hosted",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    filesList.add(filesRow);
                    Snackbar.make(view,file.getName() + " added",Snackbar.LENGTH_SHORT).show();
                    fAdapter.notifyDataSetChanged();
                }
            }
        });
        add_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileListerDialog.show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fAdapter.getFilter().filter(s);
                return false;
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    public boolean contains_file(FilesRow obj) {
        for(FilesRow file:filesList){
            if((file.getPath()).equals(obj.getPath())){
                return true;
            }
        }
        return false;
    }
}
