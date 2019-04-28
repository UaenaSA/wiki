package com.microcore;


import com.xiaoleilu.hutool.io.FileUtil;

import java.io.File;

/**
 * DESC:清除.svn目录
 *
 * @author leizhenyang
 * @date 2018/5/25
 */
public class RemoveSVN {

    /**
     * @param args
     */
    public static void main(String[] args) {
        new RemoveSVN().clean();
    }

    /**
     * 清理
     */
    public void clean() {
        remove(new File("F:\\Work\\公司文件资料\\微核\\12.MicroCore开发工具包\\04开发框架\\microcore-fast spring boot 1.5.x"));
    }

    /**
     * 移除
     *
     * @param file
     */
    public void remove(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                System.out.println(f.getName());
                if (f.getName().lastIndexOf(".svn") > 0) {
                    System.out.println(f.getAbsolutePath());
                    FileUtil.del(f);
                } else {
                    remove(f);
                }
            }
        }
    }
}
