package com.example.theanh.exe_1;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.DatePicker;
import android.widget.ImageView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    private String text;
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText edt_name, edt_ns, edt_email;
    private Button btn_ns, btn_img, btn_submit;
    private Spinner sp_gt;
    private int mDay, mMonth, mYear;
    private ImageView img_v;

    //private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_name = (EditText) findViewById(R.id.edt_Name);
        //edt_ns = (EditText) findViewById(R.id.edt_NS);
        edt_email = (EditText) findViewById(R.id.edt_Email);
        btn_ns = (Button) findViewById(R.id.btn_NS);
        sp_gt = (Spinner) findViewById(R.id.sp_GT);
        img_v = (ImageView) findViewById(R.id.Img_V);
        //btn_ns.setText("...");
        //btn_ns.setTextSize(20);
        btn_ns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_ns) {
                    Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            btn_ns.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
                    dpd.show();
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Nam, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gt.setAdapter(adapter);
        sp_gt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_img = (Button) findViewById(R.id.btn_Img);
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ho_ten = edt_name.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                //String gioitinh = sp_gt;
                String ngaysinh = btn_ns.getText().toString().trim();
                img_v.setDrawingCacheEnabled(true);
                Bitmap b = img_v.getDrawingCache();
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ho_ten", ho_ten);
                bundle.putString("email", email);
                bundle.putString("ngaysinh", ngaysinh);
                intent.putExtra("getData",text);
                intent.putExtra("Bitmap",b);
                intent.putExtras(bundle);


                startActivity(intent);
                finish();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take photo", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("Take photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri f = Uri.fromFile(getOutputMediaFile());
                    startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
                } else if (options[i].equals("Gallery")) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                } else if (options[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public static File getOutputMediaFile() {
        File m = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME);
        if (!m.exists()) {
            if (!m.mkdirs()) {
                Log.d(TAG, "Lỗi tạo danh mục " + Config.IMAGE_DIRECTORY_NAME);
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File f = new File(m.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        MainActivity.super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
                try {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    img_v.setImageBitmap(imageBitmap);
                } catch (NullPointerException npe) {
                    Log.e("onActivityResult", npe.toString());
                } catch (Exception e) {
                    Log.e("onActivityResult", e.toString());
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                Uri imageUri = data.getData();
                img_v.setImageURI(imageUri);
            }
        }
    }


}
