package id.ac.unhas.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var idNama: EditText
    private lateinit var idEmail: EditText
    private lateinit var tmblAdd: Button
    private lateinit var tmblView: Button
    private lateinit var tmblUpdate : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        tmblAdd.setOnClickListener{ tambahStudent() }
        tmblView.setOnClickListener{ viewStudent() }
        tmblUpdate.setOnClickListener{ updateStudent() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.nama, Toast.LENGTH_SHORT).show()

            idNama.setText(it.nama)
            idEmail.setText(it.email)
            std = it
        }

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }
    }

    private fun viewStudent() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("Tess", "${stdList.size}")

        adapter?.addItem(stdList)
    }

    private fun tambahStudent() {
        val nama = idNama.text.toString()
        val email = idEmail.text.toString()

        if (nama.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Harap Mengisi kotak yang kosong", Toast.LENGTH_SHORT).show()
        }else{
            val std = StudentModel(nama = nama, email = email)
            val status = sqLiteHelper.insertStudent(std)

            //Check insert success or not
            if(status > -1){
                Toast.makeText(this,"Student ditambah...", Toast.LENGTH_SHORT).show()
                clearEditText()
                tambahStudent()
            }else{
                Toast.makeText(this, "Gagal menambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStudent(){
        val nama = idNama.text.toString()
        val email = idEmail.text.toString()

        if(nama == std?.nama && email == std?.email){
            Toast.makeText(this, "Data sudah Diupdate..", Toast.LENGTH_SHORT).show()
            return
        }

        if(std == null) return

        val std = StudentModel(id = std!!.id, nama = nama, email = email)
        val status = sqLiteHelper.updateStudent(std)
        if(status > -1){
            clearEditText()
            viewStudent()
        }
    }

    private fun deleteStudent(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Anda yakin ingin menghapusnya")
        builder.setCancelable(true)
        builder.setPositiveButton("ya"){ dialog, _ ->
            sqLiteHelper.deleteStudentById(id)
            viewStudent()
            dialog.dismiss()
        }
        builder.setNegativeButton("tidak"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText() {
        idNama.setText("")
        idEmail.setText("")
        idNama.requestFocus()
    }

    private  fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager (this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        idNama = findViewById(R.id.idNama)
        idEmail = findViewById(R.id.idEmail)
        tmblAdd = findViewById(R.id.tmblAdd)
        tmblView = findViewById(R.id.tmblView)
        tmblUpdate = findViewById(R.id.tmblUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}