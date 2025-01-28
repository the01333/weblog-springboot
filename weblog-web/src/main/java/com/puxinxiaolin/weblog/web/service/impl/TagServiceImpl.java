package com.puxinxiaolin.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.puxinxiaolin.weblog.common.domain.dos.TagDO;
import com.puxinxiaolin.weblog.common.domain.mapper.TagMapper;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.convert.TagConvert;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagListResponseVO;
import com.puxinxiaolin.weblog.web.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public Response findTagList() {
        List<TagDO> tagDOList = tagMapper.selectList(Wrappers.emptyWrapper());

        // DO -> VO
        List<FindTagListResponseVO> findTagListResponseVOList = TagConvert.INSTANCE
                .convertTagDOListToFindTagListResponseVOList(tagDOList);

        return Response.success(findTagListResponseVOList);
    }

}
