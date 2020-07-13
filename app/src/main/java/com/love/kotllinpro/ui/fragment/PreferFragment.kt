package com.love.kotllinpro.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.love.kotllinpro.R
import com.love.kotllinpro.base.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class PreferFragment : Fragment() {
    companion object {
        fun getInstance(): PreferFragment = PreferFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prefered, container, false)
    }

}
