package com.exam.assignment.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.exam.assignment.BuildConfig
import com.exam.assignment.R
import com.exam.assignment.model.RelatedTopics
import com.exam.assignment.databinding.ActivityMainBinding


class ListActivity : AppCompatActivity() {
    lateinit var topicsAdapter: TopicsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TopicsViewModel
    var tabletSize = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        tabletSize = resources.getBoolean(R.bool.isTablet)
        initViewModel()
        initViews()
        initObservers()
        viewModel.getRecords(BuildConfig.api_name)
    }

    private fun initObservers() {
        viewModel.dictionaryResponseLiveData.observe(this) {
            if (it?.RelatedTopics?.isNotEmpty() == true) {
                topicsAdapter.setItems(it.RelatedTopics)
            } else {
                topicsAdapter.setItems(emptyList())
                Toast.makeText(
                    this@ListActivity, "No data found..", Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.progressLiveData.observe(this) {
            if (it) {
                binding.rlProgress.visibility = View.VISIBLE
                binding.rvList.visibility = View.GONE
            } else {
                binding.rlProgress.visibility = View.GONE
                binding.rvList.visibility = View.VISIBLE
            }
        }

        viewModel.showAlertLiveData.observe(this) {
            if (it != null && it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initViews() {
        topicsAdapter = TopicsAdapter(object : TopicsAdapter.OnItemClickListener {
            override fun onItemClick(topic: RelatedTopics) {
                Log.e("!_@_@", "${topic.Result}")
                if (tabletSize) {
                    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                    val operatorDetailsFragment = DetailsFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("topic", topic)
                    operatorDetailsFragment.arguments = bundle
                    transaction.replace(R.id.frameLayout, operatorDetailsFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else {
                    startActivity(
                        Intent(this@ListActivity, DetailActivity::class.java).putExtra(
                            "topic",
                            topic
                        )
                    )
                }
            }
        })
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvList.layoutManager = llm
        binding.rvList.adapter = topicsAdapter

        binding.edtVal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                topicsAdapter.filter(query.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun initViewModel() {
        //initialize viewModel
        viewModel = ViewModelProvider(this).get(TopicsViewModel::class.java)
    }
}