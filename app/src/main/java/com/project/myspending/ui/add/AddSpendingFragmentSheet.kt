package com.project.myspending.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.project.myspending.R
import com.project.myspending.Spending
import com.project.myspending.databinding.FragmentAddSpendingBottomSheetBinding
import com.project.myspending.ui.home.HomeAdapter
import com.project.myspending.util.DateUtil
import java.util.UUID


class AddSpendingFragmentSheet(
    private val spending: Spending?,
    private val homeAdapter: HomeAdapter,
    private val onChange: (List<Spending>) -> Unit
) :
    BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddSpendingBottomSheetBinding
    private lateinit var ed1: TextInputEditText
    private lateinit var ed2: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSpendingBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        /**Setup Date Selection Toolbar**/
        ed1 = binding.purposeEditText
        ed2 = binding.amountEditText
        /**Check if argument is not null then user is here for update the record
         * set text to editTexts & Visible The Delete Button
         * **/
        ed1.setText(spending?.purpose ?: "")
        ed2.setText(spending?.amount?.toString() ?: "")
        binding.delete.visibility = View.VISIBLE
        binding.delete.setOnClickListener {
            /**Delete The Entry From The Database**/
            val newList = ArrayList<Spending>()
            homeAdapter.currentList.forEach {
                if (it.id != spending!!.id)
                    newList.add(it.copy())
            }
            homeAdapter.submitList(newList) {
                onChange(homeAdapter.currentList)
            }
            dismiss()
        }
        /**If User press actionDone on keyboard we saved the record**/
        binding.amountEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveUpdate()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.save.setOnClickListener {
            saveUpdate()
        }

        binding.close.setOnClickListener {
            dismiss()
        }
    }

    private fun saveUpdate() {
        /**Validate Entries**/
        if (ed1.text.isNullOrEmpty()) {
            ed1.error = getString(R.string.enter_purpose)
            return
        }
        if (ed2.text.isNullOrEmpty()) {
            ed2.error = getString(R.string.enter_amount)
            return
        }
        /**If Argument is null then save the new record
         * Or Update the existing record
         * **/
        if (spending == null) {
            val newList = ArrayList<Spending>()
            homeAdapter.currentList.forEach {
                newList.add(it.copy())
            }
            newList.add(
                Spending(
                    id = UUID.randomUUID().toString(),
                    purpose = ed1.text.toString(),
                    amount = ed2.text.toString().toLong(),
                    date = DateUtil.getCurrentDate()
                )
            )
            homeAdapter.submitList(newList) {
                onChange(homeAdapter.currentList)
            }
        } else {
            val newList = ArrayList<Spending>()
            homeAdapter.currentList.forEach {
                if (it.id == spending.id) {
                    newList.add(
                        it.copy().copy(
                            purpose = ed1.text.toString(),
                            amount = ed2.text.toString().toLong()
                        )
                    )
                } else
                    newList.add(it.copy())
            }
            homeAdapter.submitList(newList) {
                onChange(homeAdapter.currentList)
            }
        }
        dismiss()
    }
}
