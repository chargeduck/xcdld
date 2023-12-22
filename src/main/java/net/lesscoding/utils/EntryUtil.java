package net.lesscoding.utils;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import net.lesscoding.entity.ThingsEntry;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author eleven
 * @date 2023/12/21 15:22
 * @apiNote
 */
public class EntryUtil {
    public static List<String> getEntriesStr(List<ThingsEntry> list) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<String> resultList = new ArrayList<>(list.size());
        list.forEach(item -> {
            String str = StrUtil.format("【{}】:{} {} {}",
                    item.getEntryName(),
                    item.getEntryIntro(),
                    item.getFixed() ? "固定增加" : "百分比增加" ,
                    item.getNum());
            resultList.add(str);
        });
        return resultList;
    }

    public static void main(String[] args) {
        String path = "D:/QQFile/exportBookSource.json";
        try {
            String string = readJsonFileToString(path);
            System.out.println(string);
            System.out.println(string.length());
            QrCodeUtil.generate(string, 1080, 960, FileUtil.file("d:/qrcode.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readJsonFileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
