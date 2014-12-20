package com.jlmd.android.newfilmsmvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlmd.android.newfilmsmvp.R;
import com.jlmd.android.newfilmsmvp.domain.model.Movie;
import com.jlmd.android.newfilmsmvp.mvp.presenter.MoviesListPresenter;
import com.jlmd.android.newfilmsmvp.mvp.view.MoviesListView;
import com.jlmd.android.newfilmsmvp.ui.adapter.MoviesListRecyclerAdapter;
import com.jlmd.android.newfilmsmvp.ui.view.ClickRecyclerView;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author jlmd
 */
public class MoviesListFragment extends BaseFragment implements MoviesListView {

    @Inject
    protected MoviesListPresenter moviesListPresenter;

    @InjectView(R.id.rv_movies_list)
    protected ClickRecyclerView moviesListView;
    @InjectView(R.id.btn_sort_items)
    protected FloatingActionButton sortButton;

    private MoviesListRecyclerAdapter moviesListAdapter;
    private static final String TAG = MoviesListFragment.class.getName();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMoviesRecyclerView();
        attachSortButtonToRecycler();
        initPresenter();
    }

    private void initPresenter() {
        moviesListPresenter.setView(this);
        moviesListPresenter.initialize();
    }

    private void attachSortButtonToRecycler() {
        sortButton.attachToRecyclerView(moviesListView);
    }

    private void initMoviesRecyclerView() {
        moviesListView.setLayoutManager(new LinearLayoutManager(getActivity()
                .getApplicationContext()));
        moviesListView.setItemAnimator(new DefaultItemAnimator());
        moviesListAdapter = new MoviesListRecyclerAdapter(getActivity().getApplicationContext());
        moviesListView.setAdapter(moviesListAdapter);
        moviesListView.setOnItemClickListener(new ClickRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                moviesListPresenter.onItemSelected(position);
            }
        });
    }

    @OnClick(R.id.btn_sort_items)
    protected void onSortButtonClick() {
        moviesListPresenter.onSortButtonClick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void renderMovies(List<Movie> movies) {
        moviesListAdapter.updateItems(movies);
    }
}
