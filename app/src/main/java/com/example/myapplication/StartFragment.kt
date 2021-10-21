package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private var index = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        nextBtnClick()

        val view: View = binding.root
        return view
    }

    private fun nextBtnClick() {
        binding.nextBtn1.setOnClickListener {
            val switchFragment = requireActivity() as SwitchFragment
            try {
                MainFragment.firstVal = binding.firstValue.text.toString().toDouble()
            }
            catch (e: Exception) {
                MainFragment.firstVal = null
            }
            switchFragment.nextFragment(index)
        }
    }
}