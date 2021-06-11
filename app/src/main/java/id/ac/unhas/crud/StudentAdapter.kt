package id.ac.unhas.crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem: ((StudentModel)->Unit )? = null
    private var onClickDeleteItem: ((StudentModel)->Unit )? = null

    fun addItem(item: ArrayList<StudentModel>){
        this.stdList = item
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback:(StudentModel)-> Unit  ){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback:(StudentModel)-> Unit  ){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent, false)
    )

    override fun getItemCount(): Int {
        return stdList.size
    }

    override fun onBindViewHolder(holder: StudentAdapter.StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
        holder.tmblDel.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }
    class StudentViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var nama = view.findViewById<TextView>(R.id.tvNama)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var tmblDel = view.findViewById<TextView>(R.id.tmblDel)

        fun bindView(std: StudentModel){
            id.text = std.id.toString()
            nama.text = std.nama
            email.text = std.email
        }
    }
}