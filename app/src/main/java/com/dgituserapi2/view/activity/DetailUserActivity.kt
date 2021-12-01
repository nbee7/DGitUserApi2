package com.dgituserapi2.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dgituserapi2.R
import com.dgituserapi2.database.FavoriteUser
import com.dgituserapi2.databinding.ActivityDetailUserBinding
import com.dgituserapi2.model.UserDetailResponse
import com.dgituserapi2.utility.BundleKeys
import com.dgituserapi2.utility.ViewModelFactory
import com.dgituserapi2.utility.setImageUrl
import com.dgituserapi2.utility.toShortNumberDisplay
import com.dgituserapi2.view.adapter.ViewPagerAdapter
import com.dgituserapi2.view.adapter.ViewPagerAdapter.Companion.TAB_TITLES
import com.dgituserapi2.viewmodel.DetailUserViewModel
import com.dgituserapi2.viewmodel.FavoriteUserUpdateViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private val detailViewModel: DetailUserViewModel by viewModels()
    private var username: String? = null
    private var mediator: TabLayoutMediator? = null
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var favUpdateViewModel: FavoriteUserUpdateViewModel
    private var favUser: FavoriteUser? = null
    private var userId: String? = null
    private var usernameFav: String? = null
    private var avatar: String? = null

    companion object {
        fun start(context: Context, username: String) {
            Intent(context, DetailUserActivity::class.java).apply {
                this.putExtra(BundleKeys.USERNAME, username)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent?.getStringExtra(BundleKeys.USERNAME)

        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        username?.let { detailViewModel.detailUser(it) }

        setObserver()

        initViewPager()
        Log.d("TAG ", userId + usernameFav + avatar)

        favUpdateViewModel = obtainViewModel(this@DetailUserActivity)

        binding.ibFavorite.setOnClickListener {
            if (binding.ibFavorite.tag.toString() == "unlike") {
                binding.ibFavorite.setImageResource(R.drawable.ic_baseline_favorited_24)
                favUpdateViewModel.insert(favUser as FavoriteUser)
                binding.ibFavorite.tag = "like"
                Toast.makeText(applicationContext, "user added to favorites", Toast.LENGTH_SHORT).show()
            } else {
                binding.ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                favUpdateViewModel.delete(favUser as FavoriteUser)
                binding.ibFavorite.tag = "unlike"
                Toast.makeText(applicationContext, "user removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObserver() {
        detailViewModel.getDetail().observe(this, {
            if (it != null) {
                updateUserData(it)
            }
        })
    }

    private fun updateUserData(data: UserDetailResponse) {
        binding.imgUserDetail.setImageUrl(this, data.avatarUrl, binding.pbDetailUser)
        binding.apply {
            tvName.text = data.name
            tvCompany.text = data.company
            tvAddress.text = data.location
            tvFollowers.text = data.followers.toShortNumberDisplay()
            tvFollowings.text = data.following.toShortNumberDisplay()
            tvRepo.text = data.publicRepos.toShortNumberDisplay()
        }
        favUser = FavoriteUser(data.id.toString(), data.login, data.avatarUrl)
        checkFav(data.id.toString())
    }

    private fun initViewPager() {
        val sectionPagerAdapter = ViewPagerAdapter(this, username)
        binding.vpFollowRepo.apply {
            adapter = sectionPagerAdapter
            offscreenPageLimit = 3
        }
        mediator = TabLayoutMediator(binding.tabLayout, binding.vpFollowRepo) { tab, pos ->
            tab.text = when (pos) {
                0 -> getString(TAB_TITLES[0])
                1 -> getString(TAB_TITLES[1])
                else -> getString(TAB_TITLES[2])
            }
        }
        mediator?.attach()
    }

    private fun checkFav(userId: String) {
        binding.ibFavorite.visibility = View.VISIBLE
        favUpdateViewModel.checkFavorite(userId).observe(this, { check ->
            if (check) {
                binding.ibFavorite.tag = "like"
                binding.ibFavorite.setImageResource(R.drawable.ic_baseline_favorited_24)
            } else {
                binding.ibFavorite.tag = "unlike"
                binding.ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserUpdateViewModel::class.java]
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}