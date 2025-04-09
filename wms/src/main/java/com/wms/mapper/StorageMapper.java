package com.wms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Wrapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liumingshu
 * @since 2025-04-09
 */
@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
    IPage pageCCC(IPage<Storage> page, @Param(Constants.WRAPPER)Wrapper wrapper);
}
