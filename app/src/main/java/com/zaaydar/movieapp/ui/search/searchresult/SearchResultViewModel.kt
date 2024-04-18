package com.zaaydar.movieapp.ui.search.searchresult

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.searchresult.SearchResultResponse
import com.zaaydar.movieapp.util.toMoviesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val searchResultMovies = MutableLiveData<List<MoviesDto>>()
    val searchResultLoading = MutableLiveData<Boolean>()
    val searchResultNextLoading = MutableLiveData<Boolean>()
    val searchResultError = MutableLiveData<Boolean>()
    private var searchResultPage: Int = 1

    fun getSearchResultMoviesFromApi(searchText: String) {
        searchResultLoading.value = true

        disposable.add(
            movieRepository.getSearch(searchResultPage, searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResultResponse>() {
                    override fun onSuccess(t: SearchResultResponse) {
                        searchResultError.value = false
                        searchResultLoading.value = false
                        searchResultMovies.value = t.toMoviesDto()
                        searchResultPage++
                    }

                    override fun onError(e: Throwable) {
                        searchResultLoading.value = false
                        searchResultError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getNextSearchResultMoviesFromApi(searchText: String) {
        searchResultNextLoading.value = true

        disposable.add(
            movieRepository.getSearch(searchResultPage, searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResultResponse>() {
                    override fun onSuccess(t: SearchResultResponse) {

                        searchResultError.value = false
                        searchResultNextLoading.value = false
                        searchResultMovies.value = t.toMoviesDto()
                        searchResultPage++
                    }

                    override fun onError(e: Throwable) {
                        searchResultNextLoading.value = false
                        searchResultError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

}