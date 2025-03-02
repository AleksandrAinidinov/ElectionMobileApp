package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a4pg.R
import com.example.a4pg.PostAdapter
import com.example.a4pg.Post

class HottestUpdatesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hottest_updates, container, false)

        // Set up RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PostAdapter(getPosts())

        return view
    }

    private fun getPosts(): List<Post> {
        return listOf(
            Post(
                avatarResId = R.drawable.ford,
                name = "Doug Ford",
                date = "Feb 28",
                content = "Thank you, Ontario!\n\nI will never take your support for granted. I will wake up everyday to fight like I’ve never fought before.\n\nTogether, we’ll protect Ontario."
            ),
            Post(
                avatarResId = R.drawable.mike,
                name = "Mike Schreiner",
                date = "Feb 27",
                content = "Today folks from across Ontario are heading to the polls.\n\nI know folks from across the province are ready for change. They're ready for a government that puts fairness first. Fairness for our housing market, fairness for our healthcare system, and fairness for our environment.\n\nNo matter who you're voting for, make sure you VOTE. Polls close at 9PM."
            ),
            Post(
                avatarResId = R.drawable.bonnie,
                name = "Bonnie Crombie",
                date = "Mar 1",
                content = "Rebuilding takes time.\n\nOntario’s Liberals fought hard in this tough snap election to win official party status for the first time in seven years. And we aren't going anywhere. Last night, we earned 30% of the vote and we will continue building. Thank you to everyone who voted for change, who knocked on doors and showed up every day to help our campaign.\n\nI’m so proud to be your Ontario Liberal Leader!"
            ),
            Post(
                avatarResId = R.drawable.ford,
                name = "Doug Ford",
                date = "Feb 28",
                content = "Thank you, Ontario!\n\nI will never take your support for granted. I will wake up everyday to fight like I’ve never fought before.\n\nTogether, we’ll protect Ontario."
            ),
        )
    }
}