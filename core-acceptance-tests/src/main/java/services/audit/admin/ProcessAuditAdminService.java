package services.audit.admin;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.activiti.cloud.api.model.shared.events.CloudRuntimeEvent;
import org.activiti.cloud.qa.service.BaseService;
import org.springframework.hateoas.PagedResources;

public interface ProcessAuditAdminService extends BaseService {

    @RequestLine("GET /admin/v1/events?sort=timestamp,desc&sort=id,desc")
    @Headers("Content-Type: application/json")
    PagedResources<CloudRuntimeEvent> getEventsAdmin();

    @RequestLine("GET /admin/v1/events?search={search}")
    @Headers("Content-Type: application/json")
    PagedResources<CloudRuntimeEvent> getEventsAdmin(@Param("search") String search);
}