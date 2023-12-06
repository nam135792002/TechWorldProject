package com.techworld.admin;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractExporter {
    public void setResponseHeaser(HttpServletResponse response, String contentType, String extension, String prefix) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String fileName = prefix + timestamp + extension;

        response.setContentType(contentType);
        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerkey, headerValue);
    }}
