package com.zaaydar.movieapp.ui.home.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.MovieApiService
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.PopularMoviesDto
import com.zaaydar.movieapp.model.PopularMoviesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieApiService: MovieApiService
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val popularMovies = MutableLiveData<List<PopularMoviesDto>>()
    val movieGenres = MutableLiveData<MovieGenre>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()


    fun getPopularMoviesFromApi() {
        loading.value = false

        disposable.add(
            movieApiService.getPopulars()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularMoviesResponse>() {
                    override fun onSuccess(t: PopularMoviesResponse) {
                        val list = arrayListOf<PopularMoviesDto>()

                        for (item in t.results) {

                            val dto = PopularMoviesDto(
                                item.id,
                                item.title,
                                item.genreIds,
                                item.voteAverage,
                                item.posterPath
                            )

                            list.add(dto)
                        }
                        error.value = false
                        loading.value = false
                        popularMovies.value = list

                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        error.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getGenresFromApi() {
        disposable.add(
            movieApiService.getGenres()
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