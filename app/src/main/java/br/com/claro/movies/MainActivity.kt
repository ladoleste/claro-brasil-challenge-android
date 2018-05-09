package br.com.claro.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.repository.MoviesRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repo: MoviesRepository

    init {
        ClaroApplication.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        repo.getMovies(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe ({ x -> x.forEach{ Timber.d(it.description)} },
//                        {
//                            Timber.e(it)
//                        })
    }
}
