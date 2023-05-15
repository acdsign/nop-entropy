package io.nop.http.api.client.rpc;

import io.nop.api.core.beans.ApiRequest;

public interface IApiUrlBuilder {
    String buildUrl(ApiRequest<?> req, String serviceMethod);

    default String toHttpHeader(String name) {
        return name;
    }
}
