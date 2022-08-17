package com.spring.core.io;

import java.io.File;
import java.io.IOException;

/**
 * 资源信息接口
 */
public interface Resource extends InputStreamSource{

    /**
     * 获取资源文件
     * @return
     * @throws IOException
     */
    File getFile() throws IOException;

}
