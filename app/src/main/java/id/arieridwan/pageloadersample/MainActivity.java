package id.arieridwan.pageloadersample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import id.arieridwan.lib.PageLoader;

public class MainActivity extends AppCompatActivity {

    public PageLoader pageLoader;
    public int AnimationMode;
    public int LoadMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageLoader = (PageLoader) findViewById(R.id.pageloader);
        pageLoader.setOnRetry(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageLoader.startProgress();
            }
        });
    }

    public void startProgress(View v){
        pageLoader.startProgress();
        LoadMode = 1;
    }

    public void stopProgress(View v){
        pageLoader.stopProgress();
        LoadMode = 2;
    }

    public void stopAndError(View v){
        pageLoader.stopProgressAndFailed();
        LoadMode = 3;
    }

    public void fullPage(View v){
        Intent i = new Intent(this,BlankActivity.class);
        i.putExtra(Constant.LOAD_MODE,LoadMode);
        i.putExtra(Constant.ANIMATION_MODE,AnimationMode);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.anim1:
                pageLoader.setLoadingAnimationMode(pageLoader.ROTATE_MODE);
                AnimationMode = 1;
                break;
            case R.id.anim2:
                pageLoader.setLoadingAnimationMode(pageLoader.FLIP_MODE);
                AnimationMode = 2;
                break;
            case R.id.anim3:
                pageLoader.setLoadingAnimationMode(pageLoader.VIBRATE_MODE);
                AnimationMode = 3;
                break;
            case R.id.anim4:
                pageLoader.setLoadingAnimationMode(pageLoader.SHAKE_MODE);
                AnimationMode = 4;
                break;
            case R.id.anim5:
                pageLoader.setLoadingAnimationMode(pageLoader.BOUNCE_MODE);
                AnimationMode = 5;
                break;
            case R.id.fullpage:
                Intent i = new Intent(this,BlankActivity.class);
                i.putExtra(Constant.LOAD_MODE,LoadMode);
                i.putExtra(Constant.ANIMATION_MODE,AnimationMode);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
