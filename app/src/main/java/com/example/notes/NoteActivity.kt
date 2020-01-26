package com.example.notes

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.extensions.getCurrentTimestamp
import com.example.notes.models.Note
import com.example.notes.repository.NoteRepository
import java.util.*

class NoteActivity :
    AppCompatActivity(),
    View.OnTouchListener,
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener,
    View.OnClickListener,
    TextWatcher{

    // ui components
    private lateinit var linedEditText: LinedEditText
    private lateinit var editTitle: EditText
    private lateinit var viewTitle: TextView
    private lateinit var checkContainer: RelativeLayout
    private lateinit var backArrowContainer: RelativeLayout
    private lateinit var checkBtn: ImageButton
    private lateinit var arrowBtn: ImageButton

    // variables
    private var finalNote: Note = Note()
    private var initialNote: Note = Note()

    private lateinit var gestureDetector: GestureDetector
    private var isNewNote: Boolean = false
    private var mode: Int = 0
    var noteRepository: NoteRepository = NoteRepository(this)

    companion object {
        private const val EDIT_MODE_ENABLED = 1
        private const val EDIT_MODE_DISABLED = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        linedEditText = findViewById(R.id.note_text)
        editTitle = findViewById(R.id.note_edit_title)
        viewTitle = findViewById(R.id.note_text_title)
        checkContainer = findViewById(R.id.check_container)
        backArrowContainer = findViewById(R.id.back_arrow_container)
        checkBtn = findViewById(R.id.toolbar_check)
        arrowBtn = findViewById(R.id.toolbar_back_arrow)

        isNewNote = !intent.hasExtra("selected_note")

        if (isNewNote) {
            setNewNoteProperites()
            enableEditMode()
        } else {
            initialNote = intent.getParcelableExtra("selected_note") ?: Note()
            setNoteProperties()
            disableContentInteraction()
        }

        setListeners()
    }

    private fun saveChanges(){
        var temp = linedEditText.text.toString()
        temp = temp.replace("\n", "").replace(" ", "")

        if(temp.isNotEmpty()){
            finalNote.title = editTitle.text.toString()
            finalNote.content = linedEditText.text.toString()
            finalNote.timeStamp = Date().getCurrentTimestamp()
            if(!finalNote.content.equals(initialNote.content) || !finalNote.title.equals(initialNote.content)){
                saveNote()
            }
        }
    }

    private fun saveNote(){
        if(isNewNote){
            noteRepository.insertNoteTask(finalNote)
        }else{
            finalNote.id = initialNote.id
            noteRepository.updateNote(finalNote)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("mode", mode)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mode = savedInstanceState.getInt("mode")
        if (mode == EDIT_MODE_ENABLED) {
            enableEditMode()
        }
    }

    private fun enableEditMode() {
        backArrowContainer.visibility = GONE
        checkContainer.visibility = VISIBLE

        viewTitle.visibility = GONE
        editTitle.visibility = VISIBLE

        mode = EDIT_MODE_ENABLED

        enableContentInteraction()

        showSoftKeyboard()
    }

    private fun disableEditMode() {
        backArrowContainer.visibility = VISIBLE
        checkContainer.visibility = GONE

        viewTitle.visibility = VISIBLE
        editTitle.visibility = GONE

        mode = EDIT_MODE_DISABLED

        disableContentInteraction()

        hideSoftKeyboard()

        saveChanges()
    }

    //TODO: BUG: NOT HIDING:
    //Enter Edit Mode, Press Back Button and the Press "Check" Button
    //Keyboard end up showed
    private fun hideSoftKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive)
            imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0)
    }

    private fun showSoftKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun setNoteProperties() {
        viewTitle.text = initialNote.title
        editTitle.setText(initialNote.title)
        linedEditText.setText(initialNote.content)
    }

    private fun setNewNoteProperites() {
        viewTitle.text = "Note Title"
        editTitle.setText("Note Title")

        initialNote.title = "Note Title"
        finalNote.title = "Note Title"
    }

    private fun setListeners() {
        linedEditText.setOnTouchListener(this);
        gestureDetector = GestureDetector(this, this)

        viewTitle.setOnClickListener(this)
        editTitle.addTextChangedListener(this)
        checkBtn.setOnClickListener(this)
        arrowBtn.setOnClickListener(this)

    }

    private fun disableContentInteraction() {
        linedEditText.keyListener = null
        linedEditText.isFocusable = false
        linedEditText.isFocusableInTouchMode = false
        linedEditText.isCursorVisible = false
        linedEditText.clearFocus()
    }


    private fun enableContentInteraction() {
        linedEditText.keyListener = EditText(this).keyListener
        linedEditText.isFocusable = true
        linedEditText.isFocusableInTouchMode = true
        linedEditText.isCursorVisible = true
        linedEditText.requestFocus()
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.d("GESTURE", "DOUBLE_TAP")
        enableEditMode()
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.toolbar_back_arrow -> finish()
            R.id.toolbar_check -> disableEditMode()
            R.id.note_text_title -> {
                enableEditMode()
                editTitle.requestFocus()
                editTitle.setSelection(editTitle.length())
            }
        }
    }

    override fun onBackPressed() {
        if (mode == EDIT_MODE_ENABLED) {
            onClick(checkBtn)
        } else {
            super.onBackPressed()
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewTitle.text = s
    }
}
