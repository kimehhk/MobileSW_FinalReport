package ddwucom.mobile.finalreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etDirector;
    EditText etActor;
    EditText etGenre;
    EditText etGrade;

    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.etTitle);
        etDirector = findViewById(R.id.etDirector);
        etActor = findViewById(R.id.etActor);
        etGenre = findViewById(R.id.etGenre);
        etGrade = findViewById(R.id.etGrade);

        movieDBManager = new MovieDBManager(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                String title = etTitle.getText().toString();
                String director = etDirector.getText().toString();
                String actor = etActor.getText().toString();
                String genre = etGenre.getText().toString();
                String grade = etGrade.getText().toString();

                if (title.equals("") || director.equals("") || actor.equals("") || grade.equals("")) {
                    Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean result = movieDBManager.addNewMovie(
                            new Movie(title, director, actor, genre, Double.parseDouble(grade)));

                    if (result) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("movie", etTitle.getText().toString());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(this, "새로운 영화 추가 실패!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
