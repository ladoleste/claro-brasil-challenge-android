package br.com.claro.movies.features.favorites

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.claro.movies.R
import br.com.claro.movies.common.Util
import br.com.claro.movies.databinding.FragmentFavoritesBinding
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.features.common.ItemClick
import br.com.claro.movies.features.detail.DetailsActivity

class FavoritesFragment : Fragment(), ItemClick {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var model: FavoritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)

        val toolbar = binding.incToolbar!!.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.title_favorites)

        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager

        Util.hideKeyboard(binding.rootView)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(FavoritesViewModel::class.java)
        binding.model = model
    }

    override fun onResume() {
        super.onResume()
        model.loadFavorites().observe(this, Observer {
            binding.loading.visibility = View.GONE
            showList(it)
        })
    }

    private fun showList(it: List<Movie>?) {
        it?.let {
            if (it.isEmpty()) {
                binding.rvListing.visibility = View.GONE
                binding.tvNoFavorites.visibility = View.VISIBLE
            } else {
                binding.rvListing.visibility = View.VISIBLE
                binding.tvNoFavorites.visibility = View.GONE

                if (binding.rvListing.adapter == null) {
                    favoritesAdapter = FavoritesAdapter(it, this)
                    binding.rvListing.adapter = favoritesAdapter
                } else {
                    favoritesAdapter.updateItems(it)
                }
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
}