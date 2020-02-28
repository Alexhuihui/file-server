package top.alexmmd.domain;

import lombok.*;

/**
 * @author 汪永晖
 */
@Data
@NoArgsConstructor
@ToString
public class RespEntity {

    private int code;
    private String msg;
    private Object data;
    private String token;
    private int sign;

    public RespEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespEntity(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
