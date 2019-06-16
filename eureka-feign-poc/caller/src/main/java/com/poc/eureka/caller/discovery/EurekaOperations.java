package com.poc.eureka.caller.discovery;

import feign.Feign;
import feign.gson.GsonDecoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;


public class EurekaOperations {

    private String serverPort;
    private String ipAddress;

    @Value("${host.name}")
    private String hostName;

    @Value("${service.name}")
    private String serviceName;

    private  EurekaHttpMethods eurekaHttpMethodsService = Feign.builder().decoder(new GsonDecoder()).target(EurekaHttpMethods.class, "http://localhost:8080/eureka/v2/apps");

    public void register(){
        eurekaHttpMethodsService.registry("{\n" +
                "    \"instance\": {\n" +
                "        \"hostName\": \""+ hostName +"\",\n" +
                "        \"app\": \""+ serviceName +"\",\n" +
                "        \"vipAddress\": \"com.teste\",\n" +
                "        \"secureVipAddress\": \"com.teste\",\n" +
                "        \"ipAddr\": \""+ ipAddress +"\",\n" +
                "        \"status\": \"STARTING\",\n" +
                "        \"port\": {\"$\": \"" + serverPort + "\", \"@enabled\": \"true\"},\n" +
                "        \"securePort\": {\"$\": \"8443\", \"@enabled\": \"true\"},\n" +
                "        \"healthCheckUrl\": \"http://localhost:"+serverPort+"/healthcheck\",\n" +
                "        \"statusPageUrl\": \"http://localhost:"+serverPort+"/status\",\n" +
                "        \"homePageUrl\": \"http://localhost:"+serverPort+"\",\n" +
                "        \"dataCenterInfo\": {\n" +
                "            \"@class\": \"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo\",\n" +
                "            \"name\": \"MyOwn\"\n" +
                "        }\n" +
                "    }\n" +
                "}", serviceName);
        eurekaHttpMethodsService.updateToUP(serviceName, hostName);
    }

    public void delete(){
        eurekaHttpMethodsService.delete(serviceName, hostName);
    }

    public String getHost(String instanceId){
        EurekaHttpMethods eurekaGet = Feign.builder().target(EurekaHttpMethods.class, "http://localhost:8080/eureka/v2/instances/");
        return extractHost(eurekaGet.getInstanceInformation(instanceId));
    }

    private  String extractHost(String jsonString){
        JSONObject jsonResult = new JSONObject(jsonString);
        String instance = jsonResult.get("instance").toString();
        JSONObject instanceInformation = new JSONObject(instance);
        return instanceInformation.get("ipAddr") + ":" + instanceInformation.getJSONObject("port").get("$");
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
