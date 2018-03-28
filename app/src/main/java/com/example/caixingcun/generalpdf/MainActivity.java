package com.example.caixingcun.generalpdf;


import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;



import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.itextpdf.text.Rectangle.RIGHT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RxPermissions(MainActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
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
                                            Log.d("tag", dkzzszyfp() + "");
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
     * 手动创建 document 创建pdf内容 并保存
     * @return
     */
    public int dkzzszyfp() {
        int flag = -1;
        // 1:建立Document对象实例
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        FileOutputStream fos;
        try {
            File file = new File(
                    Environment.getExternalStorageDirectory()
                            + "/kuangtiecheng1.pdf");
            ///storage/emulated/0/kuangtiecheng1.pdf
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            Log.d("tag", file.getAbsolutePath());
            // 2:建立一个PDF 写入器与document对象关联通过书写器(Writer)可以将文档写入到磁盘中
            PdfWriter.getInstance(document, fos);
            Log.d("tag", 1 + "step");
            // 3:打开文档
            document.open();
            Log.d("tag", 2 + "step");
            // 解决中文不显示问题

            // 解决中文不显示问题
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            InputStream abpath = getClass().getResourceAsStream("/assets/msyh.ttf");
//            String path = new String(InputStreamToByte(abpath ));
//
//
//
//            BaseFont baseFont = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontChina18 = new Font(bfChinese, 18);
            Font fontChina12 = new Font(bfChinese, 12);

            // 4:向文档添加内容
            // 标题
            Paragraph titleParagraph = new Paragraph("业务顾问分析表", fontChina18);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);// 居中
            document.add(titleParagraph);
            Log.d("tag", 3 + "step");
            // 空格
            Paragraph blank1 = new Paragraph(" ");
            document.add(blank1);

            // 编号
            Chunk c1 = new Chunk("编号：", fontChina12);
            Chunk c2 = new Chunk("20160531001", fontChina12);
            Paragraph snoParagraph = new Paragraph();
            snoParagraph.add(c1);
            snoParagraph.add(c2);
            snoParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(snoParagraph);
            Log.d("tag", 4 + "step");
            // 填开日期
            Chunk c5 = new Chunk("填开日期：2016年05月14日", fontChina12);
            Paragraph tkrqParagraph = new Paragraph();
            tkrqParagraph.add(c5);
            tkrqParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(tkrqParagraph);
            Log.d("tag", 5 + "step");
            // 空格
            document.add(blank1);

            // 表格处理
            PdfPTable table = new PdfPTable(6);// 八列
            table.setWidthPercentage(100);// 表格宽度为100%
            Log.d("tag", 6 + "step");
//            InputStream abpath = getClass().getResourceAsStream(
//                    "file:///android_assets/ic_launcher.png");
        //     String path = new String(InputStreamToByte(abpath ));


       //      String path = "file:///android_asset/bq.png";
        //     Image image2 = Image.getInstance(InputStreamToByte(abpath));
         //   Image image2 = Image.getInstance("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2906209324,3091539529&fm=27&gp=0.jpg");
            PdfPCell cell8 = new PdfPCell();

             cell8.setFixedHeight(20);
    //         cell8.setImage(image2);

             cell8.disableBorderSide(1);
             cell8.disableBorderSide(2);
             cell8.disableBorderSide(4);
             cell8.disableBorderSide(8);
             cell8.setHorizontalAlignment(RIGHT);

            cell8.setBorderWidth(1);
            // cell8.setColspan(2);// 跨2列
            cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
        //    cell8.setImage(image2);
            table.addCell(cell8);

            PdfPCell cell9 = new PdfPCell();
            cell9.setBorderWidth(1);
            cell9.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell9.setColspan(4);// 跨两列
            cell9.setPhrase(new Paragraph("北汽服务问诊", fontChina12));
            table.addCell(cell9);

            PdfPCell cell10 = new PdfPCell();
            cell10.setBorderWidth(1);
            cell10.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell10.setPhrase(new Paragraph("经销商代码001211", fontChina12));
            table.addCell(cell10);
            // Row1
            PdfPCell cell14 = new PdfPCell();
            cell14.setBorderWidth(1);
            cell14.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell14.setPhrase(new Paragraph("客服姓名", fontChina12));
            table.addCell(cell14);
            PdfPCell cell15 = new PdfPCell();
            cell15.setBorderWidth(1);
            cell15.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell15.setPhrase(new Paragraph("旷铁成", fontChina12));
            table.addCell(cell15);
            PdfPCell cell16 = new PdfPCell();
            cell16.setBorderWidth(1);
            cell16.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell16.setPhrase(new Paragraph("车牌号", fontChina12));
            table.addCell(cell16);
            PdfPCell cell17 = new PdfPCell();
            cell17.setBorderWidth(1);
            cell17.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell17.setPhrase(new Paragraph("京NY2008", fontChina12));
            table.addCell(cell17);
            PdfPCell cell18 = new PdfPCell();
            cell18.setBorderWidth(1);
            cell18.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell18.setPhrase(new Paragraph("里程数", fontChina12));
            table.addCell(cell18);
            PdfPCell cell19 = new PdfPCell();
            cell19.setBorderWidth(1);
            cell19.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell19.setPhrase(new Paragraph("1098公里", fontChina12));
            table.addCell(cell19);
            PdfPCell cell22 = new PdfPCell();
            cell22.setBorderWidth(1);
            cell22.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell22.setPhrase(new Paragraph("联系电话", fontChina12));
            table.addCell(cell22);
            PdfPCell cell23 = new PdfPCell();
            cell23.setBorderWidth(1);
            cell23.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell23.setPhrase(new Paragraph("18606291071" + "", fontChina12));
            table.addCell(cell23);
            PdfPCell cell24 = new PdfPCell();
            cell24.setBorderWidth(1);
            cell24.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell24.setPhrase(new Paragraph("VNI", fontChina12));
            table.addCell(cell24);
            PdfPCell cell25 = new PdfPCell();
            cell25.setBorderWidth(1);
            cell25.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell25.setPhrase(new Paragraph("LSVFA257845", fontChina12));
            table.addCell(cell25);
            PdfPCell cell26 = new PdfPCell();
            cell26.setBorderWidth(1);
            cell26.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell26.setPhrase(new Paragraph("进店时间", fontChina12));
            table.addCell(cell26);
            PdfPCell cell27 = new PdfPCell();
            cell27.setBorderWidth(1);
            cell27.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell27.setPhrase(new Paragraph("2016/4/15", fontChina12));
            table.addCell(cell27);

            // 价税合计
            PdfPCell cell30 = new PdfPCell();
            cell30.setBorderWidth(1);
            cell30.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell30.setPhrase(new Paragraph("车型", fontChina12));
            table.addCell(cell30);
            PdfPCell cell31 = new PdfPCell();
            cell31.setBorderWidth(1);
            // cell31.setColspan(4);// 跨四列
            cell31.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell31.setPhrase(new Paragraph("X65", fontChina12));
            table.addCell(cell31);
            PdfPCell cell32 = new PdfPCell();
            cell32.setBorderWidth(1);
            cell32.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell32.setPhrase(new Paragraph("颜色", fontChina12));
            table.addCell(cell32);
            PdfPCell cell33 = new PdfPCell();
            cell33.setBorderWidth(1);
            // cell33.setColspan(2);// 跨两列
            cell33.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell33.setPhrase(new Paragraph("香槟金", fontChina12));
            table.addCell(cell33);
            // 备注
            PdfPCell cell34 = new PdfPCell();
            cell34.setBorderWidth(1);
            // cell34.setMinimumHeight(40);
            cell34.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell34.setPhrase(new Paragraph("预约客户", fontChina12));
            table.addCell(cell34);
            PdfPCell cell35 = new PdfPCell();
            cell35.setBorderWidth(1);
            // cell35.setColspan(7);
            cell35.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell35.setPhrase(new Paragraph("是", fontChina12));
            table.addCell(cell35);

            // 销货单位
            PdfPCell cell36 = new PdfPCell();
            cell36.setBorderWidth(1);// Border宽度为1
            // cell36.setRowspan(3);// 跨三行
            cell36.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell36.setPhrase(new Paragraph("是否车检", fontChina12));
            table.addCell(cell36);

            PdfPCell cell37 = new PdfPCell();
            cell37.setBorderWidth(1);
            cell37.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell37.setPhrase(new Paragraph("是", fontChina12));
            table.addCell(cell37);
            PdfPCell cell38 = new PdfPCell();
            cell38.setBorderWidth(1);
            // cell38.setColspan(3);// 跨三列
            cell38.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell38.setPhrase(new Paragraph("维修类别", fontChina12));
            table.addCell(cell38);

            PdfPCell cell39 = new PdfPCell();
            cell39.setBorderWidth(1);
            cell39.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell39.setPhrase(new Paragraph("板喷", fontChina12));
            table.addCell(cell39);
            PdfPCell cell40 = new PdfPCell();
            cell40.setBorderWidth(1);
            // cell40.setColspan(2);// 跨两列
            cell40.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell40.setPhrase(new Paragraph("是否洗车", fontChina12));
            table.addCell(cell40);

            PdfPCell cell41 = new PdfPCell();
            cell41.setBorderWidth(1);
            cell41.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell41.setPhrase(new Paragraph("是", fontChina12));
            table.addCell(cell41);
            PdfPCell cell42 = new PdfPCell();
            cell42.setBorderWidth(1);
            cell42.setColspan(3);// 跨三列存储
            cell42.setRowspan(3);
            cell42.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell42.setPhrase(new Paragraph("用户表述", fontChina12));
            table.addCell(cell42);

            PdfPCell cell43 = new PdfPCell();
            cell43.setBorderWidth(1);
            cell43.setColspan(3);// 跨三列存储
            cell43.setRowspan(3);
            cell43.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell43.setPhrase(new Paragraph("初步诊断", fontChina12));
            table.addCell(cell43);
            PdfPCell cell44 = new PdfPCell();
            cell44.setBorderWidth(1);
            // cell44.setColspan(2);// 跨两列
            cell42.setRowspan(5);
            cell44.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell44.setPhrase(new Paragraph("问诊", fontChina12));
            table.addCell(cell44);

            PdfPCell cell45 = new PdfPCell();
            cell45.setBorderWidth(1);
            cell45.setColspan(5);
            cell42.setRowspan(5);
            cell45.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell45.setPhrase(new Paragraph(
                    "1:发生时间:3天前\n2:出现频率:3天前\n3:工作状态:p档\n4:何时发生:起步\n5:天气状况:全天",
                    fontChina12));
            table.addCell(cell45);
///storage/emulated/0/kuangtiecheng1.pdf
            document.add(table);

            document.add(blank1);

             // 底部额外信息
             StringBuilder sb1 = new StringBuilder();
             sb1.append("申请代开发票纳税人（公章）_________");
             sb1.append("法人代表_________");
             sb1.append("财务负责人_________");
             sb1.append("填写人_________");
             Paragraph pE = new Paragraph(sb1.toString(), fontChina12);
             pE.setAlignment(Element.ALIGN_CENTER);
             document.add(pE);

             document.add(blank1);

             // 注
             StringBuilder sb2 = new StringBuilder();
             sb2.append("注：第一联：税务机关代开发票岗位留存。");
             sb2.append("第二联：税务机关税款征收岗位留存。");
             Paragraph pZ = new Paragraph(sb2.toString(), fontChina12);
             pZ.setAlignment(Element.ALIGN_CENTER);
             document.add(pZ);

            // 5:关闭文档
            document.close();
            fos.flush();
            fos.close();
            flag = 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            flag = -1;
        } catch (DocumentException e) {
            e.printStackTrace();
            flag = -1;
        } catch (IOException e) {
            e.printStackTrace();
            flag = -1;
        }
        return flag;
    }
    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;

    }
}
