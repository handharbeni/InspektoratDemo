package com.mhandharbeni.inspektoratdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.OnClick
import com.mhandharbeni.inspektoratdemo.modul.surat_keluar.MainSuratKeluar
import org.jetbrains.anko.toast
import javax.annotation.Nullable

class MainActivity : AppCompatActivity(){
    @Nullable @BindView(R.id.llSuratMasuk) lateinit var llSuratMasuk : LinearLayout
    @Nullable @BindView(R.id.llSuratKeluar) lateinit var llSuratKeluar : LinearLayout
    @Nullable @BindView(R.id.llElapor) lateinit var llELapor : LinearLayout
    @Nullable @BindView(R.id.llEsakip) lateinit var llESakip : LinearLayout
    @Nullable @BindView(R.id.llTindakLanjut) lateinit var llTindakLanjut : LinearLayout
    @Nullable @BindView(R.id.llOther) lateinit var llOther : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        butterknife.ButterKnife.bind(this)
    }


    @OnClick(R.id.llSuratMasuk) fun suratMasuk(){
        toast("Surat Masuk")
    }

    @OnClick(R.id.llSuratKeluar) fun suratKeluar(){
        val intent = MainSuratKeluar.newIntent(applicationContext)
        startActivity(intent)
    }

    @OnClick(R.id.llElapor) fun eLapor(){
        toast("E-LAPOR Under Maintenance")
    }

    @OnClick(R.id.llEsakip) fun eSakip(){
        toast("E-SAKIP Under Maintenance")
    }

    @OnClick(R.id.llTindakLanjut) fun tindakLanjut(){
        toast("E-Tindak Lanjut Under Maintenance")
    }

    @OnClick(R.id.llOther) fun other(){
        toast("Other")
    }
}
