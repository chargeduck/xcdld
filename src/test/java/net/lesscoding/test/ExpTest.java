package net.lesscoding.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author eleven
 * @date 2023/10/20 8:39
 * @apiNote
 */
@Slf4j
public class ExpTest {
    public static void main(String[] args) {
        for (int i = 0; i <= 40; i++) {
            System.out.println(i + ": ===>" + i * Math.log(i) * Math.sqrt(i));
        }
    }
}
