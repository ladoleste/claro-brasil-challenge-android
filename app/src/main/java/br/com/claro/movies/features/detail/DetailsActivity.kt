package br.com.claro.movies.features.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import br.com.claro.movies.BuildConfig
import br.com.claro.movies.R
import br.com.claro.movies.common.getErrorMessage
import br.com.claro.movies.databinding.ActivityDetailsBinding
import br.com.claro.movies.dto.Trailer
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment

class DetailsActivity : AppCompatActivity(), ItemTrailerClick {
    private lateinit var model: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var trailerAdapter: TrailerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        model = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.model = model
        binding.setLifecycleOwner(this)
        setSupportActionBar(binding.incToolbar!!.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.rvListing.isNestedScrollingEnabled = false
        binding.swFavorites.setOnClickListener {
            if (binding.swFavorites.isChecked) {
                model.addToFavorites()
            } else {
                model.removeFromFavorites()
            }
        }

        model.trailers.observe(this, Observer {
            it?.let {
                loadTrailers(it)
            }
            binding.loading.visibility = View.GONE
        })

        model.movie.observe(this, Observer {
            supportActionBar?.title = it?.title
            binding.loading.visibility = View.GONE
        })

        model.movieError.observe(this, Observer(this::handleError))
    }

    private fun loadTrailers(it: List<Trailer>) {
        val youTubePlayerFragment = fragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerFragment

        if (it.isEmpty()) {
            youTubePlayerFragment.view.visibility = View.GONE
            return
        }

        youTubePlayerFragment.initialize(BuildConfig.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, wasRestored: Boolean) {
                if (!wasRestored) {
                    player.cueVideo(it.first().key)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult) {
                if (errorReason.isUserRecoverableError) {
                    errorReason.getErrorDialog(this@DetailsActivity, 1).show()
                } else {
                    val errorMessage = String.format(getString(R.string.error_player), errorReason.toString())
                    Toast.makeText(this@DetailsActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        })

        if (binding.rvListing.adapter == null) {
            binding.rvListing.layoutManager = LinearLayoutManager(this)
            val trailers = it.toMutableList()
            if (trailers.isNotEmpty())
                trailers.removeAt(0)
            trailerAdapter = TrailerAdapter(trailers, this)
            binding.rvListing.adapter = trailerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra("id", 0)
        model.loadMovie(id)
        model.loadTrailers(id)
        model.loadImages(id)
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

    override fun onItemClick(movie: Trailer) {

    }
}