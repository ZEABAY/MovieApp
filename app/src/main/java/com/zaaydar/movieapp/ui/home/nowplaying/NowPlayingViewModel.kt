package com.zaaydar.movieapp.ui.home.nowplaying

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.util.toMoviesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val nowPlayingMovies = MutableLiveData<List<MoviesDto>>()
    val nowPlayingLoading = MutableLiveData<Boolean>()
    val nowPlayingLoadingNext = MutableLiveData<Boolean>()
    val nowPlayingError = MutableLiveData<Boolean>()
    private var nowPlayingPage: Int = 1


    fun getNowPlayingMoviesFromApi() {
        nowPlayingLoading.value = true

        disposable.add(
            movieRepository.getNowPlayings(nowPlayingPage).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NowPlayingResponse>() {
                    override fun onSuccess(t: NowPlayingResponse) {
                        nowPlayingError.value = false
                        nowPlayingLoading.value = false
                        nowPlayingMovies.value = t.toMoviesDto()
                        nowPlayingPage++
                    }

                    override fun onError(e: Throwable) {
                        nowPlayingLoading.value = false
                        nowPlayingError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getNextPageNowPlayingMoviesFromApi() {
        nowPlayingLoadingNext.value = true

        disposable.add(
            movieRepository.getNowPlayings(nowPlayingPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NowPlayingResponse>() {
                    override fun onSuccess(t: NowPlayingResponse) {

                        nowPlayingError.value = false
                        nowPlayingLoadingNext.value = false
                        nowPlayingMovies.value = t.toMoviesDto()
                        nowPlayingPage++
                    }

                    override fun onError(e: Throwable) {
                        nowPlayingLoadingNext.value = false
                        nowPlayingError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

}