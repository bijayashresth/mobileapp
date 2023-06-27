package com.exam.assignment.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.exam.assignment.R
import com.exam.assignment.model.RelatedTopics
import com.exam.assignment.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (intent?.hasExtra("topic") == true) {
            val topic = intent.extras?.get("topic") as RelatedTopics

            // Todo : can add glide listener to show progressbar
            Glide.with(this).load("https://duckduckgo.com/${topic.icon?.URL}")
                .placeholder(R.drawable.ic_no_image).into(binding.ivImage)

            binding.tvText.text = topic.Text
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}