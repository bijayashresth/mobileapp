package com.exam.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.exam.assignment.R
import com.exam.assignment.model.RelatedTopics

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_topic_details, container, false)
        val bundle = this.arguments

        val topic = bundle?.get("topic") as RelatedTopics

        // Todo : can add glide listener to show progressbar
        Glide.with(this).load("https://duckduckgo.com/${topic.icon?.URL}")
            .placeholder(R.drawable.ic_no_image).into(view.findViewById(R.id.ivImage))
        view.findViewById<TextView>(R.id.tvText).text = topic.Text

        return view
    }
}