//package com.example.toyproject;
//
//import static com.example.toyproject.Define.NOWDATE;
//
//import android.Manifest;
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.media.ThumbnailUtils;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import com.example.toyproject.ml.ModelUnquant;
//import org.tensorflow.lite.DataType;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AiCameraFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AiCameraFragment extends Fragment {
//    TextView result, confidence;
//    ImageView imageView;
//    Button picture;
//    int imageSize = 224;
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AiCameraFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AiCameraFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AiCameraFragment newInstance(String param1, String param2) {
//        AiCameraFragment fragment = new AiCameraFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = null;
//        view = inflater.inflate(R.layout.fragment_plantcaptuer, container, false);
//        result = view.findViewById(R.id.result);
//        confidence = view.findViewById(R.id.confidence);
//        imageView = view.findViewById(R.id.plantai_image);
//        picture = view.findViewById(R.id.button);
//
//        picture.setOnClickListener(new View.OnClickListener() { //버튼 누름
//            @Override
//            public void onClick(View view) {
//
//                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, 1);
//                } else {
//
//                    requestPermissions(new String[]{Manifest.permission.CAMERA},100);
//                }
//            }
//        });
//
//
//
//
//
//
//        return view;
//    }
//    public void classifyImage(Bitmap image){
//        try {
//            ModelUnquant model = ModelUnquant.newInstance(getContext());
//
//            // Creates inputs for reference.
//            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
//            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
//            byteBuffer.order(ByteOrder.nativeOrder());
//
//            int [] intValues = new int[imageSize * imageSize];
//            image.getPixels(intValues, 0, image.getWidth(), 0,0,image.getWidth(),image.getHeight());
//            int pixel = 0;
//            for(int i = 0; i < imageSize; i++){
//                for(int j = 0; j < imageSize; j++){
//                    int val = intValues[pixel++];
//                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f/255.f));
//                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f/255.f));
//                    byteBuffer.putFloat((val >> 0xFF) * (1.f/255.f));
//                }
//
//            }
//
//            inputFeature0.loadBuffer(byteBuffer);
//
//            // Runs model inference and gets result.
//            ModelUnquant.Outputs outputs = model.process(inputFeature0);
//            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//            float[] confidences = outputFeature0.getFloatArray();
//            int maxPos = 0;
//            float maxConfidence = 0;
//            for(int i = 0; i < confidences.length; i++){
//                if(confidences[i] > maxConfidence){
//                    maxConfidence = confidences[i];
//                    maxPos = i;
//                }
//            }
//
//            String[] classes = {"다육이","로즈마리","몬스테라","바질트리",
//                    "백화등","브레네리아","스위트바질","스킨다섭스",
//                    "스피아민트","아메리칸블루","안스리움","안스리움",
//                    "알로에","알로카시아","워터코인","쿠페아",
//                    "테이블야자","페어리스타","호야"};
//
//            result.setText(classes[maxPos]);
//
//            String s = "";
//            for(int i = 0; i < classes.length; i++){
//                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
//            }
//            confidence.setText(s);
//
//            // Releases model resources if no longer used.
//            model.close();
//        } catch (IOException e) {
//            // TODO Handle the exception
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        if ( requestCode == 1 && resultCode == RESULT_OK){
//            Bitmap image = (Bitmap) data.getExtras().get("data");
//            int dimension = Math.min(image.getWidth(), image.getHeight());
//            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
//            imageView.setImageBitmap(image);
//
//            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//            classifyImage(image);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//}