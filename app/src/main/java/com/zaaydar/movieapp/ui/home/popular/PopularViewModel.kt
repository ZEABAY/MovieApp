package com.zaaydar.movieapp.ui.home.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.PopularMoviesDto
import com.zaaydar.movieapp.model.PopularMoviesResponse
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

    val popularMovies = MutableLiveData<List<PopularMoviesDto>>()
    val movieGenres = MutableLiveData<MovieGenre>()
    val loading = MutableLiveData<Boolean>()
    val loadingNext = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    private var page: Int = 1

    fun getPopularMoviesFromApi() {
        loading.value = true

        disposable.add(
            movieRepository.getPopulars(page.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularMoviesResponse>() {
                    override fun onSuccess(t: PopularMoviesResponse) {
                        error.value = false
                        loading.value = false
                        popularMovies.value = t.toMoviesDto()
                        page++
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        error.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getNextPagePopularMoviesFromApi() {
        loadingNext.value = true

        disposable.add(
            movieRepository.getPopulars(page.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularMoviesResponse>() {
                    override fun onSuccess(t: PopularMoviesResponse) {

                        error.value = false
                        loadingNext.value = false
                        popularMovies.value = t.toMoviesDto()
                        page++
                    }

                    override fun onError(e: Throwable) {
                        loadingNext.value = false
                        error.value = true
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
                        error.value = false
                        loading.value = false
                        movieGenres.value = t
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        error.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

}