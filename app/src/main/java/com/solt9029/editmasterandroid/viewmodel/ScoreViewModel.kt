package com.solt9029.editmasterandroid.viewmodel

import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.ceryle.radiorealbutton.RadioRealButton
import co.ceryle.radiorealbutton.RadioRealButtonGroup
import com.mlykotom.valifi.fields.ValiFieldText
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.IdConstants
import com.solt9029.editmasterandroid.constants.IdConstants.Note.SPACE
import com.solt9029.editmasterandroid.constants.IdConstants.State.FRESH
import com.solt9029.editmasterandroid.constants.NumberConstants
import com.solt9029.editmasterandroid.constants.SecondConstants
import com.solt9029.editmasterandroid.repository.ScoreRepository
import com.solt9029.editmasterandroid.response.Score
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.util.NoteUtil
import com.solt9029.editmasterandroid.util.Pointer
import com.solt9029.editmasterandroid.view.customview.ScrollContainerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ScoreViewModel @Inject constructor(
        private val repository: ScoreRepository, var context: Context
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    var navigateToScoreSettingsFragment = UnitLiveEvent()
    var username = ValiFieldText("通りすがりの創作の達人")
    var videoId = MutableLiveData(Field("jhOVibLEDhA"))
    var bpm = ValiFieldFloat(158f)
    var offset = ValiFieldFloat(0.75f)
    var speed = ValiFieldFloat(1f)
    var comment = ValiFieldText("創作の達人で創作譜面をしました！")
    var notes = MutableLiveData(mutableListOf(
            SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE,
            SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE,
            SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE,
            SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE,
            SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE,
            SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE, SPACE,
            SPACE, SPACE, SPACE, SPACE, SPACE, SPACE))
    var states = MutableLiveData(mutableListOf(
            FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH,
            FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH,
            FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH,
            FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH,
            FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH,
            FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH, FRESH,
            FRESH, FRESH, FRESH, FRESH, FRESH, FRESH))
    var translateYPx = MutableLiveData(0)
    var currentTime = MutableLiveData(0f)
    var currentNote = MutableLiveData(IdConstants.Note.DON)
    var currentDivision = MutableLiveData(NumberConstants.DIVISIONS[0])

    val onCurrentDivisionChange =
            object : RadioRealButtonGroup.OnPositionChangedListener {
                override fun onPositionChanged(button: RadioRealButton?, currentPosition: Int, lastPosition: Int) {
                    Timber.d("currentDivision $currentPosition (index) has been selected")
                    currentDivision.value = NumberConstants.DIVISIONS[currentPosition]
                }
            }

    val onCurrentNoteChange = object : RadioRealButtonGroup.OnPositionChangedListener {
        override fun onPositionChanged(button: RadioRealButton?, currentPosition: Int, lastPosition: Int) {
            Timber.d("currentNote $currentPosition (index) has been selected")
            currentNote.value = IdConstants.Note.RADIO_NOTE_MAPPING[currentPosition]
        }
    }

    val onScrollChange: ScrollContainerView.OnScrollChangeListener =
            object : ScrollContainerView.OnScrollChangeListener {
                override fun onScrollChange(x: Int, y: Int, oldX: Int, oldY: Int) {
                    translateYPx.value = y
                }
            }

    val onTouch = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            v?.performClick()

            // TODO: recognize the event is tap or swipe or ... and only tap event passes

            if (event == null || v == null || currentDivision.value == null || currentNote.value == null || translateYPx.value == null) {
                return false
            }

            val pointer: Pointer =
                    CalcUtil.calcPointer(event.x, event.y + translateYPx.value!!, v.width, currentDivision.value!!,
                            context)
            val notesPerDivision = NumberConstants.NOTES_PER_BAR / currentDivision.value!!
            val notesPerBarIndex = pointer.divisionIndex * notesPerDivision
            val index = pointer.barIndex * NumberConstants.NOTES_PER_BAR + notesPerBarIndex

            var count = 0
            if (!NoteUtil.hasState(currentNote.value!!)) {
                count = notesPerDivision - 1
            }

            // avoid exception
            if (notes.value!!.size <= index + count) {
                return false
            }


            for (i in index..index + count) {
                notes.value!![i] = currentNote.value!!
            }
            notes.value = notes.value

            return false
        }
    }

    private val viewModel = this
    var thread: Thread? = null
    private var player: YouTubePlayer? = null
    private val tracker = YouTubePlayerTracker()
    private val loop = object : Runnable {
        private var prevTimeMillis: Long = 0L
        private var prevYouTubeSecond = 0f
        private var currentYouTubeSecond: Float = 0f

        override fun run() {
            while (thread != null) {
                if (prevYouTubeSecond != tracker.currentSecond) {
                    prevTimeMillis = System.currentTimeMillis()
                    prevYouTubeSecond = tracker.currentSecond
                    currentYouTubeSecond = tracker.currentSecond
                } else {
                    currentYouTubeSecond = prevYouTubeSecond + (System.currentTimeMillis() - prevTimeMillis) / 1000f
                }
                currentTime.postValue(currentYouTubeSecond)
                Timber.d("currentTime: " + currentTime.value)
                doAutoMode()
                try {
                    Thread.sleep(10)
                } catch (error: InterruptedException) {
                    Timber.e(error.message)
                }

            }
        }
    }

    fun doAutoMode() {
        if (currentTime.value == null || bpm.value == null || offset.value == null || notes.value == null || states.value == null) {
            return
        }

        val range = CalcUtil.calcNoteIndexRangeInSecondRange(SecondConstants.RANGE_AUTO, currentTime.value!!,
                bpm.value!!.toFloat(), offset.value!!.toFloat())

        for (i in range.first..range.last) {
            if (i < 0 || i >= notes.value!!.size) {
                continue
            }

            val note = notes.value!![i]
            val state = states.value!![i]
            if (note == SPACE || state != FRESH) {
                continue
            }

            if (NoteUtil.hasState(note)) {
                states.value!![i] = IdConstants.State.GOOD
                states.postValue(states.value)
            }
        }
    }

    val youTubePlayerListener: AbstractYouTubePlayerListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            super.onReady(youTubePlayer)
            viewModel.player = youTubePlayer
            youTubePlayer.addListener(tracker)
            playVideo()
        }

        override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
            if (state == PlayerConstants.PlayerState.PLAYING) {
                thread = Thread(loop)
                thread?.start()
                return
            }
            thread = null
        }
    }

    fun playVideo() {
        if (tracker.state == PlayerConstants.PlayerState.PLAYING) {
            return
        }
        videoId.value?.value?.let {
            player?.loadVideo(it, currentTime.value ?: 0f)
        }
    }

    fun pauseVideo() {
        player?.pause()
    }

    init {
        val resources = context.resources
        username.addMaxLengthValidator(resources.getString(R.string.max_length_validation_message, 20), 20)
        username.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message))
        bpm.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message))
        offset.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message))
        speed.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message))
        comment.addMaxLengthValidator(resources.getString(R.string.max_length_validation_message, 140), 140)
    }

    fun onVideoIdChange(sequence: CharSequence, start: Int, before: Int, count: Int) {
        val field = Field(sequence.toString())
        if (sequence.isEmpty()) {
            field.error = context.resources.getString(R.string.not_empty_validation_message)
        }
        videoId.value = field
        currentTime.value = 0f
        playVideo()
    }

    public override fun onCleared() {
        compositeDisposable.clear()
        thread = null
    }

    fun initScore(id: Int) {
        // new
        if (id < 1) {
            return
        }

        // show
        val disposable = fetchScore(id).subscribe(
                { (_, username, comment, videoId, bpm, offset, speed, notes) ->
                    this.username.value = username
                    this.comment.value = comment
                    this.videoId.value = Field(videoId)
                    player?.loadVideo(videoId ?: "", currentTime.value ?: 0f)
                    this.bpm.value = bpm?.toString()
                    this.offset.value = offset?.toString()
                    this.speed.value = speed?.toString()
                    this.notes.value = notes
                    states.setValue(ArrayList(Collections.nCopies(notes?.size ?: 0, FRESH)))
                },
                { }
        )
        compositeDisposable.add(disposable)
    }

    private fun fetchScore(id: Int): Single<Score> {
        return repository.getScore(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun navigateToScoreSettingsFragment() {
        navigateToScoreSettingsFragment.call()
    }
}