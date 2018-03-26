package com.example.caixingcun.generalpdf;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by caixingcun on 2018/3/26.
 */

public class SecondActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mContext = this;
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RxPermissions(SecondActivity.this)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            convertPdf();
                                        }
                                    }).start();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
                ;


            }
        });
    }

    /**
     * 获取 asset下pdf 表单模板(已经使用acrobat 添加表单域)
     *      填充表单域内容
     *      生成新的pdf
     */
    private void convertPdf() {
        try {
            Log.d("tag", "start");
       //     String pdfPath = "file:///android_asset/test.pdf";
            InputStream open = mContext.getClass().getClassLoader().getResourceAsStream("assets/test2.pdf");
            PdfReader reader = new PdfReader(open);
            Log.d("tag", "1step");
            PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(Environment.getExternalStorageDirectory() + "/test1.pdf"));
            Log.d("tag", "2step");
            AcroFields form = pdfStamper.getAcroFields();
            Log.d("tag", "3step");
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Log.d("tag", "4step");
            Font fontChina18 = new Font(bfChinese, 18);
            Log.d("tag", "5step");
            form.setFieldProperty("Text1", "textfont", bfChinese, null);
            Log.d("tag", "6step");
            form.setField("Text1", "内容1");
            Log.d("tag", "7step");
            pdfStamper.setFormFlattening(true);
            Log.d("tag", "8step");
            pdfStamper.close();
            Log.d("tag", "9step");
            reader.close();
            Log.d("tag", "end");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "转换成功", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
