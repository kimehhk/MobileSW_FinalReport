package ddwucom.mobile.finalreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    Movie movie;

    EditText etTitle;
    EditText etDirector;
    EditText etActor;
    EditText etGenre;
    EditText etGrade;

    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        movie = (Movie) getIntent().getSerializableExtra("movie");

        etTitle = findViewById(R.id.etTitle);
        etDirector = findViewById(R.id.etDirector);
        etActor = findViewById(R.id.etActor);
        etGenre = findViewById(R.id.etGenre);
        etGrade = findViewById(R.id.etGrade);

        etTitle.setHint(movie.getTitle());
        etDirector.setHint(movie.getDirector());
        etActor.setHint(movie.getActor());
        etGenre.setHint(movie.getGenre());
        etGrade.setHint(Double.toString(movie.getGrade()));

        movieDBManager = new MovieDBManager(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_update:
                if (etTitle.getText().toString().equals("") || etGrade.getText().toString().equals("")) {
                    Toast.makeText(this, "제목과 평점은 필수 수정 항목입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    movie.setTitle(etTitle.getText().toString());
                    movie.setDirector(etDirector.getText().toString());
                    movie.setActor(etActor.getText().toString());
                    movie.setGenre(etGenre.getText().toString());
                    movie.setGrade(Double.parseDouble(etGrade.getText().toString()));

                    if (movieDBManager.modifyMovie(movie)) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("movie", movie);
                        setResult(RESULT_OK, resultIntent);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                }
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
