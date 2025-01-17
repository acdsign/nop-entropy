package io.nop.rpc.cluster.http;

import io.nop.cluster.discovery.ServiceInstance;
import io.nop.http.api.client.IHttpClient;
import io.nop.rpc.http.DefaultRpcUrlBuilder;
import io.nop.rpc.http.HttpRpcService;
import io.nop.rpc.api.IRpcService;
import io.nop.rpc.cluster.IRpcClientInstanceProvider;

import javax.inject.Inject;

public class HttpRpcClientInstanceProvider implements IRpcClientInstanceProvider {
    private IHttpClient httpClient;
    private boolean useHttps;

    public void setUseHttps(boolean useHttps) {
        this.useHttps = useHttps;
    }

    @Inject
    public void setHttpClient(IHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public IRpcService getRpcClientInstance(ServiceInstance instance) {
        String baseUrl = useHttps ? "https://" : "http://";
        baseUrl += instance.getAddr() + ":" + instance.getPort();
        return new HttpRpcService(httpClient, new DefaultRpcUrlBuilder(baseUrl));
    }
}