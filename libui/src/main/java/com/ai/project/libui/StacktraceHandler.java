package com.ai.project.libui;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StacktraceHandler {
    public static String getStacktrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
