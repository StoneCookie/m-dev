package com.example.hw_5

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_5.databinding.PersonItemBinding

interface ActivityFunctions {
    fun cartEvent(e: String)
    fun likeEvent(e: String)
}

class PersonAdapter(private val activityFunctions: ActivityFunctions): RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {
    private var personList = mutableListOf<Person>()

    @SuppressLint("NotifyDataSetChanged")
    fun addPerson(person: Person)
    {
        personList.add(person)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPreviousPersons(prevPersons: MutableList<Person>)
    {
        for (person in prevPersons)
            personList.add(person)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PersonItemBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = personList[position]

        holder.itemView.setOnClickListener{
            activityFunctions.cartEvent(person.title)
        }

        with(holder.binding) {
            like.setOnClickListener {
                activityFunctions.likeEvent(person.title)
            }
            avatar.setImageResource(person.imgId)
            tvTitle.text = person.title
            tvDate.text = person.date
            tvDescription.text = person.description
            tvSex.text = person.sex
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class PersonViewHolder(var binding: PersonItemBinding): RecyclerView.ViewHolder(binding.root)
}