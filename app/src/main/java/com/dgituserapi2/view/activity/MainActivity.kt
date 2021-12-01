package com.dgituserapi2.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dgituserapi2.R
import com.dgituserapi2.databinding.ActivityMainBinding
import com.dgituserapi2.model.UserItems
import com.dgituserapi2.utility.gone
import com.dgituserapi2.utility.visible
import com.dgituserapi2.view.adapter.UserAdapter
import com.dgituserapi2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), UserAdapter.OnUserItemListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name)

        searchUser(getString(R.string.example_search))

        setRecyleView()

        setObserver()
    }

    private fun setObserver() {
        mainViewModel.getListSearch().observe(this, {
            if (it != null) {
                updateListUser(it)
                showLoading(false)
            }
        })
    }

    private fun setRecyleView() {
        userAdapter = UserAdapter(this, mutableListOf(), this)
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun updateListUser(listUser: List<UserItems>) {
        userAdapter.data.clear()
        userAdapter.data.addAll(listUser)
        userAdapter.notifyDataSetChanged()
        if (listUser.isEmpty()) {
            binding.rvListUser.gone()
            binding.tvEmpty.visible()
        }
    }

    private fun searchUser(query: String) {
        showLoading(true)
        mainViewModel.searchUser(query)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        createSearchViewMenu(menu) {
            searchUser(it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onUserItemClicked(data: UserItems) {
        DetailUserActivity.start(this, data.login)
    }

    private fun createSearchViewMenu(menu: Menu?, listener: (String) -> Unit) {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            this.setBackgroundColor(Color.WHITE)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
        }

        val searchEditText = searchView.findViewById<View>(R.id.search_src_text) as EditText
        TextViewCompat.setTextAppearance(searchEditText, R.style.searchText)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { listener.invoke(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting) {
            val mIntent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.favorite) {
            val mIntent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvListUser.gone()
            binding.progressBar.visible()
            binding.tvEmpty.gone()
        } else {
            binding.rvListUser.visible()
            binding.progressBar.gone()
        }
    }
}