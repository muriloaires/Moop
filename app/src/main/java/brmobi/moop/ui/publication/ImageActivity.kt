package brmobi.moop.ui.publication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import brmobi.moop.R
import brmobi.moop.utils.AppConstants
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.*
import uk.co.senab.photoview.PhotoViewAttacher

class ImageActivity : AppCompatActivity() {


    private var mAttacher: PhotoViewAttacher? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val url = intent.extras!!.getString("bitmap")
        Picasso.with(this).load(AppConstants.BASE_URL + url!!).networkPolicy(NetworkPolicy.OFFLINE).into(imgFoto!!, object : Callback {
            override fun onSuccess() {

            }

            override fun onError() {
                Picasso.with(this@ImageActivity).load(url).into(imgFoto)
            }
        })
        mAttacher = PhotoViewAttacher(imgFoto)
        mAttacher!!.maximumScale = 3f
        mAttacher!!.onViewTapListener = PhotoViewAttacher.OnViewTapListener { view, x, y -> this@ImageActivity.onBackPressed() }
        mAttacher!!.onPhotoTapListener = PhotoViewAttacher.OnPhotoTapListener { view, x, y -> this@ImageActivity.onBackPressed() }
        rootView.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onDestroy() {
        mAttacher!!.cleanup()
        super.onDestroy()
    }


}
