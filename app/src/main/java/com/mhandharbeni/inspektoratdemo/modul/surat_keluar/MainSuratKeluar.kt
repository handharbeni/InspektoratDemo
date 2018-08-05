package com.mhandharbeni.inspektoratdemo.modul.surat_keluar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import butterknife.BindView
import com.mhandharbeni.inspektoratdemo.R
import org.jetbrains.anko.toast


class MainSuratKeluar : AppCompatActivity(), TextWatcher{
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        doSearch(s.toString())
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainSuratKeluar::class.java)
        }
    }

    @BindView(R.id.toolbar) lateinit var toolbar : Toolbar

    private var mSearchAction: MenuItem? = null
    private var isSearchOpened = false
    private var edtSeach: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_surat_keluar)
        butterknife.ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        mSearchAction = menu?.findItem(R.id.action_search)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            R.id.action_search -> {
                handleMenuSearch()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleMenuSearch() {
        val action = supportActionBar

        if (isSearchOpened) {

            action!!.setDisplayShowCustomEnabled(false)
            action.setDisplayShowTitleEnabled(true)

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edtSeach?.windowToken, 0)

            mSearchAction?.icon = resources.getDrawable(R.drawable.ic_search_black)

            isSearchOpened = false
        } else {

            action!!.setDisplayShowCustomEnabled(true)
            action.setCustomView(R.layout.layout_search_toolbar)
            action.setDisplayShowTitleEnabled(false)

            edtSeach = action.customView.findViewById(R.id.edtSearch) as EditText



            edtSeach!!.run {
                addTextChangedListener(this@MainSuratKeluar)
                requestFocus()
            }

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT)


            mSearchAction?.icon = resources.getDrawable(R.drawable.ic_close_search)

            isSearchOpened = true
        }
    }

    private fun doSearch(message : String){
        toast(message)
    }

    override fun onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch()
            return
        }else{
            finish()
        }
        super.onBackPressed()
    }
}