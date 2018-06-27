package com.appgather.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

//ZIP解压，并不会用到

public class ZipUtil {


    public static void zip(String src,String dest) throws IOException {
        //定义压缩输出流
        ZipOutputStream out = null;
        try {
            //传入源文件
            File fileOrDirectory= new File(src);
            File outFile= new File(dest);
            //传入压缩输出流
            //创建文件前几级目录
            if (!outFile.exists()){
                File parentfile=outFile.getParentFile();
                if (!parentfile.exists()){
                    parentfile.mkdirs();
                }
            }
            //可以通过createNewFile()函数这样创建一个空的文件，也可以通过文件流的使用创建
            out = new ZipOutputStream(new FileOutputStream(outFile));
            //判断是否是一个文件或目录
            //如果是文件则压缩
            if (fileOrDirectory.isFile()){
                zipFileOrDirectory(out,fileOrDirectory, "");
            } else {
                //否则列出目录中的所有文件递归进行压缩

                File[]entries = fileOrDirectory.listFiles();
                for (int i= 0; i < entries.length;i++) {
                    zipFileOrDirectory(out,entries[i],fileOrDirectory.getName()+"/");//传入最外层目录名

                }
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally{
            if (out!= null){
                try {
                    out.close();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private static void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath)throws IOException {
        FileInputStream in = null;
        try {
            //判断是否为目录
            if (!fileOrDirectory.isDirectory()){
                byte[] buffer= new byte[4096];
                int bytes_read;
                in= new FileInputStream(fileOrDirectory);//读目录中的子项
                //归档压缩目录
                ZipEntry entry = new ZipEntry(curPath + fileOrDirectory.getName());//压缩到压缩目录中的文件名字
                //将压缩目录写到输出流中
                out.putNextEntry(entry);//out是带有最初传进的文件信息，一直添加子项归档目录信息
                while ((bytes_read= in.read(buffer))!= -1) {
                    out.write(buffer,0, bytes_read);
                }
                out.closeEntry();
            } else {
                //列出目录中的所有文件
                File[]entries = fileOrDirectory.listFiles();
                for (int i= 0; i < entries.length;i++) {
                    //递归压缩
                    zipFileOrDirectory(out,entries[i],curPath + fileOrDirectory.getName()+ "/");//第一次传入的curPath是空字符串
                }//目录没有后缀所以直接可以加"/"
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally{
            if (in!= null){
                try {
                    in.close();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    /**
     * DeCompress the ZIP to the path
     * @param FILE_PATH  name of ZIP
     * @param outPath   path to be unZIP
     * @throws Exception
     */

    public static void UnZipFolder(String FILE_PATH, String outPath) throws Exception{
        File zipFile = new File(FILE_PATH);//FILE_PATH为压缩文件的路径
        ZipFile zf = new ZipFile(zipFile);
        for(Enumeration<?> entries = zf.entries(); entries.hasMoreElements();){//遍历压缩文件中的所有归档过的文件

            ZipEntry entry = ((ZipEntry)entries.nextElement());//默认为-1，next->下标0的位置
            InputStream is = zf.getInputStream(entry);
            String str = outPath + File.separator + entry.getName();
            str = new String(str.getBytes("8859_1"),"GB2312");//压缩文件的编码是8859_1,此处可将其转换成指定的编码
            File desFile = new File(str);//其中单执行new file并不会在存储中创建文件或文件夹
            if(!desFile.exists()){
                File fileParentDir = desFile.getParentFile();
                if(!fileParentDir.exists()){
                    fileParentDir.mkdirs();//多级目录同时创建  怕文件归档在很多级目录下,或者文件需要在很多级未创建目录下，mkdir不会创建父目录，mkdirs会
                }
                desFile.createNewFile();//如果没有父目录将创建失败
            }
            FileOutputStream out = new FileOutputStream(desFile);
            byte buffer[] = new byte[1024*1024];
            int readLength;
            while((readLength = is.read(buffer)) > 0){
                out.write(buffer, 0, readLength);
            }
            is.close();
            out.close();
        }
    }
/*
* 搜索对应文件从某目录中
* */
//保存搜索到的文件
    static File file;
    public static File findFile(File fileorfolder,String requestFile){
        file=null;//重置标识搜索文件的file为空
        searchFile(fileorfolder,requestFile);
        if (file!=null){
            Log.d("file",file.getName());
        }

        return file;
    }
    public static void searchFile(File fileorfolder,String requestFile){
        //是目录
        if(fileorfolder.isDirectory()){

            File[] entries=fileorfolder.listFiles();
            for(File enrtyfile:entries){
                searchFile(enrtyfile,requestFile);
            }
            //是文件
        }else {
            //是传入文件名对应文件
            if (fileorfolder.getName().equals(requestFile)){//equals是比较字符串值是否相等
                file=fileorfolder;
            }
        }
        //不是目录又不是目标文件
    }
}
