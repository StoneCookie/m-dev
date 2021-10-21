package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThirdBinding
    private var index = 2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        nextBtnClick()
        prevBtnClick()

        val view: View = binding.root
        return view
    }

    private fun nextBtnClick(){
        binding.nextBtn3.setOnClickListener {
            val switchFragment = requireActivity() as SwitchFragment
            MainFragment.action = binding.action.text.toString()
            switchFragment.nextFragment(index)
        }
    }

    private fun prevBtnClick(){
        binding.prevBtn3.setOnClickListener {
            val switchFragment = requireActivity() as SwitchFragment
            switchFragment.prevFragment(index)
        }
    }
}