package com.example.stuffy.presentation.transaction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stuffy.core.data.Resource
import com.example.stuffy.core.domain.model.Take
import com.example.stuffy.core.ui.*
import com.example.stuffy.databinding.FragmentTransactionTakeBinding
import com.example.stuffy.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransactionTakeFragment : Fragment() {
    private var _binding: FragmentTransactionTakeBinding? = null
    private val transactionTakeViewModel : TransactionTakeViewModel by viewModel()
    private lateinit var takeAdapter: TakeAdapter
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTransactionTakeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerview?.setHasFixedSize(true)
        auth = Firebase.auth
        showRecyclerList()
        auth.currentUser?.email?.let {
            transactionTakeViewModel.transaction(it).observe(viewLifecycleOwner){ it1 ->
                if (it1 != null) {
                    when (it1) {
                        is Resource.Loading -> {

                            binding?.recyclerview?.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            binding?.recyclerview?.visibility = View.VISIBLE

                            it1.data?.let { it2 -> takeAdapter.setData(it2) }

                        }
                        is Resource.Error -> {
                            binding?.recyclerview?.visibility = View.VISIBLE
                            Toast.makeText(activity,it1.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    private fun showRecyclerList() {
        binding?.recyclerview?.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        takeAdapter = TakeAdapter()
        binding?.recyclerview?.adapter = takeAdapter
        takeAdapter.onItemClick = {
            showSelectedUser(it)
        }
        takeAdapter.onButtonRatingClick = {
            val alert = RatingDialog()
            alert.showDialog(activity,"Berikan ulasan dan rating anda",it)
        }
        takeAdapter.onButtonAmbilClick = {
            transactionTakeViewModel.updateTransactionStatus(it.id,"Akan diambil").observe(viewLifecycleOwner){ it1 ->
                if (it1 != null) {
                    when (it1) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            Toast.makeText(activity, "sedang diambil", Toast.LENGTH_SHORT).show()
                            Intent(
                                activity,
                                MainActivity::class.java
                            ).also { intent ->
                                startActivity(intent)
                            }
                            activity?.overridePendingTransition(0, 0)

                        }
                        is Resource.Error -> {

                        }
                    }

                }
            }
        }
    }
    private fun showSelectedUser(hero: Take) {
        Toast.makeText(activity, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}