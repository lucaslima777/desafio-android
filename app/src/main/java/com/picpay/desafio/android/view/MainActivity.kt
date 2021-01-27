package com.picpay.desafio.android.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.ResultState
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.view.adapter.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }
    private val adapter: UserListAdapter by lazy { UserListAdapter() }
    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        fetchUsers()
    }

    private fun fetchUsers() {
        Observer<ResultState> { response ->
            showProgressBar(show = false)
            when (response) {
                is ResultState.Success -> showListUsers(response.data)
                is ResultState.Error -> showError()
            }
        }.apply {
            showProgressBar(show = true)
            userViewModel.users.observe(this@MainActivity, this)
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun showListUsers(list: List<User>) {
        adapter.users = list
    }

    private fun showError() {
        val message = getString(R.string.error)
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showProgressBar(show: Boolean) {
        when (show) {
            true -> progressBar.visibility = View.VISIBLE
            else -> progressBar.visibility = View.GONE
        }
    }
}