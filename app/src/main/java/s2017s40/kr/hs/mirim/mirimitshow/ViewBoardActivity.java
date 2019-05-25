package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBoardActivity extends AppCompatActivity {
    TextView postTitle, postContent;
    ImageView postImage;
    private Services service;
    Utils utils = new Utils();
    String BoardToken,position;
    Board getBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        postTitle = findViewById(R.id.view_Board_title_text);
        postContent = findViewById(R.id.view_Board_content_text);
        postImage = findViewById(R.id.view_Board_content_img);

        /* i.putExtra("BoardToken",myDataset.get(position).getGroup_token());
                i.putExtra("position", position);*/
        Intent i = getIntent();
        BoardToken = i.getExtras().getString("BoardToken");
        position = i.getExtras().getString("position");

        service = utils.mRetrofit.create(Services.class);

        Call<Board> call = service.getboard(BoardToken);

        call.enqueue(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                if(response.code() == 200){//성공
                    getBoard =  response.body();
                    setBoard();
                    Toast.makeText(ViewBoardActivity.this,"returns user's Groups",Toast.LENGTH_LONG).show();
                } else if(response.code() == 209){//실패
                    Toast.makeText(ViewBoardActivity.this,"No board found",Toast.LENGTH_LONG).show();
                }else if(response.code() == 400){//실패
                    Toast.makeText(ViewBoardActivity.this,"invalid input, object invalid",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                Log.e("getgroupboardsError", t.toString());
            }
        });
    }
    public void setBoard(){
        postTitle.setText(getBoard.getTitle());
        postContent.setText(getBoard.getContent());
        //작성자 이름.setText(getBoard.getAuthor());
        if(!getBoard.getImage().getUrl().isEmpty()){
            Picasso.get().load("http://13.125.15.20/" + getBoard.getImage().getUrl()).into(postImage);
            postImage.setVisibility(View.VISIBLE);
        }
    }
}
