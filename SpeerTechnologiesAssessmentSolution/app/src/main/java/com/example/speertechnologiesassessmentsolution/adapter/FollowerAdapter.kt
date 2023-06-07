package com.example.speertechnologiesassessmentsolution.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.speertechnologiesassessmentsolution.R
import com.example.speertechnologiesassessmentsolution.domain.model.UserModel

import com.example.speertechnologiesassessmentsolution.presentation.fragments.ListFragmentDirections
import com.example.speertechnologiesassessmentsolution.utils.LoadImageBindingAdapter
import de.hdodenhof.circleimageview.CircleImageView

class FollowerAdapter(
    var data: ArrayList<UserModel>,
    var findNavController: NavController
) :
    RecyclerView.Adapter<FollowerAdapter.MyViewHolder?>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_follower, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val model = data[position]

        LoadImageBindingAdapter.loadImage(holder.ivImage, model.avatar_url)
        holder.tvName.text = model.login




        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToProfileFragment(model.login)
            findNavController.navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // init the item view's
        var ivImage: CircleImageView = itemView.findViewById(R.id.ivImage)
        var tvName: TextView = itemView.findViewById(R.id.tvName)

    }


}