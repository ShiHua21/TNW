package com.jic.elearning.thrid.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "tianma")
public class TianmaProperties {


    private String hostName;
    private String vcodePath;
    private String loginName;
    private String loginPass;

    private HttpUrl httpUrl;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getVcodePath() {
        return vcodePath;
    }

    public void setVcodePath(String vcodePath) {
        this.vcodePath = vcodePath;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(HttpUrl httpUrl) {
        this.httpUrl = httpUrl;
    }

    public static class HttpUrl {
        private String verifyCode;
        private String login;
        private String mainHtml;
        private String myOrders;
        private String afterSalesManagement;
        private String addOrderRemark;

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getMainHtml() {
            return mainHtml;
        }

        public void setMainHtml(String mainHtml) {
            this.mainHtml = mainHtml;
        }


        public String getMyOrders() {
            return myOrders;
        }

        public void setMyOrders(String myOrders) {
            this.myOrders = myOrders;
        }

        public String getAfterSalesManagement() {
            return afterSalesManagement;
        }

        public void setAfterSalesManagement(String afterSalesManagement) {
            this.afterSalesManagement = afterSalesManagement;
        }

        public String getAddOrderRemark() {
            return addOrderRemark;
        }

        public void setAddOrderRemark(String addOrderRemark) {
            this.addOrderRemark = addOrderRemark;
        }
    }

    //    @Valid
//    @NotNull
//    private Cache cache;
//    @Valid
//    @NotNull
//    private Cors cors;
//
//    public static class Cache {
//
//        private Integer ttl;
//        private Long maxEntries;
//
//        public Integer getTtl() {
//            return ttl;
//        }
//
//        public void setTtl(Integer ttl) {
//            this.ttl = ttl;
//        }
//
//        public Long getMaxEntries() {
//            return maxEntries;
//        }
//
//        public void setMaxEntries(Long maxEntries) {
//            this.maxEntries = maxEntries;
//        }
//
//        @Override
//        public String toString() {
//            return "Cache{" +
//                    "ttl=" + ttl +
//                    ", maxEntries=" + maxEntries +
//                    '}';
//        }
//    }
//
//    public static class Cors {
//        private List<String> allowedOrigins;
//        private String[] allowedMethods;
//        private List<String> allowedHeaders;
//        private Boolean allowCredentials;
//        private Integer maxAge;
//
//        public List<String> getAllowedOrigins() {
//            return allowedOrigins;
//        }
//
//        public void setAllowedOrigins(List<String> allowedOrigins) {
//            this.allowedOrigins = allowedOrigins;
//        }
//
//        public String[] getAllowedMethods() {
//            return allowedMethods;
//        }
//
//        public void setAllowedMethods(String[] allowedMethods) {
//            this.allowedMethods = allowedMethods;
//        }
//
//        public List<String> getAllowedHeaders() {
//            return allowedHeaders;
//        }
//
//        public void setAllowedHeaders(List<String> allowedHeaders) {
//            this.allowedHeaders = allowedHeaders;
//        }
//
//        public Boolean getAllowCredentials() {
//            return allowCredentials;
//        }
//
//        public void setAllowCredentials(Boolean allowCredentials) {
//            this.allowCredentials = allowCredentials;
//        }
//
//        public Integer getMaxAge() {
//            return maxAge;
//        }
//
//        public void setMaxAge(Integer maxAge) {
//            this.maxAge = maxAge;
//        }
//
//        @Override
//        public String toString() {
//            return "Cors{" +
//                    "allowedOrigins=" + allowedOrigins +
//                    ", allowedMethods=" + Arrays.toString(allowedMethods) +
//                    ", allowedHeaders=" + allowedHeaders +
//                    ", allowCredentials=" + allowCredentials +
//                    ", maxAge=" + maxAge +
//                    '}';
//        }
//    }
//
//    public Cache getCache() {
//        return cache;
//    }
//
//    public void setCache(Cache cache) {
//        this.cache = cache;
//    }
//
//    public Cors getCors() {
//        return cors;
//    }
//
//    public void setCors(Cors cors) {
//        this.cors = cors;
//    }
//
//    @Override
//    public String toString() {
//        return "ApplicationProperties{" +
//                "cache=" + cache +
//                ", cors=" + cors +
//                '}';
//    }
}