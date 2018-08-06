package com.mhandharbeni.inspektoratdemo.modul.surat_masuk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.mhandharbeni.inspektoratdemo.R
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.where
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class MainSuratMasuk : AppCompatActivity(), TextWatcher {

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        doSearch(s.toString())
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainSuratMasuk::class.java)
        }
    }

    @BindView(R.id.toolbar) lateinit var toolbar : Toolbar
    @BindView(R.id.rvSuratMasuk)
    lateinit var rvSuratMasuk: RecyclerView

    private var adapter : SuratMasukAdapter? = null
    private lateinit var realm: Realm
    private val suratMasukList = ArrayList<SuratMasukModel>()


    private var mSearchAction: MenuItem? = null
    private var isSearchOpened = false
    private var edtSeach: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRealm()

        setContentView(R.layout.main_surat_masuk)
        butterknife.ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Surat Masuk (Demo)"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        insertData()
        getData()
        initAdapter()
    }

    private fun initRealm(){
        realm = Realm.getDefaultInstance()
    }
    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
    private fun insertData(){
        for (i in 1..10){
            realm.executeTransaction {

                val date = getCurrentDateTime()
                val dateInString = date.toString("yyyy/MM/dd")

                val suratMasukModel = SuratMasukModel()
                suratMasukModel.id = i.toLong()
                suratMasukModel.nomor = "$dateInString/MASUK/$i"
                suratMasukModel.tujuan = "Lantai $i"
                suratMasukModel.status = "1"
                suratMasukModel.judul_surat = "Manajemen Kepegawaian $i"
                suratMasukModel.isi_surat = "Peraturan baru untuk tiap department"
                suratMasukModel.tanggal_surat = dateInString
                it.copyToRealmOrUpdate(suratMasukModel)
            }
        }
    }

    private fun getData(){
        suratMasukList.clear()
        for (suratMasuk in realm.where<SuratMasukModel>().findAll()){
            suratMasukList.add(suratMasuk)
        }
    }

    private fun initAdapter(){
        rvSuratMasuk.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = SuratMasukAdapter(suratMasukList) { partItem : SuratMasukModel -> onSuratClick(partItem) }
        rvSuratMasuk.adapter = adapter
    }

    private fun onSuratClick(suratMasukModel: SuratMasukModel){
//        toast(suratMasukModel.nomor)
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_detail_surat, null)
        dialogBuilder.setView(dialogView)

        val txtNomorSurat = dialogView.findViewById(R.id.nomorSurat) as TextView
        val txtTanggalSurat = dialogView.findViewById(R.id.tanggalSurat) as TextView
        val txtTujuanSurat = dialogView.findViewById(R.id.tujuanSurat) as TextView
        val txtJudulSurat = dialogView.findViewById(R.id.judulSurat) as TextView
        val txtIsiSurat = dialogView.findViewById(R.id.isiSurat) as TextView
        val btnOk = dialogView.findViewById(R.id.btnOk) as AppCompatButton

        txtNomorSurat.text = suratMasukModel.nomor
        txtTanggalSurat.text = suratMasukModel.tanggal_surat
        txtTujuanSurat.text = suratMasukModel.tujuan
        txtJudulSurat.text = suratMasukModel.judul_surat
        txtIsiSurat.text = suratMasukModel.isi_surat

        val alertDialog = dialogBuilder.create()

        btnOk.onClick {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
    private fun exeSearch(search: String){
        suratMasukList.clear()
        for(suratMasuk in realm.where<SuratMasukModel>()
                .contains("nomor", search, Case.INSENSITIVE)
                .or()
                .contains("tujuan", search, Case.INSENSITIVE)
                .or()
                .contains("judul_surat", search, Case.INSENSITIVE)
                .or()
                .contains("isi_surat", search, Case.INSENSITIVE)
                .or()
                .contains("tanggal_surat", search, Case.INSENSITIVE)
                .findAll()){
            suratMasukList.add(suratMasuk)
        }
        initAdapter()
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
                addTextChangedListener(this@MainSuratMasuk)
                requestFocus()
            }

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT)


            mSearchAction?.icon = resources.getDrawable(R.drawable.ic_close_search)

            isSearchOpened = true
        }
    }

    private fun doSearch(message : String){
        exeSearch(message)
    }

    override fun onBackPressed() {
        if(isSearchOpened) {
            getData()
            initAdapter()
            handleMenuSearch()
            return
        }else{
            finish()
        }
        super.onBackPressed()
    }
}