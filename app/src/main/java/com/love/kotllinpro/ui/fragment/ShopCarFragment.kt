package com.love.kotllinpro.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.love.kotllinpro.R

/**
 * A simple [Fragment] subclass.
 */
class ShopCarFragment : Fragment() {
    companion object{
        fun getInstance():ShopCarFragment=ShopCarFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_car, container, false)
    }

}
