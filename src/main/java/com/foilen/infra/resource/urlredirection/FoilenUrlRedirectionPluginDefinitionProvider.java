/*
    Foilen Infra Resource Url Redirection
    https://github.com/foilen/foilen-infra-resource-urlredirection
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.urlredirection;

import java.util.Arrays;

import com.foilen.infra.plugin.v1.core.context.CommonServicesContext;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionProvider;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionV1;

public class FoilenUrlRedirectionPluginDefinitionProvider implements IPPluginDefinitionProvider {

    @Override
    public IPPluginDefinitionV1 getIPPluginDefinition() {
        IPPluginDefinitionV1 pluginDefinitionV1 = new IPPluginDefinitionV1("Foilen", "Url Redirection", "To manage url redirections", "1.0.0");

        pluginDefinitionV1.addCustomResource(UrlRedirection.class, "Url Redirection", //
                Arrays.asList( //
                        UrlRedirection.PROPERTY_DOMAIN_NAME //
                ), //
                Arrays.asList( //
                        UrlRedirection.PROPERTY_DOMAIN_NAME, //
                        UrlRedirection.PROPERTY_HTTP_REDIRECT_TO_URL, //
                        UrlRedirection.PROPERTY_HTTPS_REDIRECT_TO_URL //
                ));

        pluginDefinitionV1.addUpdateHandler(new UrlRedirectionUpdateHandler());

        return pluginDefinitionV1;
    }

    @Override
    public void initialize(CommonServicesContext commonServicesContext) {
    }

}
