package com.amarturelo.usersgithub.followers.demo.presentation.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amarturelo.usersgithub.followers.commons.Constants.ARG.USERNAME
import com.amarturelo.usersgithub.followers.demo.R
import com.amarturelo.usersgithub.followers.demo.databinding.FragmentDemoBinding

class DemoFragment : Fragment() {

    private var _binding: FragmentDemoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(
                R.id.action_demoFragment_to_followersFragment,
                Bundle().apply {
                    putString(USERNAME, "amarturelo")
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}