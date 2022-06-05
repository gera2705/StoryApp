package com.example.pinapplication

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import jp.shts.android.storiesprogressview.StoriesProgressView

class StoriesActivity : AppCompatActivity(), StoriesProgressView.StoriesListener {

    lateinit var storiesProgressView: StoriesProgressView
    lateinit var image: ImageView
    lateinit var name: TextView
    lateinit var date: TextView
    lateinit var type: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_stories)

        initStoriesProgressView()
        initView()

        image.setImageResource(stories[counter].imageId)
        name.text = stories[counter].name
        date.text = stories[counter].date
        type.text = stories[counter].type

        val reverse: View = findViewById(R.id.reverse)

        reverse.setOnClickListener {
            storiesProgressView.reverse()
        }

        reverse.setOnTouchListener(object : SwipeListener(this) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                storiesProgressView.reverse()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                storiesProgressView.skip()
            }

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                super.onTouch(v, event)
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        pressTime = System.currentTimeMillis()
                        storiesProgressView.pause()
                        return false
                    }
                    MotionEvent.ACTION_UP -> {

                        val now = System.currentTimeMillis()
                        storiesProgressView.resume()
                        return limit < now - pressTime
                    }
                }
                return false
            }

        })

        val skip: View = findViewById(R.id.skip)
        skip.setOnTouchListener(object : SwipeListener(this) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                storiesProgressView.reverse()
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                storiesProgressView.skip()
            }

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                super.onTouch(v, event)
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        pressTime = System.currentTimeMillis()
                        storiesProgressView.pause()
                        return false
                    }
                    MotionEvent.ACTION_UP -> {

                        val now = System.currentTimeMillis()
                        storiesProgressView.resume()
                        return limit < now - pressTime
                    }
                }
                return false
            }

        })
        skip.setOnClickListener {
            storiesProgressView.skip()
        }
    }

    private fun initView() {
        image = findViewById<View>(R.id.image) as ImageView
        name = findViewById(R.id.name)
        date = findViewById(R.id.date)
        type = findViewById(R.id.type)
    }

    private fun initStoriesProgressView() {
        storiesProgressView = findViewById<View>(R.id.stories) as StoriesProgressView
        storiesProgressView.setStoriesCount(stories.size)
        storiesProgressView.setStoryDuration(3000L)
        storiesProgressView.setStoriesListener(this)
        storiesProgressView.startStories(counter)
    }

    override fun onNext() {
        counter++
        image.setImageResource(stories[counter].imageId)
        name.text = stories[counter].name
        date.text = stories[counter].date
    }

    override fun onPrev() {
        if(counter != 0){
            counter--
        }

        if (counter >= 0){
            name.text = stories[counter].name
            date.text = stories[counter].date
            image.setImageResource(stories[counter].imageId)
        }
    }

    override fun onComplete() {
        counter = 0
        val i = Intent(this@StoriesActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onDestroy() {
        storiesProgressView.destroy()
        super.onDestroy()
    }

    private companion object {
        val stories: Array<Story> = arrayOf(
            Story(
                R.drawable.first_story,
                "Хьюстон",
                "9-22 сентября",
                "Путешествия"
            ),
            Story(
                R.drawable.second_story,
                "Таллахасси",
                "11-22 марта",
                "Путешествия"
            ),
            Story(
                R.drawable.third_story,
                "Ханхай",
                "1-10 ноября",
                "Путешествия"
            ),
            Story(
                R.drawable.four_story,
                "Нью-Йорк",
                "9-12 июля",
                "Путешествия"
            ),
            Story(
                R.drawable.fifht_story,
                "Москва",
                "10-27 декабря",
                "Путешествия"
            ),
        )
        var pressTime = 0L
        const val limit = 500L
        var counter = 0
    }
}