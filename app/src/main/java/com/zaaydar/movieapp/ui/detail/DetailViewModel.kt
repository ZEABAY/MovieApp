package com.zaaydar.movieapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaaydar.movieapp.data.repository.MovieRepository
import com.zaaydar.movieapp.model.MovieDetailDto
import com.zaaydar.movieapp.model.moviedetail.MovieDetailResponse
import com.zaaydar.movieapp.util.toDetailsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val movieDetail = MutableLiveData<MovieDetailDto>()
    val detailLoading = MutableLiveData<Boolean>()
    val detailError = MutableLiveData<Boolean>()

    fun getMovieDetailById(id: Int) {
        detailLoading.value = true

        disposable.add(
            movieRepository.getDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetailResponse>() {
                    override fun onSuccess(t: MovieDetailResponse) {
                        detailError.value = false
                        detailLoading.value = false
                        movieDetail.value = t.toDetailsDto()
                    }

                    override fun onError(e: Throwable) {
                        detailLoading.value = false
                        detailError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

}