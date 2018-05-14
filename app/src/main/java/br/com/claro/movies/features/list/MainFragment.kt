package br.com.claro.movies.features.list

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.claro.movies.R
import br.com.claro.movies.common.getErrorMessage
import br.com.claro.movies.databinding.FragmentMainBinding
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.features.common.EndlessRecyclerViewScrollListener
import br.com.claro.movies.features.common.ItemClick
import br.com.claro.movies.features.detail.DetailsActivity


class MainFragment : Fragment(), ItemClick {

    private lateinit var binding: FragmentMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var model: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)

        val toolbar = binding.incToolbar!!.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.app_name)

        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager
        binding.rvListing.setHasFixedSize(true)
        binding.rvListing.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                model.loadMovies(page)
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.model = model

        model.movies.observe(this, Observer {
            binding.loading.visibility = View.GONE
            showList(it)
        })

        model.moviesError.observe(this, Observer(this::handleError))
    }

    override fun onResume() {
        super.onResume()
        model.loadMovies()
    }

    private fun handleError(it: Throwable?) {

        binding.loading.visibility = View.GONE

        Snackbar.make(binding.rootView, it.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    binding.loading.visibility = View.VISIBLE
                    model.loadMovies()
                }
                .show()
    }

    private fun showList(it: List<Movie>?) {

        it?.let {
            if (binding.rvListing.adapter == null) {
                movieAdapter = MovieAdapter(it, this)
                binding.rvListing.adapter = movieAdapter
            } else {
                movieAdapter.updateItems(it)
            }
        }
    }

    override fun onItemClick(movie: Movie, image: ImageView) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", movie.id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, image, "name")
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    override fun onStop() {
        model.onStop()
        super.onStop()
    }
}