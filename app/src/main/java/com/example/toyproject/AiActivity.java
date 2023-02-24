package com.example.toyproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import com.example.toyproject.ml.ModelUnquant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.CallableStatement;

public class AiActivity extends AppCompatActivity {
    TextView result, confidence;
    TextView tv_request;
    ImageView imageView;
    Button picture,btn_yes,btn_no;
    int imageSize = 224;
    int list_cnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plantcaptuer);
        tv_request = findViewById(R.id.plantcapture_request);
        result = findViewById(R.id.plantcapture_name);
        btn_yes = findViewById(R.id.plantcapture_btn1);
        btn_no = findViewById(R.id.plantcapture_btn2);
        imageView = findViewById(R.id.plantcapture_image);
        picture = findViewById(R.id.button);

        result.setVisibility(View.INVISIBLE);
        tv_request.setVisibility(View.INVISIBLE);
        btn_no.setVisibility(View.INVISIBLE);
        btn_yes.setVisibility(View.INVISIBLE);

        picture.setOnClickListener(new View.OnClickListener() { //버튼 누름
            @Override
            public void onClick(View view) {

                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);

                } else {

                    requestPermissions(new String[]{Manifest.permission.CAMERA},100);
                }
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AiActivity.this, ResultActivity.class);
                intent.putExtra("result",result.getText().toString());
                startActivity(intent);
            }
        });
    }
    public void classifyImage(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0,0,image.getWidth(),image.getHeight());
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f/255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f/255.f));
                    byteBuffer.putFloat((val >> 0xFF) * (1.f/255.f));
                }

            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"다육이","로즈마리","몬스테라","바질트리",
                    "백화등","브레네리아","스위트바질","스킨다섭스",
                    "스피아민트","아메리칸블루","안스리움","안스리움",
                    "알로에","알로카시아","워터코인","쿠페아",
                    "테이블야자","페어리스타","호야"};

            result.setText(classes[maxPos]);
            load_image(classes[maxPos]);
            String s = "";
            for(int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }
//            confidence.setText(s);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if ( requestCode == 1 && resultCode == RESULT_OK){
            result.setVisibility(View.VISIBLE);
            tv_request.setVisibility(View.VISIBLE);
            btn_no.setVisibility(View.VISIBLE);
            btn_yes.setVisibility(View.VISIBLE);
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void load_image(String result){
        if(result.contains("false")){
            Toast.makeText(this, "이미지를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
        }else{
            Bitmap getBlob;
            String getName;
//            JSONArray list;

            try{
//                JSONArray jArray=new JSONArray(result);
                JSONObject jsonObject = new JSONObject();
                getName = jsonObject.getString("img_name");
                getBlob =StringToBitMap(jsonObject.getString("img_data"));


                Bitmap image = getBlob;
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);
//                list_cnt = jArray.length();
//                getNo=new String[list_cnt];
//                getBlob=new Bitmap[list_cnt];
//                for(int i=0;i<list_cnt;i++){
//                    JSONObject jsonObject=jArray.getJSONObject(i);
//                    getNo[i]=jsonObject.getString("NO");
//                    getBlob[i]=StringToBitMap(jsonObject.getString("IMAGE"));
//                }
//                list.setNo(getNo);
//                list.setBlob(getBlob);
//                list.notifyDataSetChanged();
            }catch (Exception e){
                String temp=e.toString();
                while (temp.length() > 0) {
                    if (temp.length() > 4000) {
                        Log.e("imageLog", temp.substring(0, 4000));
                        temp = temp.substring(4000);
                    } else {
                        Log.e("imageLog",  temp);
                        break;
                    }
                }
            }
        }
//        pd.cancel();
    }

    private Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}