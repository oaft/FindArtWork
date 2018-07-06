package com.example.inquallity.findartwork.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.inquallity.findartwork.AppDelegate;
import com.example.inquallity.findartwork.R;
import com.example.inquallity.findartwork.adapter.AlbumsListAdapter;
import com.example.inquallity.findartwork.adapter.OnItemClickListener;
import com.example.inquallity.findartwork.api.ItunesApi;
import com.example.inquallity.findartwork.loader.SearchResultLoader;
import com.example.inquallity.findartwork.model.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Olga Aleksandrova on 01-Jul-18.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Result> {

    @BindView(R.id.et_query) EditText mEtQuery;
    @BindView(R.id.rv_albums_list) RecyclerView mRecyclerView;
    @BindView(R.id.pb_load_indicator) ProgressBar mProgressBar;
    @BindView(R.id.tv_error_msg) TextView mTvErrorMsg;

    private Unbinder mUnbinder;
    private AlbumsListAdapter mAlbumsListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fmt_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager;
        if (view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getActivity());
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        }

        mAlbumsListAdapter = new AlbumsListAdapter();
        mAlbumsListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, String idCollection, String albumName) {
                final Fragment targetFragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("KEY_ID", idCollection);
                bundle.putString("KEY_NAME", albumName);
                targetFragment.setArguments(bundle);
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_content, targetFragment)
                            .addToBackStack(targetFragment.getClass().getName())
                            .commit();
                }
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAlbumsListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            getLoaderManager().initLoader(R.id.search_result_loader, null, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setTitle(R.string.app_name);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.btn_search)
    void onSearchClick() {
        getLoaderManager().restartLoader(R.id.search_result_loader, null, this);
        mProgressBar.setVisibility(View.VISIBLE);
        mTvErrorMsg.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public Loader<Result> onCreateLoader(int id, @Nullable Bundle args) {
        mRecyclerView.setVisibility(View.GONE);
        return new SearchResultLoader(getActivity(), AppDelegate.sRetrofit.create(ItunesApi.class), mEtQuery.getText().toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Result> loader, Result result) {
        mProgressBar.setVisibility(View.GONE);
        if (result != null) {
            if (result.isSuccess() && !result.getData().isEmpty()) {
                mAlbumsListAdapter.showResults(result.getData());
                mRecyclerView.setVisibility(View.VISIBLE);
            } else if (result.isSuccess()) {
                mTvErrorMsg.setText(R.string.nth_found_msg);
                mTvErrorMsg.setVisibility(View.VISIBLE);
            } else {
                mTvErrorMsg.setText(getString(R.string.error_msg, result.getErrorMessage()));
                mTvErrorMsg.setVisibility(View.VISIBLE);
            }
        } else {
            mTvErrorMsg.setText(R.string.nth_found_msg);
            mTvErrorMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Result> loader) {

    }
}
