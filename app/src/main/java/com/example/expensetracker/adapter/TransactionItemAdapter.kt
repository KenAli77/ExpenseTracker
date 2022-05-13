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

class TransactionItemAdapter() :
    RecyclerView.Adapter<TransactionItemAdapter.TransactionViewHolder>(){

    inner class TransactionViewHolder(val binding: ListItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var _currency = MutableStateFlow<String>("USD")
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
            tvTransactionDescription.text = when(item.category){

                    "bills" -> holder.itemView.context.getString(R.string.bills)
                    "food" -> holder.itemView.context.getString(R.string.food)
                    "education" -> holder.itemView.context.getString(R.string.education)
                    "entertainment" -> holder.itemView.context.getString(R.string.entertainment)
                    "housing" -> holder.itemView.context.getString(R.string.housing)
                    "health" -> holder.itemView.context.getString(R.string.health)
                    "travel" -> holder.itemView.context.getString(R.string.travel)
                    "transportation" -> holder.itemView.context.getString(R.string.transportation)
                    "shopping" -> holder.itemView.context.getString(R.string.shopping)
                    "salary" -> holder.itemView.context.getString(R.string.salary)
                    "investments" -> holder.itemView.context.getString(R.string.investments)
                    "other" -> holder.itemView.context.getString(R.string.other)
                    else -> holder.itemView.context.getString(R.string.other)

            }
            tvTransactionAmount.text = item.amount.toString()

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

            when (item.category) {
                "Bills" -> ivTransactionIcon.setImageResource(R.drawable.ic_utility)
                "Food" -> ivTransactionIcon.setImageResource(R.drawable.ic_food)
                "Education" -> ivTransactionIcon.setImageResource(R.drawable.ic_education)
                "Entertainment" -> ivTransactionIcon.setImageResource(R.drawable.ic_entertainment)
                "Housing" -> ivTransactionIcon.setImageResource(R.drawable.ic_housing)
                "Health" -> ivTransactionIcon.setImageResource(R.drawable.ic_health)
                "Travel" -> ivTransactionIcon.setImageResource(R.drawable.ic_travel)
                "Transportation" -> ivTransactionIcon.setImageResource(R.drawable.ic_transportation)
                "Shopping" -> ivTransactionIcon.setImageResource(R.drawable.ic_shopping)
                "Other" -> ivTransactionIcon.setImageResource(R.drawable.ic_other)
                "Salary" -> ivTransactionIcon.setImageResource(R.drawable.ic_salary)
                "Investment" -> ivTransactionIcon.setImageResource(R.drawable.ic_investments)
                else -> ivTransactionIcon.setImageResource(R.drawable.ic_other)
            }
        }

        holder.itemView.setOnClickListener { onItemClickListener?.let { it(item) } }
    }

    private var onItemClickListener: ((TransactionModel) -> Unit)? = null
    fun setOnItemClickListener(listener: (TransactionModel) -> Unit) {
        onItemClickListener = listener
    }
}