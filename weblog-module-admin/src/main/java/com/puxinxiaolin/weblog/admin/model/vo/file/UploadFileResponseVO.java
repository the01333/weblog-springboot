package com.puxinxiaolin.weblog.admin.model.vo.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileResponseVO {

    /**
     * 文件的访问链接
     */
    private String url;

}
