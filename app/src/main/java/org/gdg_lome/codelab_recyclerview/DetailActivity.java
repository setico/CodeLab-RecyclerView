
package org.gdg_lome.codelab_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by setico on 08/10/15.
 */
public class DetailActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView nom;
    private TextView description;
    private ShareActionProvider mShareActionProvider;
    private Bundle extra;
    private ProgressBar progress;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(
                shareIntent(extra.getString(Config.NOM_KEY)+"//"
                        +extra.getString(Config.DESCRIPTION_KEY)));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public Intent shareIntent(String share_text){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,share_text);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        logo = (ImageView) findViewById(R.id.logo);
        nom = (TextView) findViewById(R.id.nom);
        description = (TextView) findViewById(R.id.description);
        progress = (ProgressBar)findViewById(R.id.progress);

        extra = getIntent().getExtras();
        Glide.with(this)
                .load(extra.getString(Config.LOGO_KEY))
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                }) .into(logo);

        nom.setText(extra.getString(Config.NOM_KEY));
        description.setText(extra.getString(Config.DESCRIPTION_KEY));

    }
}
