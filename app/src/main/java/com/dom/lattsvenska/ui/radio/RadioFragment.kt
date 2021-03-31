package com.dom.lattsvenska.ui.radio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dom.lattsvenska.R


class RadioFragment : Fragment() {

    companion object {
        val RADIO_URL = "https://sverigesradio.se/radioswedenpalattsvenska"
    }

    private lateinit var radioViewModel: RadioViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        radioViewModel = ViewModelProvider(this).get(RadioViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_radio, container, false)

        val webView: WebView = root.findViewById(R.id.fragment_radio_webview)
        webView.loadUrl(RADIO_URL)
        webView.settings.javaScriptEnabled = true

        radioViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}