package cn.usst.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceFile {

    private Long id;

    private Long resourceId;

    private String fileUrl;

    private String fileType;
}

