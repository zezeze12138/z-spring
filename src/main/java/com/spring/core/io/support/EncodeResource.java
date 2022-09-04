package com.spring.core.io.support;

import com.spring.core.io.InputStreamSource;
import com.spring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class EncodeResource implements InputStreamSource{

    private final Resource resource;

    private final String encoding;

    private final Charset charset;

    public EncodeResource(Resource resource){
        this(resource, null, null);
    }

    public EncodeResource(Resource resource, String encoding){
        this(resource, encoding, null);
    }

    public EncodeResource(Resource resource, Charset charset){
        this(resource, null, charset);
    }

    public EncodeResource(Resource resource, String encoding, Charset charset) {
        this.resource = resource;
        this.encoding = encoding;
        this.charset = charset;
    }

    /**
     * 获取输入流
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return this.resource.getInputStream();
    }

    /**
     * 获取资源读取类
     * @return
     * @throws IOException
     */
    public Reader getReader() throws IOException {
        if(this.charset != null){
            return new InputStreamReader(this.resource.getInputStream(), this.charset);
        }else if(this.encoding != null){
            return new InputStreamReader(this.resource.getInputStream(), this.encoding);
        }else{
            return new InputStreamReader(this.resource.getInputStream());
        }
    }

    public Resource getResource() {
        return resource;
    }

    public String getEncoding() {
        return encoding;
    }

    public Charset getCharset() {
        return charset;
    }
}
