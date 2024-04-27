package com.example.emergency.views.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emergency.R
import com.example.emergency.respository.models.PojoCartItems

class CartAdapter(mContext: Activity) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var  count = 0
    private val mCartData = mutableListOf<PojoCartItems>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_food_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Set data
        holder.productName.text = mCartData[position].name
        holder.imageView.setImageResource(mCartData[position].image)
        holder.price.text = mCartData[position].price

        // Increment button
        holder.incBtn.setOnClickListener {
            if(count<=9){
                val data = count++
                holder.qty.text = data.toString()

            }
        }

        // Decrement button
        holder.decBtn.setOnClickListener {
            if(count>=0){
                val data = count--
                holder.qty.text = data.toString()
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return mCartData.size
    }

    fun updatedata(list: List<PojoCartItems>) {
        mCartData.clear()
        mCartData.addAll(list)
        notifyDataSetChanged()

    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivProductImage)
        val productName: TextView = itemView.findViewById(R.id.tvProductName)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val incBtn: TextView = itemView.findViewById(R.id.tvIncementBtn)
        val decBtn: TextView = itemView.findViewById(R.id.tvDecrementBtn)
        val qty: TextView = itemView.findViewById(R.id.tvQuantity)
    }
}
