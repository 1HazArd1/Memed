package com.example.memed

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentImageURL:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_next.setOnClickListener{
            memeCall()
            btn_next.isSelected != btn_next.isSelected
        }
        btn_share.setOnClickListener{
            val intent= Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"Hey check this cool meme $currentImageURL")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share"))
            btn_share.isSelected != btn_share.isSelected
        }
    }
    private fun memeCall(){
        progress_bar.visibility= View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageURL=response.getString("url") //url of the image
                Glide.with(this).load(currentImageURL).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility=View.GONE
                        return false
                    }
                }).into(img_meme)
            },
            { error ->
                Log.e("LoadError",error.toString())
                Toast.makeText(this,"Something went Wrong",Toast.LENGTH_SHORT).show()
            }
        )
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}