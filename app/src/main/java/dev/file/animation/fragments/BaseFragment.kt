package dev.file.animation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dev.file.animation.R
import dev.file.animation.databinding.FragmentBaseBinding

class BaseFragment : Fragment(R.layout.fragment_base) {

    private var fragmentBaseFragment: FragmentBaseBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBaseBinding.bind(view)
        fragmentBaseFragment = binding

        binding.buttonActivity.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AnimationFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }
    }

    override fun onDestroyView() {
        fragmentBaseFragment = null
        super.onDestroyView()
    }
}