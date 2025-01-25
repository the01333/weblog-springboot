package com.puxinxiaolin.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_article_category_rel")
public class ArticleCategoryRelDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "article_id")
    private Long articleId;

    @TableField(value = "category_id")
    private Long categoryId;

}
