package com.example.expensetracker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ListItemTransactionBinding
import com.example.expensetracker.model.TransactionModel
import com.example.expensetracker.util.currencyFormat
import kotlinx.coroutines.flow.MutableStateFlow

class TransactionItemAdapter:
    RecyclerView.Adapter<TransactionItemAdapter.TransactionViewHolder>(){

    inner class TransactionViewHolder(val binding: ListItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var _currency = MutableStateFlow("USD")
    var currency = _currency.value


    private val differCallback = object : DiffUtil.ItemCallback<TransactionModel>() {
        override fun areItemsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ListItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.binding.apply {
            tvTransactionTitle.text = item.title
            tvTransactionDescription.text = holder.itemView.context.getText(item.category.description)
            tvTransactionAmount.text = item.amount.toString()
            ivTransactionIcon.setImageResource(item.category.icon)

            when (item.transactionType) {
                "Income" -> {
                    tvTransactionAmount.setTextColor(
                        ContextCompat.getColor(
                            tvTransactionAmount.context,
                            R.color.holo_green_light
                        )
                    )
                    tvTransactionAmount.text = "+"  + currencyFormat(item.amount, currency)
                }
                "Expense" -> {
                    tvTransactionAmount.setTextColor(
                        ContextCompat.getColor(tvTransactionAmount.context, R.color.holo_red_light)
                    )
                    tvTransactionAmount.text = "- " + currencyFormat(item.amount, currency)
                }
            }

        }

        holder.itemView.setOnClickListener { onItemClickListener?.let { it(item) } }
    }

    private var onItemClickListener: ((TransactionModel) -> Unit)? = null
    fun setOnItemClickListener(listener: (TransactionModel) -> Unit) {
        onItemClickListener = listener
    }
}