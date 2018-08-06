package com.mhandharbeni.inspektoratdemo.modul.surat_masuk

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mhandharbeni.inspektoratdemo.R

class SuratMasukAdapter(private val suratMasukList:ArrayList<SuratMasukModel>, val clickListener : (SuratMasukModel) -> Unit): RecyclerView.Adapter<SuratMasukAdapter.Holder>() {
    class Holder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        val cvParent = itemView.findViewById<CardView>(R.id.cvParent)!!
        val nomorSurat = itemView.findViewById<TextView>(R.id.txtNomorSurat)!!
        val tanggalSurat = itemView.findViewById<TextView>(R.id.txtTanggalSurat)!!
        val tujuanSurat = itemView.findViewById<TextView>(R.id.txtTujuanSurat)!!
        val judulSurat = itemView.findViewById<TextView>(R.id.txtJudulSurat)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuratMasukAdapter.Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_surat_masuk, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return suratMasukList.size
    }

    override fun onBindViewHolder(holder: SuratMasukAdapter.Holder, position: Int) {
        holder.nomorSurat.text = suratMasukList[position].nomor
        holder.tanggalSurat.text = suratMasukList[position].tanggal_surat
        holder.tujuanSurat.text = suratMasukList[position].tujuan
        holder.judulSurat.text = suratMasukList[position].judul_surat
        holder.cvParent.setOnClickListener {
            clickListener(suratMasukList[position])
        }
    }

}