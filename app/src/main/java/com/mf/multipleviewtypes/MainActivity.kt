package com.mf.multipleviewtypes

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {

    localizedContext(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val rclNews = findViewById<RecyclerView>(R.id.rclNews)
    val adapter = NewsAdapter(createRecyclerViewItems().toMutableList())
    rclNews.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
    rclNews.adapter = adapter

    InfiniteScrollProvider().attach(rclNews) {
      adapter.setMustShowProgressBar(true)
      Handler().postDelayed({
        adapter.addItems(
          listOf(
            TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_1)!!, getString(R.string.text)),
            TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_2)!!, getString(R.string.text)),
            TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_3)!!, getString(R.string.text)),
            TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_4)!!, getString(R.string.text)),
            TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_5)!!, getString(R.string.text))
          )
        )
        adapter.setMustShowProgressBar(false)
      }, 2000)
      Toast.makeText(this, "End Of List", Toast.LENGTH_SHORT).show()
    }

  }

  // Create a list of Models
  private fun createRecyclerViewItems(): List<Model> {

    return mutableListOf<Model>().apply {

      // Type C
      add(TypeCModel(listOf(Color.BLACK, Color.BLUE, Color.DKGRAY, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.LTGRAY)))

      // Type A
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_1)!!, getString(R.string.text)))
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_2)!!, getString(R.string.text)))
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_4)!!, getString(R.string.text)))

      // Type B
      add(TypeBModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_3)!!))

      // Type A
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_1)!!, getString(R.string.text)))
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_3)!!, getString(R.string.text)))

      // Type B
      add(TypeBModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_5)!!))

      // Type A
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_2)!!, getString(R.string.text)))
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_1)!!, getString(R.string.text)))
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_1)!!, getString(R.string.text)))
      add(TypeAModel(ContextCompat.getDrawable(this@MainActivity, R.drawable.image_3)!!, getString(R.string.text)))
    }

  }

  fun localizedContext(baseContext: Context, locale: Locale = Locale("fa")): Context {
    Locale.setDefault(locale)
    val configuration = baseContext.resources.configuration
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    return baseContext.createConfigurationContext(configuration)
  }

}