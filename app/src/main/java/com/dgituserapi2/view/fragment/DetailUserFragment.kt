package com.dgituserapi2.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dgituserapi2.databinding.FragmentDetailUserBinding
import com.dgituserapi2.model.UserItems
import com.dgituserapi2.utility.BundleKeys.ARG_SECTION_INDEX
import com.dgituserapi2.utility.BundleKeys.ARG_USERNAME
import com.dgituserapi2.utility.gone
import com.dgituserapi2.utility.visible
import com.dgituserapi2.view.activity.DetailUserActivity
import com.dgituserapi2.view.adapter.UserAdapter
import com.dgituserapi2.viewmodel.DetailUserViewModel

class DetailUserFragment : Fragment(), UserAdapter.OnUserItemListener {

    private lateinit var binding: FragmentDetailUserBinding
    private var username: String? = null
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {
        fun newInstance(index: Int, username: String?) =
                DetailUserFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_SECTION_INDEX, index)
                        putString(ARG_USERNAME, username)
                    }
                }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (activity != null) {
            username = arguments?.getString(ARG_USERNAME)

            detailUserViewModel =
                    ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

            showLoading(true)

            setUpViewPager()

            setRecyleView()

            setObserver()
        }
    }

    private fun setObserver() {
        detailUserViewModel.getFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                updateListUser(it)
                showLoading(false)
            }
        })
        detailUserViewModel.getFollowings().observe(viewLifecycleOwner, {
            if (it != null) {
                updateListUser(it)
                showLoading(false)
            }
        })
    }

    private fun setRecyleView() {
        userAdapter = UserAdapter(requireContext(), mutableListOf(), this)
        binding.rvListUser.apply {
            layoutManager =
                    LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun setUpViewPager() {
        var index: Int? = 0
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_INDEX, 0)
        }
        when (index) {
            1 -> username?.let { detailUserViewModel.userFollowers(it) }
            2 -> username?.let { detailUserViewModel.userFollowings(it) }
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

    override fun onUserItemClicked(data: UserItems) {
        DetailUserActivity.start(requireContext(), data.login)
    }
}