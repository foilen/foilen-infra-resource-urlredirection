/*
    Foilen Infra Resource Url Redirection
    https://github.com/foilen/foilen-infra-resource-urlredirection
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.urlredirection;

import java.util.List;

import com.foilen.infra.plugin.v1.core.context.ChangesContext;
import com.foilen.infra.plugin.v1.core.context.CommonServicesContext;
import com.foilen.infra.plugin.v1.core.eventhandler.AbstractCommonMethodUpdateEventHandler;
import com.foilen.infra.plugin.v1.core.eventhandler.CommonMethodUpdateEventHandlerContext;
import com.foilen.infra.plugin.v1.core.service.IPResourceService;
import com.foilen.infra.plugin.v1.model.resource.LinkTypeConstants;
import com.foilen.infra.resource.dns.DnsPointer;
import com.foilen.infra.resource.machine.Machine;

public class UrlRedirectionUpdateHandler extends AbstractCommonMethodUpdateEventHandler<UrlRedirection> {

    @Override
    protected void commonHandlerExecute(CommonServicesContext services, ChangesContext changes, CommonMethodUpdateEventHandlerContext<UrlRedirection> context) {

        IPResourceService resourceService = services.getResourceService();

        UrlRedirection resource = context.getResource();

        // Create and manage : DnsPointer (attach Machines)
        List<Machine> installOnMachines = resourceService.linkFindAllByFromResourceAndLinkTypeAndToResourceClass(resource, LinkTypeConstants.INSTALLED_ON, Machine.class);
        String domainName = resource.getDomainName();
        DnsPointer dnsPointer = new DnsPointer(domainName);
        dnsPointer = retrieveOrCreateResource(resourceService, changes, dnsPointer, DnsPointer.class);
        updateLinksOnResource(services, changes, dnsPointer, LinkTypeConstants.POINTS_TO, Machine.class, installOnMachines);

        context.getManagedResources().add(dnsPointer);

    }

    @Override
    public Class<UrlRedirection> supportedClass() {
        return UrlRedirection.class;
    }

}
