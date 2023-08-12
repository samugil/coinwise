package com.app.coinwise.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.coinwise.R


class Graph7DaysFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph7_days, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            Graph1YearFragment()
    }
}