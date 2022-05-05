package com.kajal.videoplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AddFolder : AppCompatActivity() {
    lateinit var dlt_btn: Button
    lateinit var saveFolder: Button
   // lateinit var ETfolder: EditText
   lateinit var ETName: EditText
    var dbHandler: MyHelper? = null
    var isEditMode: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_folder_dialog)

        saveFolder = findViewById(R.id.save_btn)
        dlt_btn = findViewById(R.id.dlt_btn)
       // ETfolder = findViewById(R.id.ETfolder)
        ETName = findViewById(R.id.Etname)
        Log.d("abc","adn jasd")
        dbHandler = MyHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E") {
            //update data
            isEditMode = true
            saveFolder.text = "Update Data"
            dlt_btn.visibility = View.VISIBLE
            val folders: Folder = dbHandler!!.getFolder(intent.getIntExtra("Id", 0))
            ETName.setText(folders.name)


        } else {

            //insert new data

            isEditMode = false
            saveFolder.text = "Save Data"
            dlt_btn.visibility = View.GONE
        }

        saveFolder.setOnClickListener {
            var success: Boolean = false
            val folders: Folder = Folder()

            if (isEditMode) {
                //update
                folders.id = intent.getIntExtra("Id", 0)
                folders.name = ETName.toString()  //fectching foldrs name

                success = dbHandler?.updateFolder(folders) as Boolean


            } else { //insert
                folders.name = ETName.text.toString()

                success = dbHandler?.addFolder(folders) as Boolean
            }
            if (success) {
                val i = Intent(applicationContext, FolderActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_LONG)
            }
        }

        dlt_btn.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info")
                .setMessage("Click Yes if you want to delete folder")
                .setPositiveButton("YES", { dialog, i ->
                    val success = dbHandler?.deleteFolder(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                })
                .setNegativeButton("No", { dialog, i ->
                    dialog.dismiss()
                })

            dialog.show()
        }


    }
}