package com.zaaydar.movieapp.ui.home.upcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.upcoming.UpcomingResponse
import com.zaaydar.movieapp.util.toMoviesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel

class UpcomingViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val upcomingMovies = MutableLiveData<List<MoviesDto>>()
    val upcomingLoading = MutableLiveData<Boolean>()
    val upcomingLoadingNext = MutableLiveData<Boolean>()
    val upcomingError = MutableLiveData<Boolean>()
    private var upcomingPage: Int = 1


    fun getUpcomingMoviesFromApi() {
        upcomingLoading.value = true

        disposable.add(
            movieRepository.getUpcoming(upcomingPage.toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UpcomingResponse>() {
                    override fun onSuccess(t: UpcomingResponse) {
                        upcomingError.value = false
                        upcomingLoading.value = false
                        upcomingMovies.value = t.toMoviesDto()
                        upcomingPage++
                    }

                    override fun onError(e: Throwable) {
                        upcomingLoading.value = false
                        upcomingError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getNextPageUpcomingMoviesFromApi() {
        upcomingLoading.value = true

        disposable.add(
            movieRepository.getUpcoming(upcomingPage.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<UpcomingResponse>() {
                    override fun onSuccess(t: UpcomingResponse) {

                        upcomingError.value = false
                        upcomingLoadingNext.value = false
                        upcomingMovies.value = t.toMoviesDto()
                        upcomingPage++
                    }

                    override fun onError(e: Throwable) {
                        upcomingLoadingNext.value = false
                        upcomingError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }


}