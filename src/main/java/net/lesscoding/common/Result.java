package net.lesscoding.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eleven
 * @date  2022-11-10 19:55:27
 * @description 通用返回实体类
 * Generated By: lesscoding.net basic service
 * Link to: <a href="https://lesscoding.net">https://lesscoding.net</a>
 * mail to:2496290990@qq.com zjh292411@gmail.com admin@lesscoding.net
 */
@Data
@AllArgsConstructor
public class Result {
    private Integer code;

    private String message;

    private Object data;

    private Result() {}

}
