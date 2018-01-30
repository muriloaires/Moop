package brmobi.moop.features.publicacoes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import brmobi.moop.model.rotas.RetrofitSingleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import brmobi.moop.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.imgFoto)
    ImageView imgFoto;


    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);


        final String url = getIntent().getExtras().getString("bitmap");
        Picasso.with(this).load(RetrofitSingleton.BASE_URL + url).networkPolicy(NetworkPolicy.OFFLINE).into(imgFoto, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(ImageActivity.this).load(url).into(imgFoto);
            }
        });
        mAttacher = new PhotoViewAttacher(imgFoto);
        mAttacher.setMaximumScale(3f);
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                ImageActivity.this.onBackPressed();
            }
        });
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                ImageActivity.this.onBackPressed();
            }
        });

    }

    @Override
    protected void onDestroy() {
        mAttacher.cleanup();
        super.onDestroy();
    }

    @OnClick(R.id.rootView)
    public void close(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
