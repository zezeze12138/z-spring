package com.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源输入流接口
 */
public interface InputStreamSource {

    InputStream getInputStream() throws IOException;

}
