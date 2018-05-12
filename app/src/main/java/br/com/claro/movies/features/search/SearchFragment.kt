package br.com.claro.movies.features.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.claro.movies.R
import br.com.claro.movies.common.getErrorMessage
import br.com.claro.movies.databinding.FragmentSearchBinding
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.features.common.ItemClick
import br.com.claro.movies.features.detail.DetailsActivity

class SearchFragment : Fragment(), ItemClick {
    override fun onItemClick(movie: Movie) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", movie.id)
        startActivity(intent)
    }

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var model: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager
        binding.rvListing.setHasFixedSize(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)
        binding.model = model

        model.movies.observe(this, Observer {
            binding.loading.visibility = View.GONE
            showList(it)
        })

        model.moviesError.observe(this, Observer(this::handleError))
    }

    override fun onResume() {
        super.onResume()
        model.loadSuggestions("batman")
    }

    private fun handleError(it: Throwable?) {

        binding.loading.visibility = View.GONE

        Snackbar.make(binding.rootView, it.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    binding.loading.visibility = View.VISIBLE
                    model.loadSuggestions("batman")
                }
                .show()
    }

    private fun showList(it: List<Movie>?) {

        it?.let {
            if (binding.rvListing.adapter == null) {
                searchAdapter = SearchAdapter(it, this)
                binding.rvListing.adapter = searchAdapter
            } else {
                searchAdapter.updateItems(it)
            }
        }
    }
}