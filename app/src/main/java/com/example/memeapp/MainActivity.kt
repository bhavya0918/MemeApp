package com.example.memeapp
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {

    val currentImageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun load() {

        val progressBar=findViewById<ProgressBar>(R.id.progress)
        progressBar.visibility= View.VISIBLE
        val currentImageurl="https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,currentImageurl,null,
            {
                    response ->
                val url=response.getString("url")
                val img=findViewById<ImageView>(R.id.img)
                Glide.with(this).load(url).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility= View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility= View.GONE
                        return false
                    }

                }).into(img)

            },
            {
                Toast.makeText(this,"error", Toast.LENGTH_LONG).show()
            })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun sharememe(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check this my first app $currentImageurl")
        val chooser= Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)

    }
    fun nextmeme(view: View) {
        load()
    }


}