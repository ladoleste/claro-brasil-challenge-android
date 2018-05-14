package br.com.claro.movies.features.search

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.claro.movies.R
import br.com.claro.movies.common.Util
import br.com.claro.movies.common.getErrorMessage
import br.com.claro.movies.databinding.FragmentSearchBinding
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.features.common.ItemClick
import br.com.claro.movies.features.detail.DetailsActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class SearchFragment : Fragment(), ItemClick {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var model: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title = getString(R.string.title_search)

        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager
        binding.rvListing.setHasFixedSize(true)

        RxTextView.textChanges(binding.etSearch)
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.loading.visibility = View.VISIBLE
                    model.loadSuggestions(it.toString())
                }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)
        binding.model = model

        model.movies.observe(this, Observer {
            binding.loading.visibility = View.GONE
            it?.let {
                showList(it)
            }
        })

        model.moviesError.observe(this, Observer(this::handleError))

        Util.showKeyboard()
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

    private fun showList(it: List<Movie>) {
        if (it.isEmpty() || binding.etSearch.text.isBlank()) {
            binding.rvListing.visibility = View.GONE
            binding.tvNoResults.visibility = View.VISIBLE
            return
        }

        binding.rvListing.visibility = View.VISIBLE
        binding.tvNoResults.visibility = View.GONE

        if (binding.rvListing.adapter == null) {
            searchAdapter = SearchAdapter(it, this)
            binding.rvListing.adapter = searchAdapter
        } else {
            searchAdapter.updateItems(it)
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
