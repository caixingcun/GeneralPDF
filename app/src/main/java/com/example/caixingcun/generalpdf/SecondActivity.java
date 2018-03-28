package com.example.caixingcun.generalpdf;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
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
    private String path = Environment.getExternalStorageDirectory() + "/pdf/";
    private String fileName = "test1.pdf";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mContext = this;
        Log.e("tag111", path);
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
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
                                Log.e("tag111", "开启线程");
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
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Uri uri = Uri.fromFile(new File(path));
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setDataAndType(uri, "application/pdf");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);


//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.addCategory("android.intent.category.DEFAULT");
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    uri = FileProvider.getUriForFile(SecondActivity.this, "com.example.caixingcun.fileprovider", new File(path, fileName));
//                } else {
//                    uri = Uri.fromFile(new File(path, fileName));
//
//                }
//
//
//    //            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                //pdf文件要被读取所以加入读取权限
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                shareIntent.setDataAndType(uri, "application/pdf");
//                shareIntent.setType(getMimeType(path));
//
//                try {
//                    startActivity(Intent.createChooser(shareIntent,fileName));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                File date = new File(path, fileName);
                if (date.exists()) {
                    Toast.makeText(mContext, "文件存在", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.addCategory("android.intent.category.DEFAULT");
                Uri pdfUri;
                //   pdfUri = Uri.fromFile(date);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pdfUri = FileProvider.getUriForFile(SecondActivity.this, "com.example.caixingcun.fileprovider", new File(path, fileName));
                } else {
                    pdfUri = Uri.fromFile(new File(path, fileName));
                }
                intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
                intent.setType("application/pdf");
                try {
                    mContext.startActivity(Intent.createChooser(intent, date.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PDFView  pdfView = (PDFView)findViewById(R.id.pdfView);
                pdfView.fromFile(new File(path, fileName))
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        //.onDraw(onDrawListener)
                        .onLoad(new OnLoadCompleteListener() {
                            @Override
                            public void loadComplete(int nbPages) {

                            }
                        })
                        .onPageChange(new OnPageChangeListener() {
                            @Override
                            public void onPageChanged(int page, int pageCount) {

                            }
                        })
                        //.onPageScroll(onPageScrollListener)
                        //.onError(onErrorListener)
                        .enableAnnotationRendering(false)
                        .password(null)
                        .scrollHandle(new DefaultScrollHandle(mContext))
                        .load();

            }
        });


    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param filePath
     */
    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }

    /**
     * 获取 asset下pdf 表单模板(已经使用acrobat 添加表单域)
     * 填充表单域内容
     * 生成新的pdf
     */
    private void convertPdf() {
        try {
            Log.e("tag", "start");
            //     String pdfPath = "file:///android_asset/test.pdf";
            InputStream open = mContext.getClass().getClassLoader().getResourceAsStream("assets/test2.pdf");
            PdfReader reader = new PdfReader(open);
            Log.e("tag", "1step");
            PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(new File(path, fileName)));
            Log.e("tag", "2step");
            AcroFields form = pdfStamper.getAcroFields();
            Log.e("tag", "3step");
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Log.e("tag", "4step");
            Font fontChina18 = new Font(bfChinese, 18);
            Log.e("tag", "5step");
            form.setFieldProperty("Text1", "textfont", bfChinese, null);
            Log.e("tag", "6step");
            form.setField("Text1", "内容1");
            Log.e("tag", "7step");
            pdfStamper.setFormFlattening(true);
            Log.e("tag", "8step");
            pdfStamper.close();
            Log.e("tag", "9step");
            reader.close();
            Log.e("tag", "end");
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
