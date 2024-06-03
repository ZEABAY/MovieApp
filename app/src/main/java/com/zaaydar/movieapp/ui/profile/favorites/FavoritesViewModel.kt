package com.zaaydar.movieapp.ui.profile.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.moviedetail.MovieDetailResponse
import com.zaaydar.movieapp.util.MySingleton.favorites
import com.zaaydar.movieapp.util.toMovieDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    private val disposable = CompositeDisposable()

    val favoritesMovies = MutableLiveData<List<MoviesDto>>()
    val favoritesLoading = MutableLiveData<Boolean>()
    val favoritesError = MutableLiveData<Boolean>()


    fun getFavoriteMoviesFromApi() {
        favoritesLoading.value = true
        val movieDetailsList = mutableListOf<MoviesDto>()


        for (favId in favorites) {

            disposable.add(
                movieRepository.getDetails(favId.toInt())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<MovieDetailResponse>() {
                        override fun onSuccess(t: MovieDetailResponse) {
                            favoritesError.value = false
                            movieDetailsList.add(t.toMovieDto())
                            favoritesMovies.value = movieDetailsList

                        }

                        override fun onError(e: Throwable) {
                            favoritesError.value = true
                            e.printStackTrace()
                        }

                    })
            )

        }

        favoritesLoading.value = false

    }
}