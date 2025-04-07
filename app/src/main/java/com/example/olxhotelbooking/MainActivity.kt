package com.example.olxhotelbooking

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(applicationContext, getSharedPreferences("osm", MODE_PRIVATE))
        setContentView(R.layout.activity_main)

        map = findViewById(R.id.osmMap)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        // Set initial location (e.g. Bangalore)
        val center = GeoPoint(12.9716, 77.5946)
        map.controller.setZoom(6.0)
        map.controller.setCenter(center)

        // Hotel markers
        addHotelMarker(GeoPoint(12.9716, 77.5946), "Hotel Taj")
        addHotelMarker(GeoPoint(13.0827, 80.2707), "Hotel Leela")
        addHotelMarker(GeoPoint(19.0760, 72.8777), "Hotel Oberoi")
    }

    private fun addHotelMarker(location: GeoPoint, title: String) {
        val marker = Marker(map)
        marker.position = location
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title
        marker.setOnMarkerClickListener { m, _ ->
            showBookingDialog(m.title)
            true
        }
        map.overlays.add(marker)
        map.invalidate()
    }

    private fun showBookingDialog(hotelName: String?) {
        AlertDialog.Builder(this)
            .setTitle("Booking Confirmation")
            .setMessage("Room at $hotelName has been successfully booked.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
