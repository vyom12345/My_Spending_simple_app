package com.project.myspending.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.myspending.R
import com.project.myspending.Spending
import com.project.myspending.databinding.FragmentHomeBinding
import com.project.myspending.ui.add.AddSpendingFragmentSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**Main Destination Fragment for daily spending dashboard**/
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized)
            binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::adapter.isInitialized) {
            initView()
        }
    }

    private fun initView() {
        /**setup toolbar for date selection**/
        recyclerView = binding.viewSpending.spendingRecyclerView
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager

        adapter = HomeAdapter { spending ->
            onAdapterItemClick(spending)
        }
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        recyclerView.adapter = adapter
        /**setup bottomNavigation item click**/
        binding.bottomNavigation.setOnItemSelectedListener { it ->
            if (findNavController().currentDestination?.id != R.id.homeFragment) {
                return@setOnItemSelectedListener false
            }
            when (it.itemId) {
                R.id.add -> {
                    AddSpendingFragmentSheet(null, adapter) {
                        updateTotalAmountAndItem(it)
                    }.show(
                        childFragmentManager,
                        "AddSpendingFragmentSheet"
                    )
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    /**Set Adapter Item Click
     * we open bottomSheet so user can edit the fields and update the existing record
     * **/
    private fun onAdapterItemClick(spending: Spending) {
        AddSpendingFragmentSheet(spending, adapter) {
            updateTotalAmountAndItem(it)
        }.show(
            childFragmentManager,
            "AddSpendingFragmentSheet"
        )
    }

    /**Count the total amount and total item and update the view**/
    private fun updateTotalAmountAndItem(list: List<Spending>) {
        CoroutineScope(Dispatchers.Default).launch {
            val amount = list.sumOf { it.amount }.toString()
            val totalItem = list.size.toString()
            withContext(Dispatchers.Main) {
                binding.viewSpending.totalAmount.text =
                    requireContext().getString(R.string.rupee_symbol, amount)
                binding.viewSpending.totalItem.text = totalItem
            }
        }
    }

}
