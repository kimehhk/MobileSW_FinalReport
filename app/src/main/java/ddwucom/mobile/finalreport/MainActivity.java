// 과제명: 영화 평점 리뷰 및 정보 앱
// 분반: 01분반
// 학번: 20190951 성명: 김은혜
// 제출일: 2021년 6월 24일

package ddwucom.mobile.finalreport;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "MainActivity";
    final int REQ_CODE = 100;
    final int UPDATE_CODE = 200;

    ListView listView;
    MovieAdapter adapter;
    ArrayList<Movie> movieList = null;
    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        movieList = new ArrayList<Movie>();
        adapter = new MovieAdapter(this, R.layout.custom_adapter, movieList);
        listView.setAdapter(adapter);
        movieDBManager = new MovieDBManager(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieList.get(position);
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("movie", movie);
                startActivityForResult(intent, UPDATE_CODE);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("리뷰 삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (movieDBManager.removeMovie(movieList.get(pos).get_id())) {
                                    Toast.makeText(MainActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                    movieList.clear();
                                    movieList.addAll(movieDBManager.getAllMovie());
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        movieList.clear();
        movieList.addAll(movieDBManager.getAllMovie());
        adapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAdd:
                Intent intentAdd = new Intent(this, AddActivity.class);
                startActivityForResult(intentAdd, REQ_CODE);
                break;
            case R.id.btnTitleSearch:
                final EditText searchTitle = new EditText(MainActivity.this);

                AlertDialog.Builder builderS = new AlertDialog.Builder(MainActivity.this);
                builderS.setTitle("영화 검색")
                        .setMessage("검색할 영화의 제목을 입력해주세요")
                        .setView(searchTitle)
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (searchTitle.getText().toString().equals("")) {
                                    Toast.makeText(getApplicationContext(), "검색 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    int searchT = movieDBManager.getMovieByTitle(searchTitle.getText().toString());
                                    if (searchT != 0) {
                                        String msg = searchT + "번 리뷰에 있습니다";
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "해당 영화 리뷰가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "검색 취소", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
            case R.id.btnActorSearch:
                final EditText searchActor = new EditText(MainActivity.this);

                AlertDialog.Builder builderA = new AlertDialog.Builder(MainActivity.this);
                builderA.setTitle("영화 검색")
                        .setMessage("검색할 영화의 출연 배우를 입력해주세요")
                        .setView(searchActor)
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            ArrayList searchA = new ArrayList();
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (searchActor.getText().toString().equals("")) {
                                    Toast.makeText(getApplicationContext(), "검색 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    searchA.addAll(movieDBManager.getMovieByActor(searchActor.getText().toString())) ;
                                    if (movieDBManager.getMovieByActor(searchActor.getText().toString()) != null) {
                                        String msg = "영화 제목은 " + searchA.get(1).toString() + "이며,\n" + searchA.get(0).toString() + "번 리뷰에 있습니다.";
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "해당 영화 리뷰가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "검색 취소", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
            case R.id.intro:
                Intent intentIntro = new Intent(this, IntroActivity.class);
                startActivity(intentIntro);
                break;
            case R.id.terminate:
                AlertDialog.Builder builderT = new AlertDialog.Builder(MainActivity.this);
                builderT.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "종료 취소", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    String movie = data.getStringExtra("movie");
                    Toast.makeText(this, movie + " 추가 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "리뷰 추가 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == UPDATE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(this, "영화 정보 수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "영화 정보 수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
