package com.dom.lattsvenska.ui.svt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dom.lattsvenska.R
import com.dom.lattsvenska.ui.radio.RadioFragment

class SvtFragment : Fragment() {

    companion object {
        val SVT_URL = "https://www.svtplay.se/nyheter-pa-latt-svenska"
    }

    private lateinit var svtViewModel: SvtViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        svtViewModel =
                ViewModelProvider(this).get(SvtViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_svt, container, false)

        val webView: WebView = root.findViewById(R.id.fragment_radio_webview)
        webView.loadUrl(SVT_URL)
        webView.settings.javaScriptEnabled = true

        svtViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}