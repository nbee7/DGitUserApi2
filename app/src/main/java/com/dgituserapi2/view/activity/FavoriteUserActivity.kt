package com.dgituserapi2.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dgituserapi2.R
import com.dgituserapi2.database.FavoriteUser
import com.dgituserapi2.databinding.ActivityFavoriteUserBinding
import com.dgituserapi2.utility.ViewModelFactory
import com.dgituserapi2.view.adapter.FavoriteUserAdapter
import com.dgituserapi2.viewmodel.FavoriteUserViewModel

class FavoriteUserActivity : AppCompatActivity(), FavoriteUserAdapter.OnUserItemListener {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val favViewModel = obtainViewModel(this)
        favViewModel.getAllListFavorite().observe(this, { favList ->
            if (favList != null) {
                adapter.setListFavorite(favList)
            }
        })

        adapter = FavoriteUserAdapter(this, this)
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUserItemClicked(data: FavoriteUser) {
        data.username?.let { DetailUserActivity.start(this, it) }
    }
}