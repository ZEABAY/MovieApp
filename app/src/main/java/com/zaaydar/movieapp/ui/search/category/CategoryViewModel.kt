package com.zaaydar.movieapp.ui.search.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.category.CategoryResponse
import com.zaaydar.movieapp.util.toMoviesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel

class CategoryViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val disposable = CompositeDisposable()

    val categoryMovies = MutableLiveData<List<MoviesDto>>()
    val categoryLoading = MutableLiveData<Boolean>()
    val categoryLoadingNext = MutableLiveData<Boolean>()
    val categoryError = MutableLiveData<Boolean>()
    private var page: Int = 1

    fun getMoviesByCategoryFromApi(genre: Int) {
        categoryLoading.value = true
        disposable.add(
            movieRepository.getMoviesByCategory(genre, page.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CategoryResponse>() {
                    override fun onSuccess(t: CategoryResponse) {
                        categoryError.value = false
                        categoryLoading.value = false
                        categoryMovies.value = t.toMoviesDto()
                        page++
                    }

                    override fun onError(e: Throwable) {
                        categoryLoading.value = false
                        categoryError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    fun getNextPageCategoryFromApi(genre: Int) {
        categoryLoadingNext.value = true

        disposable.add(
            movieRepository.getMoviesByCategory(genre, page.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CategoryResponse>() {
                    override fun onSuccess(t: CategoryResponse) {

                        categoryError.value = false
                        categoryLoadingNext.value = false
                        categoryMovies.value = t.toMoviesDto()
                        page++
                    }

                    override fun onError(e: Throwable) {
                        categoryLoadingNext.value = false
                        categoryError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

}