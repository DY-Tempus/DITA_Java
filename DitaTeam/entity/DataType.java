package entity;

import java.io.Serializable;

public class DataType implements Serializable {
    private static final long serialVersionUID = 1L; // 직렬화 버전 ID

    public int protocol;
    public Object obj;

    // 기본 생성자 및 기타 메서드 (필요 시)
}
