package org.project.job.utility;

import jakarta.servlet.http.HttpServletRequest;

public class ApplicationUrl {
    public static String getUrl(HttpServletRequest request) {
//        String appUrl = request.getRequestURL().toString();
        String appUrl = "https://backend-5bno.onrender.com";
        return appUrl.replace(request.getServletPath(), "");
    }
}
