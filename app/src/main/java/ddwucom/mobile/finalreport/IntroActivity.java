package ddwucom.mobile.finalreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMain:
                finish();
                break;
        }
    }
}