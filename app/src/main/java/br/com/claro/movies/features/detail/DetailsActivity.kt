package br.com.claro.movies.features.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import br.com.claro.movies.BuildConfig
import br.com.claro.movies.R
import br.com.claro.movies.common.getErrorMessage
import br.com.claro.movies.databinding.ActivityDetailsBinding
import br.com.claro.movies.dto.Backdrop
import br.com.claro.movies.dto.Trailer
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeIntents
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment

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

        model.images.observe(this, Observer {
            it?.let {
                loadImages(it)
            }
            binding.loading.visibility = View.GONE
        })

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
        model.trailersError.observe(this, Observer(this::handleError))
        model.imagesError.observe(this, Observer(this::handleError))

        binding.tabLayout.setupWithViewPager(binding.viewPagerImages, true)
    }

    private fun loadImages(it: List<Backdrop>) {
        binding.viewPagerImages.clipToPadding = false
        binding.viewPagerImages.setPadding(40, 0, 40, 0)
        binding.viewPagerImages.adapter = ImagePagerAdapter(it.take(10))
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

        binding.viewPagerVideos.clipToPadding = false
        binding.viewPagerVideos.setPadding(40, 0, 40, 0)
        if (it.size > 1) {
            binding.viewPagerVideos.adapter = VideoPagerAdapter(it.drop(1), object : ItemTrailerClick {
                override fun onItemClick(movie: Trailer) {
                    startActivity(YouTubeIntents.createPlayVideoIntentWithOptions(this@DetailsActivity, movie.key, true, false))
                }
            })
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
}