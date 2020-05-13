package com.tvacstudio.motionlayout.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tvacstudio.motionlayout.R
import com.tvacstudio.motionlayout.api.response.Person
import com.tvacstudio.motionlayout.extensions.loadUrl
import kotlinx.android.synthetic.main.item_group.view.*

class MembersAdapter(private val members: ArrayList<Person>) :
    RecyclerView.Adapter<MembersAdapter.MembersViewHolder>() {

    init {
        // ToDo [მოსაფიქრებელია] დალოგნებული მომხმარებლის გრიდში ჩვენება
    }

    class MembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        return MembersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {

        val member = members[position]

        holder.itemView.textView4.text = member.name ?: ""

        if (!TextUtils.isEmpty(member.imageUrl)) {
            holder.itemView.profilePic.loadUrl(member.imageUrl!!)
        }

        // ToDo  textView5 მეთოდია გასაკეთებელი HTML - ით საათის ტექსტისთვის.

    }

    fun updateUserList(members: List<Person>) {
        this.members.clear()
        this.members.addAll(members)
        notifyDataSetChanged()
    }

}