package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.FitstMenuLayoutBinding

class MenuFirstActivity : AppCompatActivity() {
    lateinit var binding: FitstMenuLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.arrowbutton2.setOnClickListener {
            val intent = Intent(this, MenuProfileActivity::class.java)
            startActivity(intent)
        }

        binding.arrowbutton3.setOnClickListener {
            val intent = Intent(this, MenuCertifyActivity::class.java)
            startActivity(intent)
        }

        binding.arrowbutton5.setOnClickListener {
            val intent = Intent(this, MenuSecessionActivity::class.java)
            startActivity(intent)
        }
        //이용약관
        binding.arrowbutton1.setOnClickListener {
            val intent = Intent(this, MenuSerciveActivity::class.java)
            startActivity(intent)
        }

        //매물관리
        binding.arrowbutton4.setOnClickListener {
            val intent = Intent(this, MenuManagementActivity::class.java)
            startActivity(intent)
        }

        binding.imageView10.setOnClickListener {
            finish()
        }

    }
}