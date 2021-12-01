package com.dgituserapi2.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dgituserapi2.databinding.FragmentRepositoryBinding
import com.dgituserapi2.model.UserRepositoryResponse
import com.dgituserapi2.utility.BundleKeys.ARG_USERNAME
import com.dgituserapi2.utility.gone
import com.dgituserapi2.utility.visible
import com.dgituserapi2.view.adapter.RepositoryAdapter
import com.dgituserapi2.viewmodel.DetailUserViewModel


class RepositoryFragment : Fragment(), RepositoryAdapter.OnUserItemListener {

    private lateinit var binding: FragmentRepositoryBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var reposAdapter: RepositoryAdapter
    private var username: String? = null

    companion object {
        fun newInstance(username: String?) =
                RepositoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_USERNAME, username)
                    }
                }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (activity != null) {
            username = arguments?.getString(ARG_USERNAME)

            detailUserViewModel =
                    ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

            username?.let { detailUserViewModel.userRepository(it) }

            showLoading(true)

            setRecyleView()

            setObserver()
        }
    }

    private fun setObserver() {
        detailUserViewModel.getRepository().observe(viewLifecycleOwner, {
            if (it != null) {
                updateListUser(it)
                showLoading(false)
            }
        })
    }

    private fun setRecyleView() {
        reposAdapter = RepositoryAdapter(mutableListOf(), this)
        binding.rvListRepos.apply {
            layoutManager =
                    LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            setHasFixedSize(true)
            adapter = reposAdapter
        }
    }

    private fun updateListUser(listRepos: List<UserRepositoryResponse>) {
        reposAdapter.data.clear()
        reposAdapter.data.addAll(listRepos)
        reposAdapter.notifyDataSetChanged()
        if (listRepos.isEmpty()) {
            binding.rvListRepos.gone()
            binding.tvEmpty.visible()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvListRepos.gone()
            binding.progressBar.visible()
            binding.tvEmpty.gone()
        } else {
            binding.rvListRepos.visible()
            binding.progressBar.gone()
        }
    }

    override fun onUserItemClicked(data: UserRepositoryResponse) {
        val url: Uri? = data.reposUrl?.toUri()
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    }

}