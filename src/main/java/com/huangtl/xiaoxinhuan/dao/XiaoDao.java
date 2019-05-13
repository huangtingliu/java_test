package com.huangtl.xiaoxinhuan.dao;

import com.huangtl.xiaoxinhuan.entity.Location;
import org.springframework.stereotype.Component;

@Component
public interface XiaoDao {

    void updateLocation(Location location);

}
