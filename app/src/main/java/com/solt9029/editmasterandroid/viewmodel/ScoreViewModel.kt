package com.solt9029.editmasterandroid.viewmodel

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.ceryle.radiorealbutton.RadioRealButtonGroup
import com.google.gson.Gson
import com.mlykotom.valifi.fields.ValiFieldText
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.solt9029.editmasterandroid.R
import com.solt9029.editmasterandroid.constants.IdConstants
import com.solt9029.editmasterandroid.constants.IdConstants.Note.COPY
import com.solt9029.editmasterandroid.constants.IdConstants.Note.PASTE
import com.solt9029.editmasterandroid.constants.IdConstants.Note.SPACE
import com.solt9029.editmasterandroid.constants.IdConstants.State.FRESH
import com.solt9029.editmasterandroid.constants.NumberConstants
import com.solt9029.editmasterandroid.constants.SecondConstants
import com.solt9029.editmasterandroid.entity.Score
import com.solt9029.editmasterandroid.entity.ValidationErrorBody
import com.solt9029.editmasterandroid.repository.ScoreRepository
import com.solt9029.editmasterandroid.util.CalcUtil
import com.solt9029.editmasterandroid.util.NoteUtil
import com.solt9029.editmasterandroid.view.customview.ScrollContainerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ScoreViewModel @Inject constructor(
        private val repository: ScoreRepository, var context: Context
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    var navigateToScoreSettingsFragment = UnitLiveEvent()
    var openDialog = UnitLiveEvent()
    var openCopyToast = UnitLiveEvent()
    var dialogType = MutableLiveData<IdConstants.DialogType>(IdConstants.DialogType.LOADING)
    var validationErrorBody = MutableLiveData<ValidationErrorBody>()
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
    private var clipboard: List<Int>? = null
    var translateYPx = MutableLiveData(0)
    var currentTime = MutableLiveData(0f)
    var currentNote = MutableLiveData(IdConstants.Note.DON)
    var currentDivision = MutableLiveData(NumberConstants.DIVISIONS[0])
    private var gestureListener = object : GestureListener() {
        override fun onSingleTapUp(event: MotionEvent?): Boolean {
            Timber.d("onSingleTapUp")

            if (event == null || currentDivision.value == null || currentNote.value == null || translateYPx.value == null || notes.value == null || states.value == null) {
                return false
            }

            val pointer = CalcUtil.calcPointer(
                    event.x, event.y + translateYPx.value!!, widthPx, currentDivision.value!!, context)
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

            // copy
            if (currentNote.value!! == COPY) {
                val barIndex = CalcUtil.calcBarIndex(index)
                clipboard = notes.value?.slice(
                        barIndex * NumberConstants.NOTES_PER_BAR until (barIndex + 1) * NumberConstants.NOTES_PER_BAR)
                openCopyToast.call()
                return true
            }

            // paste
            if (currentNote.value!! == PASTE) {
                clipboard?.let {
                    val barIndex = CalcUtil.calcBarIndex(index)
                    for (i in 0 until NumberConstants.NOTES_PER_BAR) {
                        notes.value!![barIndex * NumberConstants.NOTES_PER_BAR + i] = it[i]
                    }
                    notes.value = notes.value
                    return true
                }
                return false
            }

            // when last line is tapped, add line after the bottom line
            if (notes.value!!.size > index + count && notes.value!!.size - NumberConstants.NOTES_PER_BAR <= index + count) {
                notes.value?.addAll(Collections.nCopies(NumberConstants.NOTES_PER_BAR, SPACE))
                states.value?.addAll(Collections.nCopies(NumberConstants.NOTES_PER_BAR, FRESH))
            }

            for (i in index..index + count) {
                notes.value!![i] = currentNote.value!!
            }
            notes.value = notes.value

            return true
        }
    }
    private val gestureDetector = GestureDetector(context, gestureListener)

    val onCurrentDivisionChange = RadioRealButtonGroup.OnPositionChangedListener { _, currentPosition, _ ->
        Timber.d("currentDivision $currentPosition (index) has been selected")
        currentDivision.value = NumberConstants.DIVISIONS[currentPosition]
    }

    val onCurrentNoteChange =
            RadioRealButtonGroup.OnPositionChangedListener { _, currentPosition, _ ->
                Timber.d("currentNote $currentPosition (index) has been selected")
                currentNote.value = IdConstants.Note.RADIO_NOTE_MAPPING[currentPosition]
            }

    val onScrollChange: ScrollContainerView.OnScrollChangeListener =
            object : ScrollContainerView.OnScrollChangeListener {
                override fun onScrollChange(x: Int, y: Int, oldX: Int, oldY: Int) {
                    translateYPx.value = y
                }
            }

    val onTouch = View.OnTouchListener { v: View?, event: MotionEvent? ->
        v?.performClick()
        gestureListener.widthPx = v?.width ?: 0
        gestureDetector.onTouchEvent(event)
        false
    }

    fun createScore() {
        validationErrorBody.value = null
        dialogType.value = IdConstants.DialogType.LOADING
        openDialog.call()
        val score = Score(bpm = bpm.value?.toFloatOrNull(), speed = speed.value?.toFloatOrNull(),
                offset = offset.value?.toFloatOrNull(), comment = comment.value, username = username.value,
                videoId = videoId.value?.value, notes = notes.value)
        val disposable = createScore(score).subscribe(
                {
                    if (it.isSuccessful) {
                        validationErrorBody.value = null
                        dialogType.value = IdConstants.DialogType.SUCCESS
                        return@subscribe
                    }
                    when (it.code()) {
                        422 -> {
                            validationErrorBody.value = Gson().fromJson<ValidationErrorBody>(it.errorBody()?.string(),
                                    ValidationErrorBody::class.java)
                            dialogType.value = IdConstants.DialogType.VALIDATION_ERROR
                        }
                    }
                },
                {
                    validationErrorBody.value = null
                    dialogType.value = IdConstants.DialogType.FAILURE
                    Timber.d(it.message)
                })
        compositeDisposable.add(disposable)
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
                currentTime.postValue(currentYouTubeSecond - NumberConstants.AUDIO_LAG)
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

    private val soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
    private val donSoundId = soundPool.load(context, R.raw.don, 1)
    private val kaSoundId = soundPool.load(context, R.raw.ka, 1)
    private fun playSound(soundId: Int) {
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }

    fun doAutoMode() {
        if (currentTime.value == null || bpm.value == null || offset.value == null || notes.value == null || states.value == null) {
            return
        }

        val range = CalcUtil.calcNoteIndexRangeInSecondRange(SecondConstants.RANGE_AUTO, currentTime.value!!,
                bpm.value!!.toFloatOrNull() ?: 0f, offset.value!!.toFloatOrNull() ?: 0f)

        for (i in range.first..range.last) {
            if (i < 0 || i >= notes.value!!.size) {
                continue
            }

            val note = notes.value!![i]
            val state = states.value!![i]

            if (note == SPACE || state != FRESH) {
                continue
            }

            if (!NoteUtil.hasState(note)) {
                playSound(donSoundId)
                return
            }

            if (NoteUtil.isDon(note)) {
                playSound(donSoundId)
            } else if (NoteUtil.isKa(note)) {
                playSound(kaSoundId)
            }

            states.value!![i] = IdConstants.State.GOOD
            states.postValue(states.value)
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
            Timber.d("YouTubePlayer: onStateChange")
            if (state == PlayerConstants.PlayerState.PLAYING) {
                states.value = ArrayList(Collections.nCopies(notes.value?.size ?: 0, FRESH))
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
                {
                    if (!it.isSuccessful) {
                        it.errorBody()?.let { errorBody ->
                            Timber.d(errorBody.string())
                        }
                        return@subscribe
                    }
                    val score = it.body()
                    this.username.value = score?.username
                    this.comment.value = score?.comment
                    this.videoId.value = Field(score?.videoId)
                    player?.loadVideo(score?.videoId ?: "", currentTime.value ?: 0f)
                    this.bpm.value = score?.bpm?.toString()
                    this.offset.value = score?.offset?.toString()
                    this.speed.value = score?.speed?.toString()
                    this.notes.value = score?.notes
                    states.value = ArrayList(Collections.nCopies(score?.notes?.size ?: 0, FRESH))
                },
                {
                    Timber.e(it.message)
                }
        )
        compositeDisposable.add(disposable)
    }

    private fun fetchScore(id: Int): Single<Response<Score>> {
        return repository.getScore(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun createScore(score: Score): Single<Response<Score>> {
        return repository.createScore(score)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun navigateToScoreSettingsFragment() {
        navigateToScoreSettingsFragment.call()
    }
}