package com.zaaydar.movieapp.ui.home.toprated

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import com.zaaydar.movieapp.util.toMoviesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val topRatedMovies = MutableLiveData<List<MoviesDto>>()
    val topRatedLoading = MutableLiveData<Boolean>()
    val topRatedLoadingNext = MutableLiveData<Boolean>()
    val topRatedError = MutableLiveData<Boolean>()
    private var topRatedPage: Int = 1


    fun getTopRatedMoviesFromApi() {
        topRatedLoading.value = true

        disposable.add(
            movieRepository.getTopRated(topRatedPage.toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TopRatedResponse>() {
                    override fun onSuccess(t: TopRatedResponse) {
                        topRatedError.value = false
                        topRatedLoading.value = false
                        topRatedMovies.value = t.toMoviesDto()
                        topRatedPage++
                    }

                    override fun onError(e: Throwable) {
                        topRatedLoading.value = false
                        topRatedError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getNextPageTopRatedMoviesFromApi() {
        topRatedLoading.value = true

        disposable.add(
            movieRepository.getTopRated(topRatedPage.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TopRatedResponse>() {
                    override fun onSuccess(t: TopRatedResponse) {

                        topRatedError.value = false
                        topRatedLoadingNext.value = false
                        topRatedMovies.value = t.toMoviesDto()
                        topRatedPage++
                    }

                    override fun onError(e: Throwable) {
                        topRatedLoadingNext.value = false
                        topRatedError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

}