package x7a.droid.advancedapp.trial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import x7a.droid.advancedapp.LoginActivity;
import x7a.droid.advancedapp.MapsPage;
import x7a.droid.advancedapp.R;

public class Try extends AppCompatActivity {
    SharedPreferences get_shared_preference;
    Button logout;
    private ImageView imgPict;
    private String URL_IMAGE = "http://arifpratama.blog.widyatama.ac.id/files/2016/02/iOS-vs-Android.jpg";
    List<String> image_urls = new ArrayList<>(Arrays.asList(
            "http://square.github.io/picasso/static/sample.png",
            "http://square.github.io/picasso/static/debug.png",
            "http://thesource.com/wp-content/uploads/2015/02/Pablo_Picasso1.jpg",
            "https://upload.wikimedia.org/wikipedia/en/2/23/Pablo_Picasso,_1901-02,_Femme_au_caf%C3%A9_%28Absinthe_Drinker%29,_oil_on_canvas,_73_x_54_cm,_Hermitage_Museum,_Saint_Petersburg,_Russia.jpg",
            "https://upload.wikimedia.org/wikipedia/en/7/74/PicassoGuernica.jpg",
            "http://i01.i.aliimg.com/wsphoto/v0/810502873/Diy-digital-oil-painting-diy-30-40-font-b-picasso-b-font-abstract-music-painting-by.jpg"
    )
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);
        get_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
        Toast.makeText(Try.this, "Welcome : "+get_shared_preference.getString("email",""), Toast.LENGTH_SHORT).show();

        logout = (Button)findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor sp_editor = get_shared_preference.edit();
//                sp_editor.putString("email", "");
//                sp_editor.putString("token_authentication", "");
//                sp_editor.commit();
//                Toast.makeText(Try.this, "Logout Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Try.this,MapsPage.class);
                startActivity(i);
                finish();
            }
        });
        imgPict = (ImageView) findViewById(R.id.imgButton);
        Picasso.with(this)
                .load(URL_IMAGE)
// optional                .placeholder(R.mipmap.ic_bk)
// optional                .error(R.drawable.logo_supercell)
// optional                .resize(400,400)
                .into(imgPict);
            }
        }
