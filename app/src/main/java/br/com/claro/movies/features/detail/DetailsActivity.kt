package br.com.claro.movies.features.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import br.com.claro.movies.R
import br.com.claro.movies.common.getErrorMessage
import br.com.claro.movies.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var model: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        model = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.model = model
        binding.setLifecycleOwner(this)
        setSupportActionBar(binding.incToolbar!!.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.swFavorites.setOnClickListener {
            if (binding.swFavorites.isChecked) {
                model.addToFavorites()
            } else {
                model.removeFromFavorites()
            }
        }

        model.movie.observe(this, Observer { binding.loading.visibility = View.GONE })

        model.movieError.observe(this, Observer(this::handleError))
    }

    override fun onResume() {
        super.onResume()
        model.loadMovie(intent.getIntExtra("id", 0))
    }

    private fun handleError(it: Throwable?) {
        binding.loading.visibility = View.GONE
        Snackbar.make(binding.rootView, it.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    binding.loading.visibility = View.VISIBLE
                    model.loadMovie((intent.getIntExtra("id", 0)))
                }
                .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}