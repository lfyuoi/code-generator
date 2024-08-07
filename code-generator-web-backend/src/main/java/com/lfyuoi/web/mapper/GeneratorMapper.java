package com.lfyuoi.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lfyuoi.web.model.entity.Generator;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lfy
 * @description 针对表【generator(代码生成器)】的数据库操作Mapper
 * @createDate 2024-04-16 14:41:21
 * @Entity com.lfyuoi.web.model.entity.Generator
 */
public interface GeneratorMapper extends BaseMapper<Generator> {

    @Select("SELECT id, distPath FROM generator WHERE isDelete = 1")
    List<Generator> listDeletedGenerator();
}




