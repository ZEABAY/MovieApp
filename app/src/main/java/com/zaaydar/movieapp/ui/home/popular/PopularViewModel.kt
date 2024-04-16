package com.zaaydar.movieapp.ui.home.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.util.toMoviesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val popularMovies = MutableLiveData<List<MoviesDto>>()
    val movieGenres = MutableLiveData<MovieGenre>()
    val popularLoading = MutableLiveData<Boolean>()
    val popularLoadingNext = MutableLiveData<Boolean>()
    val popularError = MutableLiveData<Boolean>()
    private var page: Int = 1

    fun getPopularMoviesFromApi() {
        popularLoading.value = true

        disposable.add(
            movieRepository.getPopulars(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularMoviesResponse>() {
                    override fun onSuccess(t: PopularMoviesResponse) {
                        popularError.value = false
                        popularLoading.value = false
                        popularMovies.value = t.toMoviesDto()
                        page++
                    }

                    override fun onError(e: Throwable) {
                        popularLoading.value = false
                        popularError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getNextPagePopularMoviesFromApi() {
        popularLoadingNext.value = true
        disposable.add(
            movieRepository.getPopulars(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularMoviesResponse>() {
                    override fun onSuccess(t: PopularMoviesResponse) {

                        popularError.value = false
                        popularLoadingNext.value = false
                        popularMovies.value = t.toMoviesDto()
                        page++
                    }

                    override fun onError(e: Throwable) {
                        popularLoadingNext.value = false
                        popularError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }


    fun getGenresFromApi() {
        disposable.add(
            movieRepository.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieGenre>() {
                    override fun onSuccess(t: MovieGenre) {
                        popularError.value = false
                        popularLoading.value = false
                        movieGenres.value = t
                    }

                    override fun onError(e: Throwable) {
                        popularLoading.value = false
                        popularError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

}