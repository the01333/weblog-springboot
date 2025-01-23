package com.puxinxiaolin.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "添加标签 VO")
public class AddTagRequestVO {

    @NotEmpty(message = "标签集合不能为空")
    private List<String> tagList;

}
